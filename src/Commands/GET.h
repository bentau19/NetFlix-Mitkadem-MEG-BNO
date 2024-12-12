#ifndef RecommendCommand_H
#define RecommendCommand_H
using namespace std;
#include <string>
#include <vector>
#include "ICommand.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/BaseFile.h"
#include "../File_Classes/FileIO.h"
class GET : public ICommand { // Ensure public inheritance
public:
    GET();
        ~GET();

    void execute(std::string str) override; // Use "override" keyword
            std::vector<unsigned long> TestExFunc(std::string str);
    void execute();

    static bool isExist(unsigned long id, BaseFile *f){
        vector<unsigned long> res =  FileIO::IdList(id, f);
        return !res.empty();
    }
};

#endif