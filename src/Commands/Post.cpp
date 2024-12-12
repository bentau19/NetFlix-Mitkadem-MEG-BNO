//
// Created by User on 12/12/2024.
//

#include <stdexcept>
#include "Post.h"
#include "File_Classes/StringHandler.h"
#include "Commands/GET.h"
#include "File_Classes/AddBuilder.h"

Post::Post() {}

// Destructor definition
Post::~Post() {}

// Method to execute with no parameters
void Post::execute() {
    throw std::invalid_argument("");
}

// Method to execute with a string parameter
void Post::execute(std::string str) {
    UserFile userFile;
    std::vector<std::string> userMovies = StringHandler::splitString(str);
    if(userMovies.size()<2) throw std::invalid_argument("");
    unsigned long usrId = stoul(userMovies[0]);
    userMovies.erase(userMovies.begin());
    if (!GET::isExist(usrId,&userFile))
    addAct(userMovies,usrId);
    else throw std::invalid_argument("");
}





