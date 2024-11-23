#include "Movie.h"

Movie::Movie(int movieid)
{
    this->movieid = movieid;
}

Movie::~Movie()
{
}

int Movie::getmovieid()
{
    return movieid;
}
