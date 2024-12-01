#ifndef HelpCommand_H
#define HelpCommand_H
using namespace std;
#include <string>
#include "ICommand.h"

class HelpCommand : public ICommand { // Ensure public inheritance
public:
    HelpCommand();
    ~HelpCommand();
    virtual void execute(std::string str) override; // Use "override" keyword
};

#endif