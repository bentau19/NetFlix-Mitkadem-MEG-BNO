#ifndef Movie_H
#define Movie_H
#include <iostream>

class Movie
{
private:
    int movieid;
public:
    Movie(int movieid = 0);
    ~Movie();
    int getmovieid();
};

#endif
