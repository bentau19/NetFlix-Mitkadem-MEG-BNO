using namespace std;
#include "IMenu.h"
#include <iostream>
#include <fstream>
class ConsoleMenu : public IMenu
{
private:
    /* data */
public:
    ConsoleMenu(/* args */);
    ~ConsoleMenu();
    string nextCommand(){
        string task;
        cin >> task;
        return task;
    }

    string getCommand(string task){
         std::string firstWord;
         size_t i = 0;

       // Extract first word
       for (; i < task.length() && task[i] != ' '; ++i) {
          firstWord += task[i];
         }
         return firstWord;
    }

    string getCommandAsk(string task) {
        size_t i = 0;

       // Extract first word
       for (; i < task.length() && task[i] != ' '; ++i) {}
       std::string remainingString = (i < task.length()) ? task.substr(i + 1) : "";
       return remainingString;
    }
};

ConsoleMenu::ConsoleMenu(/* args */)
{
}

ConsoleMenu::~ConsoleMenu()
{
}
