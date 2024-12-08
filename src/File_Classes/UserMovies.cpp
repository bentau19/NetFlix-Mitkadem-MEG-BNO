#include "UserMovies.h"
#include "UserFile.h"
#include "StringHandler.h"
#include <vector>
#include <string>
#include <iostream>
#include <algorithm>


vector<unsigned long> UserMovies::IdList(unsigned long Id, BaseFile* f){
    int j;

    string line = IdLine(Id,&j ,f); // find me the line of the id
    vector<string> IdAndList = StringHandler::split(line,';');
    if (IdAndList.size()<2)
    {
        vector<unsigned long> a;
        return  a;
    }
    vector<string> ListIds = StringHandler::split(IdAndList[1], ' '); //gice me the list of the ids
    vector<unsigned long>  a = StringTounsignedlongVector(ListIds);
    return a;
}

bool UserMovies::AddIdsToId(vector<string> ListId, unsigned long ToId,BaseFile* f)
{
    try
    {
        vector<string> AllIdData; // a vector of all the Ids and their lists
        vector<string> IdData; // a vector of the specific Id,
        // 1st arg is the Id 2nd arg will be all its list
        vector<string> IdList;//all Ids in the list of the Toid
        AllIdData = f->read(); // read from the file

        int i = 0; // remember index in the all list to delete

        IdData = StringHandler::split(IdLine(ToId,&i,f), ';');
        if(IdData.size()>=2){
            IdList = StringHandler::split(IdData[1], ' ');
        }
        if (i!= -1)//delete i if it exists
        {
            AllIdData.erase(AllIdData.begin() + i); // Erase the line at i
        }
        else{//if the id didnt exist make it
            IdData[0]=to_string(ToId);
            IdData.push_back("");
        }
        //now we change the users movies
        IdList = addUnique(IdList,ListId);
        //now we add the new user to the vector
        IdData[1] = StringHandler::join(IdList , ' ');
        AllIdData.push_back(StringHandler::join(IdData,';'));
        //delete the file to create a new one
        f->deleteItem();
        f->create(f->GetName());
        for (size_t i = 0; i < AllIdData.size(); i++)
        {
            f->Write(AllIdData[i]);
        }
        return true;
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
        return false;
    }
}
vector<string> UserMovies::addUnique(const std::vector<string>& vec1, const vector<std::string>& vec2) {
    vector<std::string> result = vec1; // Start with all elements from vec1
    for (const auto& item : vec2) {
        // Add the item only if it's not already present in the result
        if (find(result.begin(), result.end(), item) == result.end()) {
            result.push_back(item);
        }
    }

    return result;
}

string UserMovies::IdLine(unsigned long id, int* loc,BaseFile* File) {
    std::vector<std::string> info = File->read();;
    for (size_t i = 0; i < info.size(); i++) {
        std::string line = info[i];
        std::vector<std::string> user = StringHandler::split(line, ';');

        if (user[0] == std::to_string(id)) {
            if (loc) { // Only set loc if loc is not nullptr
                *loc = static_cast<unsigned long>(i);
            }
            return line;
        }
    }

    if (loc) { // Only set loc if loc is not nullptr
        *loc = -1;
    }
    return "";
}


vector<unsigned long> UserMovies::StringTounsignedlongVector(const std::vector<std::string>& strVec) {
    std::vector<unsigned long> unsignedlongVec;
    for (const auto& str : strVec) {
        try {
            unsignedlongVec.push_back(std::stoul(str));
        } catch (const std::invalid_argument& e) {
            std::cerr << "Invalid number: " << str << std::endl;
        } catch (const std::out_of_range& e) {
            std::cerr << "Out of range: " << str << std::endl;
        }
    }
    return unsignedlongVec;
}

