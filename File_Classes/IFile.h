
#ifndef IFile_H
#define IFile_H

#include <string>

class IFile{
private:
    virtual void create(const std::string name) = 0;
    virtual void deleteItem(const std::string name) = 0;
public:
    virtual void display() = 0;
};

#endif
