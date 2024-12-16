//
// Created by User on 12/12/2024.
//

#ifndef NETFLIX_PROJECT_POST_H
#define NETFLIX_PROJECT_POST_H


#include <vector>
#include "Commands/General/ICommand.h"
#include "File_Classes/AddBuilder.h"

class Post : public ICommand {
public:
    Post();  // Constructor
    ~Post(); // Destructor

   std::string execute(std::string str) override;  // Override execute method with string parameter
};

#endif //NETFLIX_PROJECT_POST_H
