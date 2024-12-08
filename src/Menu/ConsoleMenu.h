#ifndef ConsoleMenu_H
#define ConsoleMenu_H
#include <iostream>
#include<string>
#include "IMenu.h"
using namespace std;

class ConsoleMenu : public IMenu { // Ensure public inheritance
public:

    ConsoleMenu();
     virtual string nextCommand() override; //get the next full command from user
     virtual string getCommand(string task) override; //take the first word from input (that the command itself)
     virtual string getCommandAsk(string task) override; //take the string without the first word (that agument to the command)
    ~ConsoleMenu();
};

#endif