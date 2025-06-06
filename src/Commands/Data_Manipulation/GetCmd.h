#ifndef GetCmd_H
#define GetCmd_H
using namespace std;
#include <string>
#include <vector>
#include "../General/ICommand.h"
#include "File_Classes/UserFile.h"
#include "File_Classes/BaseFile.h"
#include "File_Classes/FileIO.h"
class GetCmd : public ICommand { // Ensure public inheritance
public:
    GetCmd();
        ~GetCmd();

    std::string execute(std::string str) override; // Use "override" keyword
    std::vector<unsigned long> TestExFunc(std::string str);

};

#endif