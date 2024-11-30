#include "UserFile.h"
#include "stringhandler.h"
using namespace std;
UserFile::UserFile(): BaseFile("Users.txt")
{
    fileName = "Users.txt";
}
UserFile::~UserFile(){
}


std::string UserFile::GetName() const
{
    return "Users.txt";
}
string UserFile::display()
{
    if (fileName.empty()) {
        return "Users";
    }
    std::ifstream file(fileName);
    if (file.is_open()) {
        std::string line;
        string alline = "Users " + line;
        while (std::getline(file, line)) {
            alline+=line;
        }
        return alline;
        file.close();
    } else {
        throw std::ios_base::failure("failed");
    }
}

