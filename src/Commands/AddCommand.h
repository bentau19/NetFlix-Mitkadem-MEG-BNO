<<<<<<< HEAD
#ifndef ADDCOMMAND_H
#define ADDCOMMAND_H
#include <vector>
#include "../Commands/ICommand.h"
 // Include ICommand header
=======
#ifndef ADD_H
#define ADD_H
#include <vector>
#include "ICommand.h"  // Include ICommand header
>>>>>>> 8aa9cee4dad35b4020b58f4c37f496d3d29df6ff

class AddCommand : public ICommand {
public:
    AddCommand();  // Constructor
    ~AddCommand(); // Destructor

<<<<<<< HEAD
    void execute();  // Override execute method with no parameters
=======
    void execute() override;  // Override execute method with no parameters
>>>>>>> 8aa9cee4dad35b4020b58f4c37f496d3d29df6ff
    void execute(std::string str) override;  // Override execute method with string parameter

};

#endif // ADD_H