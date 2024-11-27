#ifndef BASEFILE_H
#define BASEFILE_H

#include "IFile.h"
#include <fstream>
#include <iostream>
#include <string>
#include <stdexcept>
#include <filesystem>
#include <vector>
class BaseFile : public IFile {
protected:
    std::fstream file;  // File stream
    static std::string fileName;  // File name

    void openFile(std::ios::openmode mode);  // Open file helper

public:
    explicit BaseFile(const std::string& name);
    virtual ~BaseFile();

    // IFile interface methods (these will be overridden in BaseFile)
    virtual void create(const std::string name) override;
    virtual void deleteItem() override;
    virtual void display() override;
    virtual void Write(const std::string line) override;
    virtual std::string GetName() override;

    // Static method to check if file exists
    static bool doesFileExist();
    virtual std::vector<std::string> read();
};

#endif // BASEFILE_H
