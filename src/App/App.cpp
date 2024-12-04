#include "App.h"
#include <stdexcept>
#include <iostream>

App::App(IMenu* menu, std::map<std::string, ICommand*> commands)
    : menu(menu), commands(commands) {}

App::~App() {}

    void App::run() {
        while (true)
        {
        string task = menu->nextCommand(); //get user input
        string command = menu->getCommand(task);
        string remainingCommand = menu->getCommandAsk(task);
            try {          
                    auto it = commands.find(command);
                    if (it != commands.end() && it->second != nullptr) { //check if command in the map
                        commands[command]-> execute(remainingCommand); //execute according to the command
                    } else {
                        throw std::runtime_error("Command not found: " + command);
                    }
                } 
                    catch(...){ //catch errors (dont print a thing)

                }
       }
        
    }



