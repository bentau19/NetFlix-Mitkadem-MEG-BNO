#ifndef USERMOVIES
#define USERMOVIES
#include <string>
class UserMovies
{
private:
public:
    static int* UserList(int userid); //return a list of the user movies, first arg has the size of the array.
    static bool AddUserMovies( std::string movies[], int size,int Userid); // recieve array of movies, 
    //the size of the array and userid, return if managed to add the movies

};


#endif