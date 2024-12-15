using namespace std;
#include <iostream>
#include <fstream>
#include <pthread.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string>
#include "../File_Classes/StringHandler.h"
#include "../dataClass/Data.h"
#include "ConsoleMenu.h"
#include <cstring>

ConsoleMenu::ConsoleMenu(/* args */)
{
}

ConsoleMenu::~ConsoleMenu()
{
}
    string ConsoleMenu::nextCommand(){
        string task;
        getline(cin, task); //take next user input
        return task; //return the input
    }

    string ConsoleMenu::getCommand(string task){
        vector<string> vecString = StringHandler::split(task,' ');
        if (!vecString.empty()) {//if have words
            return vecString[0];  //return first Word
        } 
        return ""; //else is empty string
    }

string ConsoleMenu::getCommandAsk(string task) {
    vector<string> vecString = StringHandler::split(task,' ');
    vecString.erase(vecString.begin()); //erse the first word from string
    string remaining = StringHandler::join(vecString, ' '); //return the vector to string
    return remaining; //return the string
}


Data* ConsoleMenu::nextCommand2(void* param) {
    Data* data = (Data*)param;

    // Clear the buffer before receiving new data
    memset(data->buffer, 0, sizeof(data->buffer));

    // Receive data from the client
    int bytes_received = recv(data->client_sock, data->buffer, sizeof(data->buffer) - 1, 0);
    if (bytes_received <= 0) {
        if (bytes_received == 0) {
            cout << "Client disconnected" << endl;
        } else {
            perror("Error receiving data");
        }
        return nullptr;  // Signal disconnection or error
    }

    // Null-terminate the buffer
    data->buffer[bytes_received] = '\0';
    data->buffer_size = bytes_received;

    cout << "Received from client: " << data->buffer << endl;

    return data;
}

void* ConsoleMenu::printOutPut2(void* param) {
    Data* data = (Data*)param;

    // Send the received message back to the client
    int sent_bytes = send(data->client_sock, data->buffer, data->buffer_size, 0);
    if (sent_bytes < 0) {
        perror("Error sending to client");
        return nullptr;
    }

    cout << "Sent to client: " << data->buffer << endl;

    return nullptr;
}

