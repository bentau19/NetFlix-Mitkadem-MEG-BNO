using namespace std;
#include "ICommand.h"
#include <iostream>
#include <fstream>
class AddCommand : public ICommand
{
private:
    /* data */
public:
    AddCommand();
    ~AddCommand();
     void execute(string str){
        cout<<str<<endl;

    }
    void execute(){
       
    }
};

AddCommand::AddCommand()
{

    
}

AddCommand::~AddCommand()
{
}

