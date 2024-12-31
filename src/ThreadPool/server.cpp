#include <iostream>
#include <thread>
#include <mutex>
#include <atomic>
#include <vector>
#include <queue>
#include <condition_variable>
#include <string>
#include <cstring>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define PORT 8082
#define MAX_CLIENTS 3
#define BUFFER_SIZE 1024

// Thread pool to manage worker threads
class ThreadPool {
private:
    std::vector<std::thread> workers;
    std::queue<int> tasks; // Queue of client sockets
    std::mutex queueMutex;
    std::condition_variable condition;
    std::atomic<bool> stop;

    void workerFunction() {
        while (true) {
            int clientSocket;
            {
                std::unique_lock<std::mutex> lock(queueMutex);
                condition.wait(lock, [this]() { return stop || !tasks.empty(); });

                if (stop && tasks.empty())
                    return;

                clientSocket = tasks.front();
                tasks.pop(); // Mutex is unlocked here
                // std::unique_lock<std::mutex> lock(queueMutex);

            }

            // Process the client outside the critical section
            handleClient(clientSocket);

            // Close the client socket after processing
            close(clientSocket);
        }
    }


    void handleClient(int clientSocket) {
        static thread_local int messageCount = 0; // Each thread tracks its own count
        char buffer[BUFFER_SIZE] = {0};

        while (true) {
            int bytesRead = read(clientSocket, buffer, BUFFER_SIZE);
            if (bytesRead <= 0) {
                std::cout << "Client " << clientSocket << " disconnected.\n";
                close(clientSocket);
                break;
            }

            std::string clientMessage(buffer);
            messageCount++;

            // Check if the client sent "goodbye"
            if (clientMessage.find("goodbye") != std::string::npos) {
                std::string goodbyeResponse = "goodbye :)";
                send(clientSocket, goodbyeResponse.c_str(), goodbyeResponse.size(), 0);
                std::cout << "Client said goodbye. Connection closed.\n";
                close(clientSocket);
                break;
            }

            // Respond to other messages
            std::cout << "Client "<< clientSocket << " send: " << clientMessage << std::endl;
            std::string response = "Message " + std::to_string(messageCount) + ": " + clientMessage + " :)";
            send(clientSocket, response.c_str(), response.size(), 0);

            memset(buffer, 0, BUFFER_SIZE);
        }
    }

public:
    ThreadPool(size_t numThreads) : stop(false) {
        for (size_t i = 0; i < numThreads; ++i) {
            workers.emplace_back([this]() { workerFunction(); });
        }
    }

    ~ThreadPool() {
        {
            std::unique_lock<std::mutex> lock(queueMutex);
            stop = true;
        }

        condition.notify_all();
        for (std::thread &worker : workers) {
            if (worker.joinable()) {
                worker.join();
            }
        }
    }

    void addTask(int clientSocket) {
        {
            std::unique_lock<std::mutex> lock(queueMutex);
            tasks.push(clientSocket);
        }
        condition.notify_one();
    }
};

int main() {
    int serverSocket, clientSocket;
    struct sockaddr_in serverAddr, clientAddr;
    socklen_t clientAddrLen = sizeof(clientAddr);

    // Create socket
    if ((serverSocket = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    // Configure server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(PORT);

    // Bind the socket
    if (bind(serverSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0) {
        perror("Bind failed");
        exit(EXIT_FAILURE);
    }

    // Listen for incoming connections
    if (listen(serverSocket, MAX_CLIENTS) < 0) {
        perror("Listen failed");
        exit(EXIT_FAILURE);
    }

    std::cout << "Server is listening on port " << PORT << std::endl;

    // Initialize thread pool
    ThreadPool threadPool(MAX_CLIENTS); // 3 worker threads

    while (true) {
        if ((clientSocket = accept(serverSocket, (struct sockaddr *)&clientAddr, &clientAddrLen)) < 0) {
            perror("Accept failed");
            continue;
        }

        std::cout << "New connection: " << inet_ntoa(clientAddr.sin_addr) << ":" << ntohs(clientAddr.sin_port) << std::endl;

        // Add client socket to the thread pool
        threadPool.addTask(clientSocket);
    }

    close(serverSocket); // Close the server socket
    return 0;
}
