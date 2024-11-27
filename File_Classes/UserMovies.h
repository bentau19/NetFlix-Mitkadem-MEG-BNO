#ifndef USERMOVIES_H
#define USERMOVIES_H

#include <vector>  // Include the vector header
#include <string>  // Include string header
using namespace std;
class UserMovies {
private:
    static vector<string> addUnique(const std::vector<string>& vec1, const vector<std::string>& vec2);
    static string UserLine(int id, int* loc);
    static vector<int> StringToIntVector(const std::vector<std::string>& strVec);
public:
    static vector<int> UserList(int userid);  // Return a list of the user's movie IDs
    static bool AddUserMovies(vector<string> movie, int Userid); // add the movies to the user ret flase if didnt manage
};

#endif // USERMOVIES_H
