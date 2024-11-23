#include "User.h"

User::User(int UserId)
{
}

User::~User()
{
}

void User::addMovie(const Movie& movie)
{
    movies.push_back(movie);  // Add the movie to the vector
}
// Get the list of movies (returns reference to the vector)
std::vector<Movie> User::getMovies()
{
    return movies;
}

// Remove a movie from the list by movie ID
bool User::removeMovie(int movieId)
{
    for (auto it = movies.begin(); it != movies.end(); ++it) {
        if (it->getmovieid() == movieId) { 
            movies.erase(it); 
            return true; //true if found
        }
    }
    return false;  // false - didnt find
}