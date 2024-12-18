using namespace std;
#include "HelpCmd.h"
#include "Validity.h"
#include <string>

    HelpCmd::HelpCmd(){}

    HelpCmd::~HelpCmd(){}
std::string HelpCmd::execute(std::string str){
        if(str.length() > 0 || str==" ") { //if got more agument then "help" then invalid
            return Validity::ValidityAlert(syntaxErr);
        } else {
            //help command
            std::string res=
           "DELETE, arguments: [userid] [movieid1] [movieid2] ...\n"
            "GET, arguments: [userid] [movieid]\n"
            "PATCH, arguments: [userid] [movieid1] [movieid2] ...\n"
            "POST, arguments: [userid] [movieid1] [movieid2] ...\n"
            "HELP\n";
            return res;
        }
    }

