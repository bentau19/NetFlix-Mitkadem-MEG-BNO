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
#include "ThreadPool/ThreadPool.h"
#include "App/App.h"
#include "Menu/ServerMenu.h"
#include "Commands/General/ICommand.h"
#include "Commands/General/HelpCmd.h"
#include "Commands/Add_Data/PatchCmd.h"
#include "Commands/Add_Data/PostCmd.h"
#include "Commands/Delete_Data/DeleteCmd.h"
#include "Commands/Data_Manipulation/GetCmd.h"


#define PORT 8082
#define MAX_CLIENTS 3

// Declare global commands and mutex
std::mutex command_mutex;
std::map<std::string, ICommand*> commands;

ThreadPool threadPool(MAX_CLIENTS);  // Create a thread pool

void handleClient(int clientSocket) {
    Data* data = new Data();
    data->client_sock = clientSocket;

    // Initialize commands
    commands["help"] = new HelpCmd();
    commands["GET"] = new GetCmd();
    commands["POST"] = new PostCmd();
    commands["PATCH"] = new PatchCmd();
    commands["DELETE"] = new DeleteCmd();

    // Create App instance and run
    App* app = new App(new ServerMenu(), commands, data);
    app->run();  // This will handle the client request

    // Clean up after client interaction
    delete app;
    delete data;

    close(clientSocket);  // Close the socket after handling
}

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

    while (true) {
        if ((clientSocket = accept(serverSocket, (struct sockaddr *)&clientAddr, &clientAddrLen)) < 0) {
            perror("Accept failed");
            continue;
        }

        std::cout << "New connection: " << inet_ntoa(clientAddr.sin_addr) << ":" << ntohs(clientAddr.sin_port) << std::endl;

        // Add the client handler as a task for the thread pool
        threadPool.addTask([clientSocket]() {
            handleClient(clientSocket);  // Pass client socket to the client handler
        });
    }

    close(serverSocket);  // Close the server socket
    return 0;
}
