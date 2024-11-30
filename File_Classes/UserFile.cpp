#include "UserFile.h"
#include "StringHandler.h"
UserFile::UserFile(): BaseFile("Users.txt")
{
    fileName = "Users.txt";
}
void UserFile::display()
{

    std::ifstream file(fileName);  // file for reading
    if (file.is_open()) {
        std::string line;
        std::cout << fileName << std::endl;
        while (std::getline(file, line)) {
            std::vector<std::string> splitResult = StringHandler::split(line, ';');
            std::string firstString = splitResult[0];
            std::cout << "UserId: " << firstString;
            std::cout << " Users Movies:";
            if (splitResult.size()>=1)
            {              
                for (size_t i = 1; i < splitResult.size(); ++i) {
                    std::cout << splitResult[i] << std::endl;
                }
            }
                
        }
        file.close();
    } else {
        std::cout << " user Failed to open file: " << fileName << std::endl;
    }
}
