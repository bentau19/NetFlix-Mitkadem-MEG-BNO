using namespace std;
#include "HelpCmd.h"
#include "Validity.h"
#include <string>
#include <algorithm>

HelpCmd::HelpCmd(){}

    HelpCmd::~HelpCmd(){}
std::string HelpCmd::execute(std::string str){
        if(str.length() > 0 && !std::all_of(str.begin(), str.end(), [](char c) { return c == ' '; })) { //if got more agument then "help" then invalid
            return Validity::ValidityAlert(syntaxErr);
        } else {
            //help command
            std::string res=Validity::ValidityAlert(GetSuc)+"\n"
           "DELETE, arguments: [userid] [movieid1] [movieid2] ...\n"
            "GET, arguments: [userid] [movieid]\n"
            "PATCH, arguments: [userid] [movieid1] [movieid2] ...\n"
            "POST, arguments: [userid] [movieid1] [movieid2] ...\n"
            "help\n";
            return res;
        }
    }

