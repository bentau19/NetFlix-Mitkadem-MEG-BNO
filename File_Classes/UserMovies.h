#ifndef USERMOVIES_H
#define USERMOVIES_H
#include "BaseFile.h"
#include <vector>  // Include the vector header
#include <string>  // Include string header
using namespace std;
class UserMovies {
private:
    static vector<string> addUnique(const std::vector<string>& vec1, const vector<std::string>& vec2);
    static string IdLine(int id, int* loc,BaseFile* File);// return the line of the id in the file
    static vector<int> StringToIntVector(const std::vector<std::string>& strVec);
public:
    static vector<int> IdList(int Id,BaseFile* File);  // Return a list of the Id in the specific file
    static bool AddIdsToId(vector<string> ListId, int ToId,BaseFile* File); // recieve a list of ids,
    // and add them to the given id list for a specidic file: ToId; id1 id2 id3 id4 .....
};

#endif
