
#include <vector>
#include <stdexcept>
#include <unordered_map>
#include <algorithm>
#include "RecommendCommand.h"
#include "../File_Classes/StringHandler.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/MovieFile.h"
#include "../File_Classes/FileIO.h"


using namespace std;
RecommendCommand::RecommendCommand() {}

// Destructor definition
RecommendCommand::~RecommendCommand() {}

// Method to execute with no parameters
void RecommendCommand::execute() {
    throw std::invalid_argument("");
}


// Function to sort the unordered_map by values in descending order
// and by keys in ascending order for equal values
std::vector<std::pair<unsigned long, unsigned long>> sortByValueThenKey(unordered_map<unsigned long, unsigned long> dict) {
    std::vector<std::pair<unsigned long, unsigned long>> vec(dict.begin(), dict.end());

    // Sort the vector of pairs: first by value descending, then by key ascending
    std::sort(vec.begin(), vec.end(), [](const std::pair<unsigned long, unsigned long>& a, const std::pair<unsigned long, unsigned long>& b) {
        if (a.second == b.second) {
            return a.first < b.first;  // If values are equal, sort by key ascending
        }
        return a.second > b.second;  // Otherwise, sort by value descending
    });

    return vec;
}




// Method to execute with a string parameter
void RecommendCommand::execute(std::string str) {

    vector<unsigned long> res = TestExFunc(str);

    for(unsigned long a :res){
        std::cout  << a << " ";
    }
    std::cout << "\n";

}

std::vector<unsigned long> RecommendCommand::TestExFunc(std::string str) {
    //checks if the string input is valid
    vector<std::string> data = StringHandler::splitString(str);
    if(data.size()!=2)throw std::invalid_argument("");

    // init the user and the movie and checks if they exist
    unsigned long userId =stoul(data[0]);
    unsigned long movieId = stoul(data[1]);
    UserFile userFile;
    MovieFile movieFile;
    vector<unsigned long> watchedList;
    vector<unsigned long> movieWatchers;
    try {
        watchedList = FileIO::IdList(userId, &userFile);
        movieWatchers = FileIO::IdList(movieId, &movieFile);
    }catch (...){
        throw std::invalid_argument("");
    }
    if(watchedList.empty()||movieWatchers.empty())throw std::invalid_argument("");

    //similarity calc
    unordered_map<unsigned long, unsigned long> numOfCommon;//dict of user nums of common movies with our user (user->sum of common movies)
    // make the heavy calc about how much common movie with which user
    for (unsigned long tempMovie :watchedList){
        vector<unsigned long> TempWatchers = FileIO::IdList(tempMovie,&movieFile);
        for(unsigned long tempUser : TempWatchers){
            if(tempUser==userId){ continue;}
            numOfCommon[tempUser]++;
        }
    }

    unordered_map<unsigned long, unsigned long> recommendedMovies;//dict of rating of movie recommend

    for (unsigned long tempUser : movieWatchers){
        vector<unsigned long> tempMovies = FileIO::IdList(tempUser,&userFile);
        for(unsigned long tempMovie : tempMovies){
            bool flag= false;
            for (unsigned long temp : watchedList){
                if (temp == tempMovie){flag=true;
                    continue;}
            }

            if(flag||tempMovie==movieId) continue;
            recommendedMovies[tempMovie]+=numOfCommon[tempUser];
        }
    }

    std::vector<std::pair<unsigned long, unsigned long>> sortedDict = sortByValueThenKey(recommendedMovies);
    int count = 0;
    vector<unsigned long> res;
    for (const auto& pair : sortedDict) {
        res.push_back(pair.first);
        count++;
        if (count == 10) break;  // Stop after printing the first 10 elements
    }
    return res;
}
