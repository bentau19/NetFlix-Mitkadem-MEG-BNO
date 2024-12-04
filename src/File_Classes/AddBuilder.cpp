#include "AddBuilder.h"

bool AddBuilder::BuildAdd(unsigned long userid, vector<string> movies)
{
    try{
        UserFile uf;
        MovieFile mf;
        //add to the userfile
        bool first = UserMovies::AddIdsToId(movies, userid,&uf);
        vector<string> user;
        user.push_back(to_string(userid));
        for (size_t i = 0; i < movies.size(); i++)
        {
            long id = stoul(movies[i]);
            first &= UserMovies::AddIdsToId(user,id ,&mf);
        }
        return first;
    }
    catch(exception){
        return false;
    }
}