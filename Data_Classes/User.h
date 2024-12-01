#ifndef User_H
#define User_H
#include "Movie.h"
#include <iostream>
#include <vector>
class User
{
private:
    int UserId;
    std::vector<Movie> movies;  // List of movies

public:
    User(int UserId = 0);
    ~User();
    void addMovie(const Movie& movie);  // AddCommand a movie
    std::vector<Movie> getMovies();  // Return the movies
    bool removeMovie(int movieId);  // Remove a movie
    int getuserid(); // Return the user id
};

#endif
