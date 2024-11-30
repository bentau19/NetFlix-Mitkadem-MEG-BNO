#include "MovieFile.h"
#include <fstream>
#include <string>
#include <stdexcept>

using namespace std;

MovieFile::MovieFile() : BaseFile("Movies.txt") {
    fileName = "Movies.txt";
}
MovieFile::~MovieFile(){
    
}

string MovieFile::display() {
    if (fileName.empty()) {
        return "Movies file is empty";
    }

    std::ifstream file(fileName);
    if (file.is_open()) {
        std::string line;
        std::string alline = "Movies: ";  // Initialize with a title
        while (std::getline(file, line)) {
            alline += "\n" + line;  // Append each line with newline
        }
        file.close();
        return alline;
    } else {
        throw std::ios_base::failure("Failed to open file: " + fileName);
    }
}
std::string MovieFile::GetName() const{
    return "Movies.txt";
}