#include <iostream>
#include <cstring>
#include <pthread.h>
#include <arpa/inet.h>
#include <unistd.h>
#include "dataClass/Data.h"
using namespace std;
#include "Menu/ConsoleMenu.h"
#include "File_Classes/StringHandler.h"


int server_sock; // Server socket descriptor

void* clientHandler(void* param) {
    Data* data = (Data*)param;

    // Keep the client connected and process inputs in a loop
    while (true) {
        // Receive data from the client
        data = ConsoleMenu::nextCommand2(data);
        if (data == nullptr) {
            break;  // Exit the loop if the client disconnects or an error occurs
        }

        // Send the received data back to the client
        ConsoleMenu::printOutPut2(data);
    }

    // Clean up when the client disconnects
    cout << "Client disconnected." << endl;
    close(data->client_sock);
    delete data;

    return nullptr;
}


int main() {
    struct sockaddr_in server_addr, client_addr;
    unsigned int client_len = sizeof(client_addr);
    char buffer[4096];
    // Create TCP socket
    if ((server_sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Error creating socket");
        return 1;
    }

    // Set up server address structure
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(12345);  // Listening port
    server_addr.sin_addr.s_addr = INADDR_ANY;  // Any interface

    // Bind the socket to the address
    if (bind(server_sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Bind failed");
        return 1;
    }

    // Start listening for incoming client connections
    if (listen(server_sock, 5) < 0) {
        perror("Listen failed");
        return 1;
    }

    cout << "Server is listening on port 12345..." << endl;

    while (true) {
        // Accept a client connection
        int client_sock = accept(server_sock, (struct sockaddr *)&client_addr, &client_len);
        if (client_sock < 0) {
            perror("Accept failed");
            continue;
        }

        // Allocate memory to store the received data
        Data* data = new Data();
        data->client_sock = client_sock;
        ConsoleMenu menu;  // Create a ConsoleMenu object on the stack
        // Create a new thread to handle this client
        pthread_t tid;
        if (pthread_create(&tid, nullptr, clientHandler, data) != 0) {
            perror("Failed to create thread");
            close(client_sock);
            delete data;
            continue;
        }
        pthread_detach(tid); // Detach the thread to allow automatic cleanup
    }

    // Close the server socket (not reached in this case due to infinite loop)
    close(server_sock);

    return 0;
}
