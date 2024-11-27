#include "MovieFile.h"
MovieFile::MovieFile() : BaseFile("Movies.txt") {
    if (!doesFileExist(fileName)) {
        create(fileName);
    }
}
void MovieFile::Write(std::string Line) {
    try {
        openFile(std::ios::out | std::ios::app);  //file in append
        file << Line << std::endl;               // Write the line
        file.close();
    } catch (const std::ios_base::failure& e) {  
        std::cerr << "Error: " << e.what() << std::endl;
    }
}

void MovieFile::display()
{
    if (fileName.empty()) {
        std::cout << "No file to display. Please create or set a file first." << std::endl;
        return;
    }
    std::ifstream file(fileName+".txt");  // file for reading
    if (file.is_open()) {
        std::string line;
        std::cout << fileName << std::endl;
        while (std::getline(file, line)) {
            std::cout << line << std::endl;
        }
        file.close();
    } else {
        std::cout << "Failed to open file: " << fileName << std::endl;
    }
}

std::string MovieFile::read()
{

    std::string line;
    std::cout << fileName << std::endl;
    try {
        openFile(std::ios::in);

        while (std::getline(file, line)) {
            std::cout << line << std::endl;
        }


        file.close();
    } catch (const std::ios_base::failure& e) {  
        std::cerr << "Error: " << e.what() << std::endl;
    }

    

    return std::string();
}
