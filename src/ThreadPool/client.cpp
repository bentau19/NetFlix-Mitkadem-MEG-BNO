#include <iostream>
#include <string>
#include <cstring>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 8082
#define BUFFER_SIZE 1024
int main() {
    int clientSocket;
    struct sockaddr_in serverAddr;
    char buffer[BUFFER_SIZE] = {0};

    // Create socket
    if ((clientSocket = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Socket creation failed");
        return -1;
    }

    // Configure server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);

    // Convert IPv4 address from text to binary form
    if (inet_pton(AF_INET, "127.0.0.1", &serverAddr.sin_addr) <= 0) {
        perror("Invalid address or address not supported");
        return -1;
    }

    // Connect to the server
    if (connect(clientSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0) {
        perror("Connection failed");
        return -1;
    }

    std::cout << "Connected to the server." << std::endl;

    // Send initial "hello" message
    std::string initialMessage = "hello";
    send(clientSocket, initialMessage.c_str(), initialMessage.size(), 0);
    std::cout << "Sent: " << initialMessage << std::endl;

    while (true) {
        // Read server response
        memset(buffer, 0, BUFFER_SIZE);
        int bytesRead = read(clientSocket, buffer, BUFFER_SIZE);
        if (bytesRead <= 0) {
            std::cout << "Server disconnected." << std::endl;
            break;
        }

        std::cout << "Server: " << buffer << std::endl;

        // Exit if the server says "goodbye :)"
        if (std::string(buffer).find("goodbye :)") != std::string::npos) {
            std::cout << "Connection closed by the server." << std::endl;
            break;
        }

        // Get user input
        std::string userInput;
        std::cout << "You: ";
        std::getline(std::cin, userInput);
        std::cout << "Sent: " << userInput << std::endl;

        // Send user input to the server
        send(clientSocket, userInput.c_str(), userInput.size(), 0);

        // Exit if the user sends "goodbye"
        if (userInput == "goodbye") {
            std::cout << "You ended the connection." << std::endl;
            break;
        }
    }

    close(clientSocket);
    return 0;
}
