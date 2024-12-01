#ifndef USERFILE_H
#define USERFILE_H

#include "BaseFile.h"
#include <string>

class UserFile : public BaseFile {
public:
    // Constructor: Initialize fileName
    UserFile();
    ~UserFile();
    std::string GetName() const override;
    // Display the content of the Users.txt file
    std::string display() override;
};

#endif // USERFILE_H
