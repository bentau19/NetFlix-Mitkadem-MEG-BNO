
#ifndef IFile_H
#define IFile_H

#include <string>

class IFile{
private:
    std::fstream file;      // File stream to work with files
    std::string fileName;   // Member variable to store the file name
    virtual void create(const std::string name) = 0;
    virtual void deleteItem(const std::string name) = 0;
public:
    virtual void display() = 0;
};

#endif
