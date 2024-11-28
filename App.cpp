#include <iostream>
#include <fstream>
#include <map>
#include "ICommand.h"
#include "IMenu.h"
using namespace std;
class App
{
private:
    IMenu* menu;
    map <string, ICommand*> commands;
public:
   App(IMenu* menu, map <string, ICommand*> commands): menu(menu), commands(commands){}
    void run() {
        while (true)
        {
        string task = menu->nextCommand();
        string command = menu->getCommand(task);
        string remainingCommand = menu->getCommandAsk(task);
            try {          
                    auto it = commands.find(command);
                    if (it != commands.end() && it->second != nullptr) {
                        commands[command]-> execute(remainingCommand);
                    } else {
                        throw std::runtime_error("Command not found: " + command);
                    }
                } 
                    catch(...){

                }
       }
        
    }
};


