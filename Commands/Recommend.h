//
// Created by User on 30/11/2024.
//

#ifndef NETFLIX_RECOMMEND_H
#define NETFLIX_RECOMMEND_H


#include "ICommand.h"

class Recommend: public ICommand {
    public:
        Recommend();  // Constructor
        ~Recommend(); // Destructor
        void execute() override;  // Override execute method with no parameters
        void execute(std::string str) override;  // Override execute method with string parameter

    };


#endif //NETFLIX_RECOMMEND_H
