//
// Created by User on 18/12/2024.
//

#include "Validity.h"
 std::vector<std::string> Validity::SplitToDigitVec (const std::string& str){
    return StringHandler::splitString(str);
}
 std::vector<std::string> Validity::twoNumsVec(std::string str){
    std::vector<std::string> data = SplitToDigitVec(str);
    if(data.size()!=2)throw std::invalid_argument(ERR400);
    return data;
}
 std::vector<std::string> Validity::UserMoviesStringHandler(std::string str, unsigned long *id) {
    std::vector<std::string> userMovies = SplitToDigitVec(str);
    if (userMovies.size() < 2) throw std::invalid_argument(ERR400);
    *id = stoul(userMovies[0]);
    userMovies.erase(userMovies.begin());
    return userMovies;
}

 std::string Validity::ValidityAlert(ErrorType type){
    switch (type){
        case PostSuc: return SUC201;
        case PatchSuc: return SUC204;
        case DeleteSuc: return SUC204;
        case GetSuc: return SUC200;
        case GenFail: return ERR404;
        case syntaxErr: return ERR400;
        default: return ERR400;
    }

}