#ifndef MOVIEFILE_H
#define MOVIEFILE_H

#include "BaseFile.h"
#include <string>

class MovieFile : public BaseFile {
public:
    // Constructor: Initialize fileName
    MovieFile();
    ~MovieFile();
    std::string GetName() const override;
    // Display the content of the Movies.txt file
    std::string display() override;
};

#endif // MOVIEFILE_H
