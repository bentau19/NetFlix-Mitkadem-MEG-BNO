#include "UserFile.h"
#include "StringHandler.h"
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
    openFile(std::ios::in);
    if (file.is_open()) {
        std::string line;
        string alline = "Users " + line;
        while (std::getline(file, line)) {
            alline+=line;
        }
        file.close();
        return alline;
    } else {
        throw std::ios_base::failure("failed");
    }
}

