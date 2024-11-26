#ifndef MOVIEFILE_H
#define MOVIEFILE_H

#include "IFile.h"
#include <iostream>
#include <filesystem>
#include <fstream>
#include <string>
#include <stdexcept>

class MovieFile : public IFile {
private:
    void create(const std::string name) override;     // Create a file with the specified name
    void deleteItem(const std::string name) override; // Delete the specified file
public:
    // Constructor that initializes fileName and creates the file
    MovieFile(const std::string name);
    ~MovieFile() {                // Destructor to close file if it's open
        if (file.is_open()) {
            file.close();
        }
    }
    void Write(std::string Line);
    void display() override;                          // Display file contents
    std::string GetName();
    bool doesFileExist();
};

#endif
