using namespace std;
#include <string>
#include "ConsoleMenu.h"
#include <iostream>
#include <fstream>
#include "File_Classes/StringHandler.h"

ConsoleMenu::ConsoleMenu(/* args */)
{
}

ConsoleMenu::~ConsoleMenu()
{
}
    string ConsoleMenu::nextCommand(){
        string task;
        getline(cin, task); //take next user input
        return task; //return the input
    }

    string ConsoleMenu::getCommand(string task){
        vector<string> vecString = StringHandler::split(task,' ');
        if (!vecString.empty()) {//if have words
            return vecString[0];  //return first Word
        } 
        return ""; //else is empty string
    }

string ConsoleMenu::getCommandAsk(string task) {
    vector<string> vecString = StringHandler::split(task,' ');
    vecString.erase(vecString.begin()); //erse the first word from string
    string remaining = StringHandler::join(vecString, ' '); //return the vector to string
    return remaining; //return the string
}


