#include "AddCommand.h"
#include "../File_Classes/AddBuilder.h"
<<<<<<< HEAD
#include "File_Classes/StringHandler.h"
=======
#include "../../src/StringTools/StringTools.h"
>>>>>>> 8aa9cee4dad35b4020b58f4c37f496d3d29df6ff
#include <iostream>
#include <vector>
#include <sstream>  // Include the sstream header
// Constructor definition
AddCommand::AddCommand() {}

// Destructor definition
AddCommand::~AddCommand() {}

// Method to execute with no parameters
void AddCommand::execute() {
    throw std::invalid_argument("");
}

// Method to execute with a string parameter
void AddCommand::execute(std::string str) {
<<<<<<< HEAD
    std::vector<std::string> userMovies = StringHandler::splitString(str);
    if(userMovies.size()<2) throw std::invalid_argument("");
    int usrId = std::atoi(userMovies[0].c_str());
    userMovies.erase(userMovies.begin());
    AddBuilder::BuildAdd(usrId, userMovies);
}
=======
    std::vector<std::string> userMovies = StringTools::splitString(str); // make the commend vector {userid,movies...}
    if(userMovies.size()<2) throw std::invalid_argument(""); // check if the length of the command is valid
    int usrId = std::atoi(userMovies[0].c_str()); // separate the userid from vector
    userMovies.erase(userMovies.begin()); // get the vector one step to delete the userid
    AddBuilder::BuildAdd(usrId, userMovies); // insert all to the database
}

>>>>>>> 8aa9cee4dad35b4020b58f4c37f496d3d29df6ff
