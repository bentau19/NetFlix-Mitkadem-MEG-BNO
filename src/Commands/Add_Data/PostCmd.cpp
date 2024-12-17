//
// Created by User on 12/12/2024.
//

#include <stdexcept>
#include "PostCmd.h"
#include "File_Classes/StringHandler.h"
#include "Commands/Data_Manipulation/GetCmd.h"
#include "File_Classes/AddBuilder.h"
#include "Commands/General/Validity.h"

PostCmd::PostCmd() {}

// Destructor definition
PostCmd::~PostCmd() {}


// Method to execute with a string parameter
std::string PostCmd::execute(std::string str) {
    try {
        UserFile userFile;
        //init the data
        unsigned long usrId;
        std::vector<std::string> userMovies = Validity::UserMoviesStringHandler(str, &usrId);
        if (!GetCmd::isExist(usrId, &userFile))
            AddBuilder::BuildAdd(usrId, userMovies);
        else throw std::invalid_argument("");
    }
    catch (...){
        return Validity::ValidityAlert(GenFail);
    }
    return Validity::ValidityAlert(PostSuc);
}





