#include "BaseFile.h"
#include <iostream>
#include <fstream>
#include <string>
#include <stdexcept>
// Constructor: Initialize the file name
BaseFile::BaseFile(const std::string& name){
    create(name);
}

// Destructor: Close the file if it's open
BaseFile::~BaseFile() {
    if (file.is_open()) {
        file.close();
    }
}

// Create a file
void BaseFile::create(const std::string name) {
    if(doesFileExist){
        return;
    }
    std::ofstream outFile(name);
    if (!outFile) {
        throw std::ios_base::failure("Failed to create file: " + name);
    }
    outFile.close();
}

// Open the file in the specified mode
void BaseFile::openFile(std::ios::openmode mode) {
    if (!file.is_open()) {
        file.open(fileName, mode);
        if (!file) {
            throw std::ios_base::failure("base Failed to open file: " + fileName);
        }
    }
}

// Delete a file
void BaseFile::deleteItem() {
    std::string name = fileName;
    if (doesFileExist()) {
        if (std::remove(name.c_str()) != 0) {  // Use std::remove to delete the file
            throw std::ios_base::failure("Failed to delete file: " + name);
        }
    } else {
        throw std::invalid_argument("File does not exist: " + name);
    }
}

// Display file content
void BaseFile::display() {
    openFile(std::ios::in);
    std::string line;
    while (std::getline(file, line)) {
        std::cout << line << std::endl;
    }
    file.close();
}

// Check if a file exists
bool BaseFile::doesFileExist() {
    std::ifstream testFile(fileName);
    bool exists = testFile.good();  // Check if the file stream can be opened
    testFile.close();
    return exists;
}

// Read file content and return as a string
std::vector<std::string> BaseFile::read() {
    std::vector<std::string> lines;
    std::string line;
    try {
        openFile(std::ios::in);  // Open the file in input mode
        while (std::getline(file, line)) {
            lines.push_back(line);  // Add each line to the vector
        }
        file.close();
    } catch (const std::ios_base::failure& e) {
        std::cerr << "Errorbase: " << e.what() << std::endl;
    }
    return lines;  // Return the vector of lines
}

//write to the file
void BaseFile::Write(std::string Line) {
    try {
        doesFileExist();
        openFile(std::ios::out | std::ios::app);  //file in append
        file << Line << std::endl;               // Write the line
        file.close();
    } catch (const std::ios_base::failure& e) {  
        std::cerr << "Error: " << e.what() << std::endl;
    }
}

std::string BaseFile::GetName()
{
    return fileName;
}
