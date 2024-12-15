#include "App.h"
#include <stdexcept>
#include <iostream>
#include "../dataClass/Data.h"
#include "../Menu/ConsoleMenu.h"
#include "../Menu/IMenu.h"

App::App(ConsoleMenu* menu, std::map<std::string, ICommand*> commands, Data* data)
    : menu(menu), commands(commands), data(data) {}
App::App(IMenu* menu, std::map<std::string, ICommand*> commands)
    : menu(menu), commands(commands){
        
    }
App::~App() {}

    void App::run() {
        while (true) {
        // Receive data from the client
        Data* inputData = ConsoleMenu::nextCommand2(data);
        string toPrint;
        if (data == nullptr || inputData ==nullptr) {
            break;  // Exit the loop if the client disconnects or an error occurs
        }
        string task = inputData->buffer;
        string command = menu->getCommand(task);
        string remainingCommand = menu->getCommandAsk(task);
            try {          
                    auto it = commands.find(command);
                    if (it != commands.end() && it->second != nullptr) { //check if command in the map
                        toPrint = commands[command]-> execute(remainingCommand); //execute according to the command
                    } else {
                        toPrint = " 400 Bad Request";
                    }
                } 
                    catch(...){ //catch errors (dont print a thing)
                    toPrint = " 400 Bad Request";
                }
                 Data* outputData = new Data(toPrint, toPrint.length(), data->client_sock );

                ConsoleMenu::printOutPut2(outputData);
        
    }
}



