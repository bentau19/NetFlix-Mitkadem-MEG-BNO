#include <iostream>
#include <cstring>
#include <arpa/inet.h>
#include <unistd.h>

using namespace std;

int main() {
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
    server_addr.sin_port = htons(12345);  // The same port as the server
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");  // Loopback address (localhost)

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
