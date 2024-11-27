#include "UserMovies.h"
#include "UserFile.h"
#include "stringhandler.h"
#include <vector>
#include <string>
#include <iostream>

std::vector<int> UserMovies::UserList(int userid) {
    UserFile f;
    std::vector<std::string> info = f.read();
    for (size_t i = 0; i < info.size(); i++) {
        std::string line = info[i];
        std::vector<std::string> user = StringHandler::split(line, ';');
        if (user[0] == std::to_string(userid)) {
            if (user.size() >= 1) {
                std::vector<std::string> movies = StringHandler::split(user[1], ' ');
                return transformStringToIntVector(movies);
            } else {
                return std::vector<int>();  // Return an empty vector if no movies found
            }
        }
    }
    return std::vector<int>();  // Return empty vector if the user was not found
}

bool UserMovies::AddUserMovies(vector<string> movie, int Userid)
{
    return false;
}

std::vector<int> UserMovies::transformStringToIntVector(const std::vector<std::string>& strVec) {
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
