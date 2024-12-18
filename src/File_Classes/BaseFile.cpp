#include "BaseFile.h"
#include <stdexcept>
#include <iostream>
#include <fstream>

std::mutex BaseFile::fileMutex;  // Define the static mutex

// Constructor
BaseFile::BaseFile(const std::string name) {
    std::string DATA_DIR = "/app/data/";
    loc = DATA_DIR + name;
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
    if (doesFileExist()) {
        return;
    }
    fileMutex.lock();
    std::ofstream outFile(loc);
    if (!outFile) {
        throw std::ios_base::failure("Failed to create file: " + loc);
    }
    outFile.close();
    fileMutex.unlock();
}

// Open the file in the specified mode
void BaseFile::openFile(std::ios::openmode mode) {
    fileMutex.lock();  // Lock the mutex for thread safety
    if (!file.is_open()) {
        file.open(loc, mode);
        if (!file) {
            throw std::ios_base::failure("Failed to open file: " + loc);
        }
    }
    fileMutex.unlock();  // Unlock the mutex after the operation
}

// Delete a file
void BaseFile::deleteItem() {
    if (doesFileExist()) {
        fileMutex.lock();  // Lock the mutex for thread safety
        if (std::remove(loc.c_str()) != 0) {  // Use std::remove to delete the file
            throw std::ios_base::failure("Failed to delete file: " + loc);
        }
    } else {
        throw std::invalid_argument("File does not exist: " + loc);
    }
    fileMutex.unlock();  // Unlock after operation
}

// Display file content
std::string BaseFile::display() {
    fileMutex.lock();  // Lock the mutex for thread safety
    openFile(std::ios::in);
    std::string line;
    std::string alline;
    while (std::getline(file, line)) {
        alline += line;
    }
    file.close();
    fileMutex.unlock();  // Unlock after operation
    return alline;
}

// Check if a file exists
bool BaseFile::doesFileExist() {
    fileMutex.lock();  // Lock the mutex for thread safety
    std::ifstream testFile(loc);
    bool exists = testFile.good();
    testFile.close();
    fileMutex.unlock();  // Unlock after operation
    return exists;
}

// Read file content and return as a string
std::vector<std::string> BaseFile::read() {
    std::vector<std::string> lines;
    std::string line;
    try {
        openFile(std::ios::in);  // Open the file in input mode
        fileMutex.lock();  // Lock the mutex for thread safety
        while (std::getline(file, line)) {
            lines.push_back(line);  // Add each line to the vector
        }
        file.close();
    } catch (const std::ios_base::failure& e) {
    }
    fileMutex.unlock();  // Unlock after operation
    return lines;  // Return the vector of lines
}

// Write to the file
void BaseFile::Write(std::string Line) {
    try {
        openFile(std::ios::out | std::ios::app);  // Open file in append mode
        fileMutex.lock();  // Lock the mutex for thread safety
        file << Line << std::endl;  // Write the line
        file.close();
    } catch (const std::ios_base::failure& e) {
    }
    fileMutex.unlock();  // Unlock after operation
}

// Get the file name
std::string BaseFile::GetName() const {
    return loc;
}
