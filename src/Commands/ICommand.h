#ifndef ICOMMAND_H
#define ICOMMAND_H

#include <string>

class ICommand {
public:
    virtual void execute() = 0;  // Method with no parameters
    virtual void execute(std::string str) = 0;  // Method with a string parameter
};

#endif // ICOMMAND_H
