#ifndef MOVIEFILE_H
#define MOVIEFILE_H


#include "BaseFile.h"
#include <iostream>
#include <filesystem>
#include <fstream>
#include <string>
#include <stdexcept>
class MovieFile : public BaseFile {
public:
    MovieFile();
    void Write(std::string Line);
    void display() override;                     // Optional: Customize display
};
#endif
