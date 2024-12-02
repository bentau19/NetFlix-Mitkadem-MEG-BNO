#ifndef ADDCOMMAND_H
#define ADDCOMMAND_H
#include <vector>
#include "../Commands/ICommand.h"
 // Include ICommand header

class AddCommand : public ICommand {
public:
    AddCommand();  // Constructor
    ~AddCommand(); // Destructor

    void execute();  // Override execute method with no parameters
    void execute(std::string str) override;  // Override execute method with string parameter

};

#endif // ADD_H