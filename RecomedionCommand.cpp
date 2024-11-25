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
       int userid = str[1];
       int* movies = new int[str.length() - 2];
       for (size_t i = 2; i < str.length(); i++)
       {
          movies[i-2] = str[i];
       }

    }
    void execute(){}
};

RecomedionCommand::RecomedionCommand(/* args */)
{
}

RecomedionCommand::~RecomedionCommand()
{
}
