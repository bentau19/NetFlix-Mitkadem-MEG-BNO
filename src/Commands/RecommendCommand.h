#ifndef RecommendCommand_H
#define RecommendCommand_H
using namespace std;
#include <string>
#include "ICommand.h"

class RecommendCommand : public ICommand { // Ensure public inheritance
public:
    RecommendCommand();
        ~RecommendCommand();

    void execute(std::string str) override; // Use "override" keyword
    void execute();
};

#endif