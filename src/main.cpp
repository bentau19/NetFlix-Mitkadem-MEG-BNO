#include <iostream>
#include <fstream>
#include <map>
#include "Commands/ICommand.h"
#include "Menu/ConsoleMenu.h"
#include "Commands/HelpCommand.h"
#include "Commands/Add.h"
#include "App/App.h"
#include "Commands/GET.h"
#include <stdexcept>

using namespace std;
map <string, ICommand*> commands;


int main(){
     ICommand* addCommand = new Add();
    commands["add"] = addCommand; //make add command
    ICommand* helpCommand = new Help();
    commands["help"] = helpCommand; //make help command
    ICommand* recCommand = new GET();
    commands["recommend"] = recCommand; //make reccomadtion command
    App app(new ConsoleMenu(), commands); //give app the commands we made and the menu we have
    app.run(); //run the pogram

   //delete the commands
  delete addCommand;
  delete helpCommand;
  delete recCommand;
}