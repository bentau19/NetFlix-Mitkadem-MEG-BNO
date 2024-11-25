#include <iostream>
#include <fstream>
#include <map>
#include "ICommand.h"
#include "HelpCommand.cpp"
#include "AddCommand.cpp"
using namespace std;
map <string, ICommand*> commands;

void run() {
   // ICommand* help = new HelpCommand();
  //  commands["help"] = help;
    commands["help"] = new HelpCommand();
  //  commands["add"] = new AddCommand();
  //  commands["recommend %d %s"];
    int userid;
    string task;
    string com;
    while (true)
    {
        cin >> task;
        com = task[0];
        try{
          commands[com] -> execute(task);
        }
        catch(...) {

        }
    }
    
}