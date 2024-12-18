//
// Created by User on 15/12/2024.
//

#ifndef NETFLIX_PROJECT_VALIDITY_H
#define NETFLIX_PROJECT_VALIDITY_H


#include <vector>
#include <string>
#include <stdexcept>
#include "File_Classes/StringHandler.h"

#define SUC201 "201 Created \n"
#define SUC204 "204 No Content \n"
#define SUC200 "200 Ok \n"
#define ERR404 "404 Not Found \n"
#define ERR400 "400 Bad Request\n"
enum ErrorType {
    PostSuc,
    PatchSuc,
    DeleteSuc,
    GetSuc,
    GenFail,
    syntaxErr
};

class Validity {
public:
    static std::vector<std::string> SplitToDigitVec (const std::string& str);
    static std::vector<std::string> twoNumsVec(std::string str);
    static std::vector<std::string> UserMoviesStringHandler(std::string str, unsigned long *id) ;

    static std::string ValidityAlert(ErrorType type);
};
#endif //NETFLIX_PROJECT_VALIDITY_H
