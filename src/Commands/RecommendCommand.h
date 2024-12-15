#ifndef RecommendCommand_H
#define RecommendCommand_H
using namespace std;
#include <string>
#include <vector>
#include "ICommand.h"

class RecommendCommand : public ICommand { // Ensure public inheritance
public:
    RecommendCommand();
        ~RecommendCommand();

    string execute(std::string str) override; // Use "override" keyword
            std::vector<unsigned long> TestExFunc(std::string str);
    string execute();
};

#endif