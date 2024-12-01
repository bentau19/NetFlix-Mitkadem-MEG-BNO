#include <iostream>
#include <fstream>
#include <map>
#include "ICommand.h"
#include "ConsoleMenu.cpp"
#include "HelpCommand.cpp"
#include "AddCommand.cpp"
#include "App.cpp"
#include "RecomedionCommand.cpp"
using namespace std;
map <string, ICommand*> commands;


int main(){
     ICommand* addCommand = new AddCommand();
    commands["add"] = addCommand; //make add command
    ICommand* helpCommand = new HelpCommand();
    commands["help"] = helpCommand; //make help command
    ICommand* recCommand = new RecomedionCommand();
    commands["recommendation"] = recCommand; //make reccomadtion command
    App app(new ConsoleMenu(), commands); //give app the commands we made and the menu we have
    app.run(); //run the pogram

   //delete the commands
  delete addCommand;
  delete helpCommand;
  delete recCommand;

}