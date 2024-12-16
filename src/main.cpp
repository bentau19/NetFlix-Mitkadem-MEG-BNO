#include <iostream>
#include <fstream>
#include <map>
#include "Commands/General/ICommand.h"
#include "Menu/ConsoleMenu.h"
#include "Commands/General/HelpCommand.h"

#include "App/App.h"
#include "Commands/Data_Manipulation/Get.h"
#include "Commands/Add_Data/Post.h"
#include <stdexcept>

using namespace std;
map <string, ICommand*> commands;


int main(){
     ICommand* addCommand = new Post();
    commands["add"] = addCommand; //make add command
    ICommand* helpCommand = new Help();
    commands["help"] = helpCommand; //make help command
    ICommand* recCommand = new Get();
    commands["recommend"] = recCommand; //make reccomadtion command
    App app(new ConsoleMenu(), commands); //give app the commands we made and the menu we have
    app.run(); //run the pogram

   //delete the commands
  delete addCommand;
  delete helpCommand;
  delete recCommand;
}