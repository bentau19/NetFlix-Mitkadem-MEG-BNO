#include "Add.h"
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
    std::cout << "Executing add command with argument: " << str << std::endl;
}

std::vector<std::string> Add::splitString(const std::string& str) {
    std::vector<std::string> res;
    std::stringstream ss(str);
    std::string item;
    while (getline(ss, item, ' ')) {
        if (!item.empty()) {
            res.push_back(item);
        }
    }
    return res;
}