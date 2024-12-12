//
// Created by User on 12/12/2024.
//

#ifndef NETFLIX_PROJECT_PATCH_H
#define NETFLIX_PROJECT_PATCH_H


#include <string>
#include "ICommand.h"

class Patch : public ICommand {
    public:
        Patch();  // Constructor
        ~Patch(); // Destructor

        void execute();  // Override execute method with no parameters
        void execute(std::string str) override;  // Override execute method with string parameter

};



#endif //NETFLIX_PROJECT_PATCH_H
