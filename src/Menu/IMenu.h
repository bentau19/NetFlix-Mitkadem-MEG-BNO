#ifndef IMenu_H
#define IMenu_H
#include <iostream>
using namespace std;

class IMenu {
    public:
     virtual Data* nextCommand(void* param) = 0; //get the next full command from user
     virtual string getCommand(string task) = 0; //take the first word from input (that the command itself)
     virtual string getCommandAsk(string task) = 0; //take the string without the first word (that agument to the command)
     virtual void* getCommandOutput(void* param)=0;
};

#endif