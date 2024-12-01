//
// Created by Ben on 30/11/2024.
//

#include <stdexcept>
#include <unordered_map>
#include <algorithm>
#include "RecomedionCommand.h"
#include "../../src/StringTools/StringTools.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/MovieFile.h"
#include "../File_Classes/UserMovies.h"

using namespace std;
RecomedionCommand::RecomedionCommand() {}

// Destructor definition
RecomedionCommand::~RecomedionCommand() {}

// Method to execute with no parameters
void RecomedionCommand::execute() {
    throw std::invalid_argument("");
}


// Function to sort the unordered_map by values in descending order
// and by keys in ascending order for equal values
std::vector<std::pair<int, int>> sortByValueThenKey(const std::unordered_map<int, int>& dict) {
    std::vector<std::pair<int, int>> vec(dict.begin(), dict.end());

    // Sort the vector of pairs: first by value descending, then by key ascending
    std::sort(vec.begin(), vec.end(), [](const std::pair<int, int>& a, const std::pair<int, int>& b) {
        if (a.second == b.second) {
            return a.first < b.first;  // If values are equal, sort by key ascending
        }
        return a.second > b.second;  // Otherwise, sort by value descending
    });

    return vec;
}

// Method to execute with a string parameter
void RecomedionCommand::execute(std::string str) {
    //checks if the string input is valid
    vector<std::string> data = StringTools::splitString(str);
    if(data.size()!=2)throw std::invalid_argument("");

    // init the user and the movie and checks if they exist
    int userId = atoi(data[0].c_str());
    int movieId = atoi(data[1].c_str());
    UserFile userFile;
    MovieFile movieFile;
    vector<int> watchedList = UserMovies::IdList(userId,&userFile);
    vector<int> movieWatchers = UserMovies::IdList(movieId,&movieFile);
    if(watchedList.empty()||movieWatchers.empty())throw std::invalid_argument("");

    //similarity calc
    unordered_map<int, int> numOfCommon;//dict of user nums of common movies with our user (user->sum of common movies)
    // make the heavy calc about how much common movie with which user
    for (int tempMovie :watchedList){
        vector<int> TempWatchers = UserMovies::IdList(tempMovie,&movieFile);
        for(int tempUser : TempWatchers){
            if(tempUser==userId){ continue;}
            numOfCommon[tempUser]++;
        }
    }

    unordered_map<int, int> recommendedMovies;//dict of rating of movie recommend

    for (int tempUser : movieWatchers){
        vector<int> tempMovies = UserMovies::IdList(tempUser,&userFile);
        for(int tempMovie : tempMovies){
            bool flag= false;
            for (int temp : watchedList){
                if (temp == tempMovie){flag=true;
                continue;}
            }

            if(flag||tempMovie==movieId) continue;
            recommendedMovies[tempMovie]+=numOfCommon[tempUser];
        }
    }

    std::vector<std::pair<int, int>> sortedDict = sortByValueThenKey(recommendedMovies);
    int count = 0;
    for (const auto& pair : sortedDict) {
        std::cout  << pair.first << " ";
        count++;
        if (count == 10) break;  // Stop after printing the first 10 elements
    }
}
