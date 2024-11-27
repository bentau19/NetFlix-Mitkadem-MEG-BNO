#ifndef USER_FILE
#define USER_FILE
#include "BaseFile.h"
#include <iostream>
#include <filesystem>
#include <fstream>
#include <string>
#include <stdexcept>
class UserFile : public BaseFile {
public:
    UserFile();
    void display();
};






#endif