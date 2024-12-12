#include "Add.h"
#include "../File_Classes/AddBuilder.h"
#include "File_Classes/StringHandler.h"
#include <iostream>
#include <vector>
#include <sstream>  // Include the sstream header
// Constructor definition
Add::Add() {}

// Destructor definition
Add::~Add() {}

// Method to execute with no parameters
void Add::execute() {
    throw std::invalid_argument("");
}

// Method to execute with a string parameter
void Add::execute(std::string str) {
    std::vector<std::string> userMovies = StringHandler::splitString(str);
    if(userMovies.size()<2) throw std::invalid_argument("");
    unsigned long usrId = stoul(userMovies[0]);
    userMovies.erase(userMovies.begin());
    AddBuilder::BuildAdd(usrId, userMovies);
}

