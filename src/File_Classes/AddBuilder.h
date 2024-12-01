#ifndef AddBUILDER_H
#define AddBUILDER_H
using namespace std;
#include <vector>
#include <string>
#include "UserMovies.h"
#include "BaseFile.h"
#include "UserFile.h"
#include "MovieFile.h"
class AddBuilder
{
private:

public:
    static bool BuildAdd(int userid,vector<string> movies);
};

#endif
