#include "Add.h"
#include "../File_Classes/AddBuilder.h"
#include <iostream>
#include <vector>
#include <sstream>  // Include the sstream header
// Constructor definition
Add::Add() {
    std::cout << "Add object created!" << std::endl;
}

// Destructor definition
Add::~Add() {
    std::cout << "Add object destroyed!" << std::endl;
}

// Method to execute with no parameters
void Add::execute() {
    std::cout << "Executing add command with no arguments!" << std::endl;
}

// Method to execute with a string parameter
void Add::execute(std::string str) {
    std::vector<std::string> userMovies = splitString(str);
    if(userMovies.size()<2) throw std::invalid_argument("");
    int usrId = std::atoi(userMovies[0].c_str());
    userMovies.erase(userMovies.begin());
    AddBuilder a;
    a.BuildAdd(usrId, userMovies);
}

std::vector<std::string> Add::splitString(const std::string& str) {
    std::vector<std::string> res;
    std::stringstream ss(str);
    std::string item;
    try{
        while (getline(ss, item, ' ')) {
            if (!item.empty()) {
                for (char ch : item) {
                    if (!isdigit(ch)) {
                        throw std::invalid_argument("");  // Found a non-digit character
                    }
                }
                res.push_back(item);
            }
        }}catch(...){
        throw std::invalid_argument("");
    }
    return res;
}