#ifndef HelpCommand_H
#define HelpCommand_H
using namespace std;
#include <string>
#include "ICommand.h"

class Help : public ICommand { // Ensure public inheritance
public:
    Help();
    ~Help();
    virtual void execute(std::string str) override; // Use "override" keyword
};

#endif