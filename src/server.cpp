#include <iostream>
#include <cstring>
#include <pthread.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <cstdlib>  // For std::stoi
#include <map>
#include "dataClass/Data.h"
#include "Menu/ServerMenu.h"
#include "File_Classes/StringHandler.h"
#include "Commands/ICommand.h"
#include "Commands/HelpCommand.h"
#include "Commands/AddCommand.h"
#include "App/App.h"
#include "Commands/RecommendCommand.h"

using namespace std;

map<string, ICommand*> commands;

int server_sock; // Server socket descriptor

void* threadFunc(void* arg) {
    App* app = static_cast<App*>(arg);
    app->run();
    return nullptr;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cerr << "Usage: " << argv[0] << " <port>" << endl;
        return 1;
    }

    int port;
    try {
        port = stoi(argv[1]);
        if (port <= 0 || port > 65535) {
            throw invalid_argument("Invalid port number");
        }
    } catch (...) {
        cerr << "Error: Please provide a valid port number between 1 and 65535." << endl;
        return 1;
    }

    struct sockaddr_in server_addr, client_addr;
    unsigned int client_len = sizeof(client_addr);

    // Create TCP socket
    if ((server_sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Error creating socket");
        return 1;
    }

    // Set up server address structure
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);  // Listening port from command line
    server_addr.sin_addr.s_addr = INADDR_ANY;  // Any interface

    // Bind the socket to the address
    if (bind(server_sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Bind failed");
        close(server_sock);
        return 1;
    }

    // Start listening for incoming client connections
    if (listen(server_sock, 5) < 0) {
        perror("Listen failed");
        close(server_sock);
        return 1;
    }

    cout << "Server is listening on port " << port << "..." << endl;

    // Initialize commands
    commands["add"] = new AddCommand();
    commands["help"] = new HelpCommand();
    commands["recommend"] = new RecommendCommand();

    while (true) {
        // Accept a client connection
        int client_sock = accept(server_sock, (struct sockaddr *)&client_addr, &client_len);
        if (client_sock < 0) {
            perror("Accept failed");
            continue;
        }

        // Allocate memory to store the received data
        Data* data = new Data();
        App app(new ServerMenu(), commands, data);  // Pass commands and menu to App
        data->client_sock = client_sock;

        // Create a new thread to handle this client
        pthread_t tid;
        if (pthread_create(&tid, nullptr, threadFunc, &app) != 0) {
            perror("Failed to create thread");
            close(client_sock);
            delete data;
            continue;
        }
        pthread_detach(tid);  // Detach the thread to allow automatic cleanup
    }

    // Close the server socket (not reached in this case due to infinite loop)
    close(server_sock);

    return 0;
}
