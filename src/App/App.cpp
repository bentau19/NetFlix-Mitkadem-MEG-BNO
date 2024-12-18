#include "App.h"
#include <stdexcept>
#include <iostream>
#include "../dataClass/Data.h"
#include "../Menu/IMenu.h"

App::App(IMenu* menu, std::map<std::string, ICommand*> commands)
    : menu(menu), commands(commands){}
App::App(IMenu* menu, std::map<std::string, ICommand*> commands, Data* data): menu(menu), commands(commands), data(data){} // New constructor

App::~App() {}

    void App::run() {
        while (true) {
        // Receive data from the client
        Data* inputData = menu->nextCommand(data);
        string toPrint;
        if (data == nullptr || inputData ==nullptr) {
            break;  // Exit the loop if the client disconnects or an error occurs
        }
        string task = inputData->buffer; //get the string from data = client input
        string command = menu->getCommand(task); //first word of string
        string remainingCommand = menu->getCommandAsk(task); //get the agument of the command
            try {          
                    auto it = commands.find(command); //search command 
                    if (it != commands.end() && it->second != nullptr) { //check if command in the map
                        toPrint = commands[command]-> execute(remainingCommand); //execute according to the command
                    } else {
                        toPrint = "400 Bad Request\n"; //if command dont exist return error
                    }
                } 
                catch(...) { //catch errors (dont print a thing)
                    toPrint = "400 Bad Request\n";
                }
                 Data* outputData = new Data(toPrint, toPrint.length(), data->client_sock );

                menu->getCommandOutput(outputData); //print the result
                
        
    }
}



