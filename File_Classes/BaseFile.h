#ifndef BASEFILE_H
#define BASEFILE_H
#include "IFile.h"
#include <iostream>
#include <fstream>
#include <filesystem>
#include <string>
#include <stdexcept>

class BaseFile : public IFile {
protected:
    std::fstream file;         // File stream for file operations
    std::string fileName;      // Name of the file
    void openFile(std::ios::openmode mode); // Open the file


public:
    // Constructor and destructor
    explicit BaseFile(const std::string& name);
    virtual ~BaseFile();

    // File operations
    virtual void create(const std::string& name);          // Create a file
    virtual void deleteItem(const std::string& name);      // Delete a file
    virtual void display();                                // Display file content
    static bool doesFileExist(const std::string& name);    // Check if file exists

    // Abstract method for writing to the file
    virtual void write(std::string line) = 0;
    //Abstract methode for reading (to read for example 
    //the movies normaly but for the user only its movies withough its name)
    virtual std::string read();
};

#endif // BASEFILE_H
