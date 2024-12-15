#ifndef ADDCOMMAND_H
#define ADDCOMMAND_H
#include <vector>
#include "../Commands/ICommand.h"
 // Include ICommand header

class AddCommand : public ICommand {
public:
    AddCommand();  // Constructor
    ~AddCommand(); // Destructor

    string execute();  // Override execute method with no parameters
    string execute(std::string str) override;  // Override execute method with string parameter

};

#endif // ADD_H