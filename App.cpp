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
            string command = menu-> getCommand(task);
            std::string remaingCommand = menu ->getCommandAsk(task);
            try {
                commands[command]-> execute(remaingCommand);
            }
            catch(...){

            }

        }
        
    }
};


