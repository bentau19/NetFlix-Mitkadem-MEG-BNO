#ifndef USERMOVIES_H
#define USERMOVIES_H
#include "BaseFile.h"
#include <vector>  // Include the vector header
#include <string>  // Include string header
using namespace std;
class FileIO {
private:    
    static vector<string> RemoveSimillar(const std::vector<string>& vec1, const vector<std::string>& vec2);
    static vector<string> PopId(unsigned long ToId,vector<string>* alldata,BaseFile* f);//find the id line return it and delete it from all data
    static vector<string> addUnique(const std::vector<string>& vec1, const vector<std::string>& vec2);
    static vector<unsigned long> StringTounsignedlongVector(const std::vector<std::string>& strVec);
public:
    static string IdLine(unsigned long id, int* loc,BaseFile* File);// return the line of the id in the file
    static bool RemoveIdList(vector<string> ListId, unsigned long ToId,BaseFile* File);// remove all
    static vector<unsigned long> IdList(unsigned long Id,BaseFile* File);  // Return a list of the Id in the specific file
    static bool AddIdsToId(vector<string> ListId, unsigned long ToId,BaseFile* File); // recieve a list of ids,
    // and add them to the given id list for a specidic file: ToId; id1 id2 id3 id4 .....
};

#endif
