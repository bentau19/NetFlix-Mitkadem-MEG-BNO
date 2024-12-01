#include <iostream>
#include <fstream>
#include <map>
#include "Commands/ICommand.h"
#include "ConsoleMenu.h"
#include "Commands/HelpCommand.h"
#include "Commands/AddCommand.h"
#include "App.h"
#include "Commands/RecommendCommand.h"
#include <stdexcept>

using namespace std;
map <string, ICommand*> commands;


int main(){
     ICommand* addCommand = new AddCommand();
    commands["add"] = addCommand; //make add command
    ICommand* helpCommand = new HelpCommand();
    commands["help"] = helpCommand; //make help command
    ICommand* recCommand = new RecommendCommand();
    commands["recommend"] = recCommand; //make reccomadtion command
    App app(new ConsoleMenu(), commands); //give app the commands we made and the menu we have
    app.run(); //run the pogram

   //delete the commands
  delete addCommand;
  delete helpCommand;
  delete recCommand;
}