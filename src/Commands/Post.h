//
// Created by User on 12/12/2024.
//

#ifndef NETFLIX_PROJECT_POST_H
#define NETFLIX_PROJECT_POST_H


#include <vector>
#include "ICommand.h"
#include "File_Classes/AddBuilder.h"

class Post : public ICommand {
public:
    Post();  // Constructor
    ~Post(); // Destructor

    void execute();  // Override execute method with no parameters
    void execute(std::string str) override;  // Override execute method with string parameter
    static void addAct(std::vector<std::string> userMovies, unsigned long usrId){
        if(userMovies.size()<2) throw std::invalid_argument("");
        AddBuilder::BuildAdd(usrId, userMovies);
    }
};

#endif //NETFLIX_PROJECT_POST_H
