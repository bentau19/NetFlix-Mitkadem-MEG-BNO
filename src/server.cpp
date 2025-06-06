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
#include <cstdlib> 
#include "ThreadPool/ThreadPool.h"
#include "App/App.h"
#include "Menu/ServerMenu.h"
#include "Commands/General/ICommand.h"
#include "Commands/General/HelpCmd.h"
#include "Commands/Add_Data/PatchCmd.h"
#include "Commands/Add_Data/PostCmd.h"
#include "Commands/Delete_Data/DeleteCmd.h"
#include "Commands/Data_Manipulation/GetCmd.h"

#define DEFAULT_PORT 12345 //defolt port
#define MAX_CLIENTS 3 //define max threads num

// Declare global commands and mutex
std::mutex command_mutex;
std::map<std::string, ICommand*> commands;

ThreadPool threadPool(MAX_CLIENTS);  // Create a thread pool

void handleClient(int clientSocket) {
    Data* data = new Data(); //create data
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

int main(int argc, char* argv[]) {
    int port = DEFAULT_PORT;

    // Check if a port number is provided as a command-line argument
    if (argc > 1) {
        try {
            port = std::stoi(argv[1]);  // Convert argument to integer
        } catch (std::exception& e) {
            std::cerr << "Invalid port number. Using default port " << DEFAULT_PORT << std::endl;
            port = DEFAULT_PORT;
        }
    }

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
    serverAddr.sin_port = htons(port);

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
     //inform that the server is indeed running
    std::cout << "Server is listening on port " << port << std::endl;
    //infinate loop
    while (true) {
        //if negatine means an error happend
        if ((clientSocket = accept(serverSocket, (struct sockaddr *)&clientAddr, &clientAddrLen)) < 0) {
            perror("Accept failed");
            continue;
        }
        //print un sever that anew connection happend
        std::cout << "New connection: " << inet_ntoa(clientAddr.sin_addr) << ":" << ntohs(clientAddr.sin_port) << std::endl;

        // Add the client handler as a task for the thread pool
        threadPool.addTask([clientSocket]() {
            handleClient(clientSocket);  // Pass client socket to the client handler
        });
    }

    close(serverSocket);  // Close the server socket
    return 0; //end pogram
}
