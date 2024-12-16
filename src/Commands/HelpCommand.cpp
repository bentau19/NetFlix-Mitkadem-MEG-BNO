using namespace std;
#include "HelpCommand.h"
#include <iostream>
#include <fstream>
#include <string>

    HelpCommand::HelpCommand(){}

    HelpCommand::~HelpCommand(){}
     void HelpCommand::execute(std::string str){
        if(str.length() > 0 || str==" ") { //if got more agument then "help" then invalid
            throw std::invalid_argument("");
        } else {
            //help command
        cout << "add[userid] [movieid1] [movieid2]…" << endl;
        cout << "recommend[userid] [movieid]" <<endl;
        cout << "help" <<endl;
        }
    
    }

