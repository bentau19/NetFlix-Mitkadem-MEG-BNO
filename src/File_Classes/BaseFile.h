#ifndef BASEFILE_H
#define BASEFILE_H

#include <fstream>
#include <string>
#include <vector>
#include <iostream>
#include "IFile.h"
#include <mutex>
#include <shared_mutex>


class BaseFile : public IFile {
protected:
    std::fstream file;
    std::string fileName;
    bool    OpenLock;

    static std::mutex fileMutex;
private:

    std::string loc;
public:
    // Constructor: Initialize the file name
    BaseFile(const std::string name);

    // Destructor: Close the file if it's open
    virtual ~BaseFile() override;

    // Create a file
    void create(const std::string name) override;

    // Open the file in the specified mode
    void openFile(std::ios::openmode mode) override;

    // Delete a file
    void deleteItem() override;

    // Display file content
    std::string display() override;

    // Check if a file exists
    bool doesFileExist() override;

    // Read file content and return as a string
    std::vector<std::string> read() override;

    // Write to the file
    void Write(const std::string line) override;

    // Get the file name
    std::string GetName() const override;
};

#endif // BASEFILE_H
