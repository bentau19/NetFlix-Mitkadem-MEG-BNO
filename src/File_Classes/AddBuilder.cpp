#include "AddBuilder.h"


bool AddBuilder::CheckValid(unsigned long userid, std::vector<std::string> movies, BaseFile* b) {
    int i;
    std::string ListString = UserMovies::IdLine(userid, &i, b);
    
    // If the user doesn't exist, return false
    if (ListString.empty()) {
        return false;
    }

    // Split the user's list string into a vector
    std::vector<std::string> VectorList = StringHandler::split(ListString, ' ');

    // Check if all movies are in the user's list
    for (const auto& movie : movies) {
        if (find(VectorList.begin(), VectorList.end(), movie) == VectorList.end()) {
            // If a movie is not found in VectorList, return false
            return false;
        }
    }

    // All movies exist in the list
    return true;
}


bool AddBuilder::BuildAdd(unsigned long userid, vector<string> movies)
{
    try{
        if(movies.size()==0){
            return true;
        }
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

bool AddBuilder::BuildRemove(unsigned long userid, vector<string> movies)
{
    try{
        if(movies.size()==0){
            return true;
        }
        UserFile uf;
        MovieFile mf;
        if(!CheckValid(userid,movies,&uf)){
            return false;
        }
        bool first = UserMovies::RemoveIdList(movies, userid,&uf);
        vector<string> user;
        user.push_back(to_string(userid));
        for (size_t i = 0; i < movies.size(); i++)
        {
            long id = stoul(movies[i]);
            first &= UserMovies::RemoveIdList(user,id ,&mf);
        }
        return first;
    }
    catch(exception){
        return false;
    }
}
