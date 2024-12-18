#ifndef HelpCommand_H
#define HelpCommand_H
using namespace std;
#include <string>
#include "ICommand.h"

class HelpCmd : public ICommand { // Ensure public inheritance
public:
    HelpCmd();
    ~HelpCmd();
    virtual std::string execute(std::string str) override; // Use "override" keyword
};

#endif