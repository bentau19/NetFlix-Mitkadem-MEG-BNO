#include <iostream>
#include <cstring>
#include <arpa/inet.h>
#include <unistd.h>
#include <cstdlib>  // For std::stoi

using namespace std;

int main(int argc, char* argv[]) {
    if (argc != 3) {
        cerr << "Usage: " << argv[0] << " <server_ip> <port>" << endl;
        return 1;
    }

    const char* server_ip = argv[1];
    int port;
    try {
        port = stoi(argv[2]);
        if (port <= 0 || port > 65535) {
            throw invalid_argument("Invalid port number");
        }
    } catch (...) {
        cerr << "Error: Please provide a valid port number between 1 and 65535." << endl;
        return 1;
    }

    int client_sock;
    struct sockaddr_in server_addr;
    char buffer[4096];

    // Create TCP socket
    if ((client_sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Error creating socket");
        return 1;
    }

    // Set up server address structure
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);  // The port passed as an argument
    server_addr.sin_addr.s_addr = inet_addr(server_ip);  // IP address passed as an argument

    // Connect to the server
    if (connect(client_sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Connection failed");
        close(client_sock);
        return 1;
    }

    while (true) {
        // Input message to send to the server
        cout << "Enter a message to send to the server (or type 'exit' to quit): ";
        cin.getline(buffer, sizeof(buffer));

        // Exit condition
        if (strcmp(buffer, "exit") == 0) {
            break;
        }

        // Send the message to the server
        int bytes_sent = send(client_sock, buffer, strlen(buffer), 0);
        if (bytes_sent < 0) {
            perror("Error sending message to server");
            break;
        }

        // Receive the response from the server
        int bytes_received = recv(client_sock, buffer, sizeof(buffer), 0);
        if (bytes_received < 0) {
            perror("Error receiving message from server");
            break;
        }

        // Null-terminate the received message
        buffer[bytes_received] = '\0';

        // Print the message received from the server
        cout << "Received from server: " << buffer << endl;
    }

    // Close the client socket
    close(client_sock);

    return 0;
}
