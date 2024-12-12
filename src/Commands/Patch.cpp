//
// Created by User on 12/12/2024.
//

#include <stdexcept>
#include "Patch.h"
#include "File_Classes/UserFile.h"
#include "File_Classes/StringHandler.h"
#include "GET.h"
#include "Post.h"

Patch::Patch() {}

// Destructor definition
Patch::~Patch() {}

// Method to execute with no parameters
void Patch::execute() {
    throw std::invalid_argument("");
}

// Method to execute with a string parameter
void Patch::execute(std::string str) {
    UserFile userFile;
    std::vector<std::string> userMovies = StringHandler::splitString(str);
    if(userMovies.size()<2) throw std::invalid_argument("");
    unsigned long usrId = stoul(userMovies[0]);
    userMovies.erase(userMovies.begin());
    if (GET::isExist(usrId,&userFile))
        Post::addAct(userMovies,usrId);
    else throw std::invalid_argument("");
}