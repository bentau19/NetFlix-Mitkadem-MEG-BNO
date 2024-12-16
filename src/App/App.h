#ifndef APP_H
#define APP_H

#include <map>
#include <string>
#include "../Menu/IMenu.h"
#include "Commands/General/ICommand.h"

class App {
private:
    IMenu* menu;
    std::map<std::string, ICommand*> commands;

public:
    App(IMenu* menu, std::map<std::string, ICommand*> commands); // Constructor
    ~App(); // Destructor
    void run(); // Main logic
};

#endif // APP_H
