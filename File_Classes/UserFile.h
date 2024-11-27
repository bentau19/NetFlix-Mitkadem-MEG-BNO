#ifndef USER_FILE
#define USER_FILE
#include "IFile.h"
#include <iostream>
#include <filesystem>
#include <fstream>
#include <string>
#include <stdexcept>
class UserFile : public IFile
{
private:
    std::fstream file;      // File stream to work with files
    std::string fileName;   // Member variable to store the file name
    void create(const std::string name);
    void deleteItem(const std::string name);
public:
    void display();
    UserFile(/* args */);
    ~UserFile();
};






#endif