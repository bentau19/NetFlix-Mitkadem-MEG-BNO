#include "BaseFile.h"

// Constructor: Initialize the file name
BaseFile::BaseFile(const std::string& name) : fileName(name) {}

// Destructor: Close the file if it's open
BaseFile::~BaseFile() {
    file.close();
}
// Create a file
void BaseFile::create(const std::string& name) {
    std::ofstream outFile(name);
    if (!outFile) {
        throw std::ios_base::failure("Failed to create file: " + name);
    }
    outFile.close();
}
// Open the file r/w mode
void BaseFile::openFile(std::ios::openmode mode) {
    if (!file.is_open()) {
        file.open(fileName, mode);
        if (!file) {
            throw std::ios_base::failure("Failed to open file: " + fileName);
        }
    }
}
// Delete a file
void BaseFile::deleteItem(const std::string& name) {
    if (std::filesystem::exists(name)) {
        std::filesystem::remove(name);
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
bool BaseFile::doesFileExist(const std::string& name) {
    return std::filesystem::exists(name);
}
