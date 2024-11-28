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
    commands["Add"] = addCommand;

    ICommand* helpCommand = new HelpCommand();
    commands["Help"] = helpCommand;
      ICommand* recCommand = new RecomedionCommand();
    commands["recomadetion"] = recCommand;
  App app(new ConsoleMenu(), commands);
  app.run();

  delete addCommand;
  delete helpCommand;
  delete recCommand;

}