#include "MovieFile.h"
MovieFile::MovieFile(const std::string name){
    fileName = name;
    if (!doesFileExist())
    {
        create(name);
    }
    
}
bool MovieFile::doesFileExist() {
    std::ifstream file(fileName + ".txt"); 
    bool t = file.good();            // true if managed to open file
    if (t){
        file.close();
    }
    return t;
}
void MovieFile::create(const std::string name) {
    std::ofstream file(name+".txt");  // Create and open the file
    if (file.is_open()) {
        file.close(); 
    } else {
        std::cout << "Failed to create file: " << name << std::endl;
    }
}

void MovieFile::deleteItem(const std::string name) {
    if (std::filesystem::remove(name+".txt")) {  // Remove the file using filesystem
        std::cout << "Deleted movie file: " << name << std::endl;
    } else {
        std::cout << "Failed to delete file: " << name << std::endl;
    }
}

void MovieFile::Write(std::string Line)
{
    std::ofstream outFile(fileName + ".txt", std::ios::app);  // Open the file
    if (outFile.is_open()) {
        outFile << Line << std::endl;   // Write to the file
        outFile.close();  // Dont forget to close the file
    } else {
        std::cout << "Error opening file for writing.\n";
    }
}

void MovieFile::display()
{
    if (fileName.empty()) {
        std::cout << "No file to display. Please create or set a file first." << std::endl;
        return;
    }
    std::ifstream file(fileName+".txt");  // Open the file for reading
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

std::string MovieFile::GetName()
{
    return fileName;
}
