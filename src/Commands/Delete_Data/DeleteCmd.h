//
// Created by User on 15/12/2024.
//

#ifndef NETFLIX_PROJECT_DELETE_H
#define NETFLIX_PROJECT_DELETE_H


using namespace std;
#include <string>
#include <vector>
#include "Commands/General/ICommand.h"
#include "File_Classes/UserFile.h"
#include "File_Classes/BaseFile.h"
#include "File_Classes/FileIO.h"


class DeleteCmd : public ICommand { // Ensure public inheritance
public:
    DeleteCmd();
    ~DeleteCmd();

    std::string execute(std::string str) override; // Use "override" keyword
};
#endif //NETFLIX_PROJECT_DELETE_H
