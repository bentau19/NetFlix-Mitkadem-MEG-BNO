//
// Created by User on 12/12/2024.
//

#include <stdexcept>
#include "Post.h"
#include "File_Classes/StringHandler.h"
#include "Commands/Data_Manipulation/Get.h"
#include "File_Classes/AddBuilder.h"
#include "Commands/General/Validity.h"

Post::Post() {}

// Destructor definition
Post::~Post() {}


// Method to execute with a string parameter
std::string Post::execute(std::string str) {
    try {
        UserFile userFile;
        //init the data
        unsigned long usrId;
        std::vector<std::string> userMovies = Validity::UserMoviesStringHandler(str, &usrId);
        if (!Get::isExist(usrId, &userFile))
            AddBuilder::BuildAdd(usrId, userMovies);
        else throw std::invalid_argument("");
    }
    catch (...){
        return Validity::ValidityAlert(GenFail);
    }
    return Validity::ValidityAlert(PostSuc);
}





