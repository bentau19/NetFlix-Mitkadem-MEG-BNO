#ifndef ICOMMAND_H
#define ICOMMAND_H
<<<<<<< HEAD
using namespace std;
#include <string>

class ICommand {
    public:
    //every command will have execute func we will run in the app
     virtual void execute(std::string str) = 0;
};

#endif
=======

#include <string>

class ICommand {
public:
    virtual void execute() = 0;  // Method with no parameters
    virtual void execute(std::string str) = 0;  // Method with a string parameter
};

#endif // ICOMMAND_H
>>>>>>> 8aa9cee4dad35b4020b58f4c37f496d3d29df6ff
