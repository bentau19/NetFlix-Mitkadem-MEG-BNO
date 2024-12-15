using namespace std;
#include "HelpCommand.h"
#include <iostream>
#include <fstream>
#include <string>

    HelpCommand::HelpCommand(){}

    HelpCommand::~HelpCommand(){}
     string HelpCommand::execute(std::string str){
        if(str.length() > 0 || str==" ") { //if got more agument then "help" then invalid
            return " 400 Bad Request";
        } else {
            //help command
        string toPrint =  "add[userid] [movieid1] [movieid2]…\n";
        toPrint += "recommend[userid] [movieid]\n";
        toPrint += "help\n";
        return toPrint;
        }
    
    }

