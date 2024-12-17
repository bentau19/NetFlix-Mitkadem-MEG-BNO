
#include <vector>
#include <stdexcept>
#include <unordered_map>
#include <algorithm>
#include "GetCmd.h"
#include "File_Classes/StringHandler.h"
#include "File_Classes/UserFile.h"
#include "File_Classes/MovieFile.h"
#include "File_Classes/FileIO.h"
//#include "Commands/General/Validity.h"


using namespace std;
GetCmd::GetCmd() {}

// Destructor definition
GetCmd::~GetCmd() {}



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
std::string GetCmd::execute(std::string str) {
    try {
        vector<unsigned long> recommend = TestExFunc(str);
      //  string res = Validity::ValidityAlert(GetSuc);
      string  res = " \n";

        for (unsigned long a : recommend) {
            res += std::to_string(a) + " "; // Convert number to string and append
        }
        res +="\n";
        return res;

    }catch(...){
       // return Validity::ValidityAlert(GenFail);
    }
}



std::vector<unsigned long> GetCmd::TestExFunc(std::string str) {
    // init the user and the movie and checks if they exist
    unsigned long userId;
    unsigned long movieId;
    UserFile userFile;
    MovieFile movieFile;
    vector<unsigned long> watchedList;
    vector<unsigned long> movieWatchers;

    try {
        //checks if the string input is valid
        vector<std::string> data;
        // = Validity::twoNumsVec(str);
        userId = stoul(data[0]);
        movieId = stoul(data[1]);
        if(!isExist(userId,&userFile)||
           !isExist(movieId,&movieFile))throw std::invalid_argument("");

        watchedList = FileIO::IdList(userId, &userFile);
        movieWatchers = FileIO::IdList(movieId, &movieFile);
    }catch (...){
        throw std::invalid_argument("");
    }
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
