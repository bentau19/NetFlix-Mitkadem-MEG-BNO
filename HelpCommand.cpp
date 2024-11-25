using namespace std;
#include "ICommand.h"
#include <iostream>
#include <fstream>
class HelpCommand : public ICommand {
private:
    /* data */
public:
    HelpCommand();
    ~HelpCommand();
    void execute(){
        cout << "add[userid] [movieid1] [movieid2]…" << endl;
        cout << "recommend[userid] [movieid]" <<endl;
        cout << "help" <<endl;
    }
    void execute(string str){
        if(str.length() > 1) {
         //   throw exception;
        }
        cout << "add[userid] [movieid1] [movieid2]…" << endl;
        cout << "recommend[userid] [movieid]" <<endl;
        cout << "help" <<endl;
    }
};

HelpCommand::HelpCommand()
{
}

HelpCommand::~HelpCommand()
{
}
