#ifndef APP_H
#define APP_H

#include <map>
#include <string>
#include "../Menu/IMenu.h"
#include "../Commands/ICommand.h"
#include "../dataClass/Data.h"
#include "../Menu/ConsoleMenu.h"

class App {
private:
    IMenu* menu;
    std::map<std::string, ICommand*> commands;
    Data* data;

public:
    App(IMenu* menu, std::map<std::string, ICommand*> commands); // Constructor
    
        App(ConsoleMenu* menu, std::map<std::string, ICommand*> commands, Data* data); // Constructor
        ~App(); // Destructor
    void run(); // Main logic
};

#endif // APP_H
