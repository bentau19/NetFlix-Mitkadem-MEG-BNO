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
       int userid = str[1];
       int* movies = new int[str.length() - 2];
       for (size_t i = 2; i < str.length(); i++)
       {
          movies[i-2] = str[i];
       }

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

