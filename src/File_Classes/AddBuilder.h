#ifndef AddBUILDER_H
#define AddBUILDER_H
using namespace std;
#include <vector>
#include <string>
#include <algorithm>
#include "FileIO.h"
#include "BaseFile.h"
#include "UserFile.h"
#include "MovieFile.h"
#include "StringHandler.h"
#include <mutex>
class AddBuilder
{
private:
    static std::mutex fileMutex;
    static bool CheckValid(unsigned long userid,vector<string> movies,BaseFile* b);
public:
    static bool BuildAdd(unsigned long userid,vector<string> movies);
    static bool BuildRemove(unsigned long userid,vector<string> movies);
};

#endif
