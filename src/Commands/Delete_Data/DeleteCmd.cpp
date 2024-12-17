//
// Created by User on 15/12/2024.
//

#include "DeleteCmd.h"
#include "File_Classes/AddBuilder.h"
#include "Commands/General/Validity.h"

DeleteCmd::DeleteCmd(){}
DeleteCmd::~DeleteCmd(){}

std::string DeleteCmd::execute(string str) {
    try {
        //init the data
        unsigned long usrId;
        std::vector<std::string> userMovies = Validity::UserMoviesStringHandler(str, &usrId);

        bool flag = AddBuilder::BuildRemove(usrId, userMovies);
        if (!flag)throw std::invalid_argument(ERR404);
    } catch (const std::invalid_argument& e) {
    if (e.what() == std::string(ERR404)) {
        return Validity::ValidityAlert(GenFail);
    } else {
        return Validity::ValidityAlert(syntaxErr);
    }
}
    return Validity::ValidityAlert(DeleteSuc);
}