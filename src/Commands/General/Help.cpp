using namespace std;
#include "HelpCmd.h"
#include <iostream>
#include <fstream>
#include <string>

    Help::Help(){}

    Help::~Help(){}
std::string Help::execute(std::string str){
        if(str.length() > 0 || str==" ") { //if got more agument then "help" then invalid
            throw std::invalid_argument("");
        } else {
            //help command
        cout << "DELETE, arguments: [userid] [movieid1] [movieid2] ..." << endl;
            cout << "GET, arguments: [userid] [movieid]" << endl;
            cout << "PATCH, arguments: [userid] [movieid1] [movieid2] ..." << endl;
            cout << "POST, arguments: [userid] [movieid1] [movieid2] ..." << endl;
            cout << "HELP" <<endl;
        }
    return "";
    }

