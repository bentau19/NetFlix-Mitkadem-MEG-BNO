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
        if (!FileIO::isExists(usrId, &userFile))
            AddBuilder::BuildAdd(usrId, userMovies);
        else throw std::invalid_argument(ERR404);
    }catch(const std::invalid_argument& e){
    if (e.what() == std::string(ERR404)) {
        return Validity::ValidityAlert(GenFail);
    } else {
        return Validity::ValidityAlert(syntaxErr);
    }
}
    return Validity::ValidityAlert(PostSuc);
}





