#ifndef USERMOVIES
#define USERMOVIES
#include <string>
class UserMovies
{
private:
    /* data */
public:
    static int* UserList(int userid);
    static bool AddUserMovies( std::string movies[], int array size,int Userid);

};


#endif