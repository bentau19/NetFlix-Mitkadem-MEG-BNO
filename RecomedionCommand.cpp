using namespace std;
#include "ICommand.h"
#include <iostream>
#include <fstream>

class RecomedionCommand : public ICommand
{
private:
    /* data */
public:
    RecomedionCommand(/* args */);
    ~RecomedionCommand();

    void execute(string str){
        cout<<str<<endl;
    }
    void execute(){}
};

RecomedionCommand::RecomedionCommand(/* args */)
{
}

RecomedionCommand::~RecomedionCommand()
{
}
