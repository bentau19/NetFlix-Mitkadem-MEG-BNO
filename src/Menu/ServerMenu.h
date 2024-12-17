#ifndef ServerMenu_H
#define ServerMenu_H
#include <iostream>
#include<string>
#include "IMenu.h"
#include "../dataClass/Data.h"
using namespace std;

class ServerMenu : public IMenu { // Ensure public inheritance
public:

    ServerMenu();
     virtual Data* nextCommand(void* param) override; //get the next full command from user
     virtual string getCommand(string task) override; //take the first word from input (that the command itself)
     virtual string getCommandAsk(string task) override; //take the string without the first word (that agument to the command)
     virtual void* getCommandOutput(void* param) override;
    ~ServerMenu();
};

#endif