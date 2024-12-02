//
// Created by User on 30/11/2024.
//

#ifndef NETFLIX_RECOMEDIONCOMMAND_H
#define NETFLIX_RECOMEDIONCOMMAND_H


#include <vector>
#include "ICommand.h"

class RecomedionCommand: public ICommand {
    public:
        RecomedionCommand();  // Constructor
        ~RecomedionCommand(); // Destructor
        void execute() override;  // Override execute method with no parameters
        void execute(std::string str) override;  // Override execute method with string parameter
        std::vector<int> TestExFunc(std::string str);
    };


#endif //NETFLIX_RECOMEDIONCOMMAND_H
