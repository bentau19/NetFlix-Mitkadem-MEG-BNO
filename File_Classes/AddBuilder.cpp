#include "AddBuilder.h"

void AddBuilder::BuildAdd(int userid, vector<string> movies)
{
    UserFile uf;
    MovieFile mf;
    //add to the userfile
    UserMovies::AddIdsToId(movies, userid,&uf);
    vector<string> user;
    user.push_back(to_string(userid));
    for (size_t i = 0; i < movies.size(); i++)
    {
        int id = stoi(movies[i]);
        UserMovies::AddIdsToId(user,id ,&mf);
    }
    
}