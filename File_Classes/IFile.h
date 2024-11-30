#ifndef IFILE_H
#define IFILE_H

#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <string>
#include <stdexcept>
class IFile {
public:
    virtual ~IFile() = default;
    // Pure virtual functions that must be implemented by any class inheriting IFile
    virtual void create(const std::string fileName) = 0;
    virtual void openFile(std::ios::openmode mode) = 0;
    virtual void deleteItem() = 0;
    virtual std::string display() = 0;
    virtual bool doesFileExist(const std::string fileName) = 0;
    virtual std::vector<std::string> read() = 0;
    virtual void Write(const std::string line) = 0;
    virtual std::string GetName() const = 0;
};

#endif // IFILE_H
