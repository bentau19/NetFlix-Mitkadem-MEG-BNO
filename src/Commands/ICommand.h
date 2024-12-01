#ifndef ICOMMAND_H
#define ICOMMAND_H
using namespace std;
#include <string>

class ICommand {
    public:
    //every command will have execute func we will run in the app
     virtual void execute(std::string str) = 0;
};

#endif