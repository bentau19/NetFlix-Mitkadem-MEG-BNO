#ifndef ICommand_H
#define ICommand_H
using namespace std;

class ICommand {
    public:
    //every command will have execute func we will run in the app
     virtual void execute(std::string str) = 0;
};

#endif