#include "MovieFile.h"
MovieFile::MovieFile() : BaseFile("Movies.txt") {
    fileName = "Movies.txt";
}
void MovieFile::display()
{
    if (fileName.empty()) {
        std::cout << "No file to display. Please create or set a file first." << std::endl;
        return;
    }
    std::ifstream file(fileName);  // file for reading
    if (file.is_open()) {
        std::string line;
        std::cout << fileName << std::endl;
        while (std::getline(file, line)) {
            std::cout << line << std::endl;
        }
        file.close();
    } else {
        std::cout << "movie Failed to open file: " << fileName << std::endl;
    }
}


