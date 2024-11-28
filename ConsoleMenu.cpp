using namespace std;
#include <string>
#include "IMenu.h"
#include <iostream>
#include <fstream>
#include "stringhandler.cpp"
class ConsoleMenu : public IMenu
{
private:
    /* data */
public:
    ConsoleMenu(/* args */);
    ~ConsoleMenu();
    string nextCommand(){
        string task;
        getline(cin, task);
        return task;
    }

    string getCommand(string task){
          std::string firstWord;
          size_t i = 0;

          // Skip leading spaces
        while (i < task.length() && task[i] == ' ') {
             ++i;
         }

         // Extract first word
          for (; i < task.length() && task[i] != ' '; ++i) {
            firstWord += task[i];
         }

         return firstWord;
    }

string getCommandAsk(string task) {
    vector<string> vecString = StringHandler::split(task,' ');
    vecString.erase(vecString.begin());
    string remaining = StringHandler::join(vecString, ' ');
    return remaining;
}



};

ConsoleMenu::ConsoleMenu(/* args */)
{
}

ConsoleMenu::~ConsoleMenu()
{
}
