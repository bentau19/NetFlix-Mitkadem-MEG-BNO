#ifndef ICommand_H
#define ICommand_H
using namespace std;

class ICommand {
    public:
     virtual void execute(std::string str) = 0;

};

#endif