#ifndef MOVIEFILE_H
#define MOVIEFILE_H


#include "BaseFile.h"
#include <iostream>
#include <fstream>
#include <string>
#include <stdexcept>
class MovieFile : public BaseFile {
public:
    MovieFile();
    void display() override;
};
#endif
