//
// Created by User on 12/12/2024.
//

#include <stdexcept>
#include "PatchCmd.h"
#include "File_Classes/UserFile.h"
#include "File_Classes/StringHandler.h"
#include "Commands/Data_Manipulation/GetCmd.h"
#include "PostCmd.h"
#include "Commands/General/Validity.h"

PatchCmd::PatchCmd() {}

// Destructor definition
PatchCmd::~PatchCmd() {}


// Method to execute with a string parameter
std::string PatchCmd::execute(std::string str) {
    try {
        UserFile userFile;
        //init the data
        unsigned long usrId;
        std::vector<std::string> userMovies = Validity::UserMoviesStringHandler(str, &usrId);

        if (FileIO::isExists(usrId, &userFile))
            AddBuilder::BuildAdd(usrId, userMovies);
        else throw std::invalid_argument(ERR404);
    }catch(const std::invalid_argument& e){
    if (e.what() == std::string(ERR404)) {
        return Validity::ValidityAlert(GenFail);
    } else {
        return Validity::ValidityAlert(syntaxErr);
    }
}
    return Validity::ValidityAlert(PatchSuc);
    }

