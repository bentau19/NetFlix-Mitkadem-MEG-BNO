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
enum ErrorType {
    PostSuc,
    PatchSuc,
    DeleteSuc,
    GetSuc,
    GenFail

};

class Validity {
public:
    static std::vector<std::string> SplitToDigitVec (const std::string& str){
        return StringHandler::splitString(str);
    }
    static std::vector<std::string> twoNumsVec(std::string str){
        std::vector<std::string> data = SplitToDigitVec(str);
        if(data.size()!=2)throw std::invalid_argument("");
        return data;
    }
    static std::vector<std::string> UserMoviesStringHandler(std::string str, unsigned long *id) {
        std::vector<std::string> userMovies = SplitToDigitVec(str);
        if (userMovies.size() < 2) throw std::invalid_argument("");
        *id = stoul(userMovies[0]);
        userMovies.erase(userMovies.begin());
        return userMovies;
    }

    static std::string ValidityAlert(ErrorType type){
        switch (type){
            case PostSuc: return SUC201;
            case PatchSuc: return SUC204;
            case DeleteSuc: return SUC204;
            case GetSuc: return SUC200;
            case GenFail: return ERR404;
        }

    }

    };

#endif //NETFLIX_PROJECT_VALIDITY_H
