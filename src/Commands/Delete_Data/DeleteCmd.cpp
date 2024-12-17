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
        if (!flag)throw std::invalid_argument("404");
    }
    catch (...){
        return Validity::ValidityAlert(GenFail);
    }
    return Validity::ValidityAlert(DeleteSuc);
}