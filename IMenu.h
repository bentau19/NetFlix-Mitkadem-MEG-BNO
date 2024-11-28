#ifndef IMenu_H
#define IMenu_H
#include <iostream>
using namespace std;

class IMenu {
    public:
     virtual string nextCommand() = 0;
     virtual string getCommand(string task) = 0;
     virtual string getCommandAsk(string task) = 0;
     //virtual void displayError(string str) = 0;

};

#endif