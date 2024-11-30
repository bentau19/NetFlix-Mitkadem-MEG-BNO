#ifndef ADD_H
#define ADD_H
#include <vector>
#include "ICommand.h"  // Include ICommand header

class Add : public ICommand {
public:
    Add();  // Constructor
    ~Add(); // Destructor

    void execute() override;  // Override execute method with no parameters
    void execute(std::string str) override;  // Override execute method with string parameter

};

#endif // ADD_H