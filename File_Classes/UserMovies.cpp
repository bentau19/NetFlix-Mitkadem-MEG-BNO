#include "UserMovies.h"
#include "UserFile.h"
#include "stringhandler.h"
#include <vector>
#include <string>
#include <iostream>
#include <algorithm>


std::vector<int> UserMovies::UserList(int userid) {
    int j;
    string line = UserLine(userid,&j);
    std::vector<std::string> movies = StringHandler::split(line, ' ');
    return StringToIntVector(movies);
}

bool UserMovies::AddUserMovies(vector<string> movie, int Userid)
{
    try
    {
        vector<string> AllUsersData; // a vector of all the users
        vector<string> UserData; // a vector of the specific user,
        // 1st arg is the userid 2nd arg will be all its movies
        vector<string> UserMovies;//all movies of the user
        UserFile f;
        AllUsersData = f.read();
        int i = 0;
        UserData = StringHandler::split(UserLine(Userid,&i), ';');
        if(UserData.size()>=2){
            UserMovies = StringHandler::split(UserData[1], ' ');
        }
        if (i!= -1)//delete i if it exists
        {
            AllUsersData.erase(AllUsersData.begin() + i); // Erase the line at i
        }
        else{
            UserData[0]=to_string(Userid);
            UserData.push_back("");
        }
        //now we change the users movies
        UserMovies = addUnique(UserMovies,movie);
        //now we add the new user to the vector
        UserData[1] = StringHandler::join(UserMovies , ' ');
        AllUsersData.push_back(StringHandler::join(UserData,';'));
        //delete the file to create a new one
        f.deleteItem();
        UserFile fn;
        for (size_t i = 0; i < AllUsersData.size(); i++)
        {
            fn.Write(AllUsersData[i]);
        }
        return true;
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
        return false;
    }
}
vector<string> UserMovies::addUnique(const std::vector<string>& vec1, const vector<std::string>& vec2) {
    vector<std::string> result = vec1; // Start with all elements from vec1

    for (const auto& item : vec2) {
        // Add the item only if it's not already present in the result
        if (find(result.begin(), result.end(), item) == result.end()) {
            result.push_back(item);
        }
    }

    return result;
}

std::string UserMovies::UserLine(int id, int* loc = nullptr) {
    UserFile f;
    std::vector<std::string> info = f.read();

    for (size_t i = 0; i < info.size(); i++) {
        std::string line = info[i];
        std::vector<std::string> user = StringHandler::split(line, ';');

        if (user[0] == std::to_string(id)) {
            if (loc) { // Only set loc if loc is not nullptr
                *loc = static_cast<int>(i);
            }
            return line;
        }
    }

    if (loc) { // Only set loc if loc is not nullptr
        *loc = -1;
    }
    return "";
}


std::vector<int> UserMovies::StringToIntVector(const std::vector<std::string>& strVec) {
    std::vector<int> intVec;
    for (const auto& str : strVec) {
        try {
            intVec.push_back(std::stoi(str));
        } catch (const std::invalid_argument& e) {
            std::cerr << "Invalid number: " << str << std::endl;
        } catch (const std::out_of_range& e) {
            std::cerr << "Out of range: " << str << std::endl;
        }
    }
    return intVec;
}
