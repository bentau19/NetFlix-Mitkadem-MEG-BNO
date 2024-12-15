#ifndef ConsoleMenu_H
#define ConsoleMenu_H
#include <iostream>
#include<string>
#include "IMenu.h"
#include "../dataClass/Data.h"
using namespace std;

class ConsoleMenu : public IMenu { // Ensure public inheritance
public:

    ConsoleMenu();
         static void* printOutPut2(void* param);
     static Data* nextCommand2(void* param); //get the next full command from user

     virtual string nextCommand() override; //get the next full command from user
     virtual string getCommand(string task) override; //take the first word from input (that the command itself)
     virtual string getCommandAsk(string task) override; //take the string without the first word (that agument to the command)
    ~ConsoleMenu();
};

#endif