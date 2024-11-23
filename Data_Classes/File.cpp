#include <fstream>
#include <iostream>
using   namespace   std;
class File
{
private:
    ofstream* file;  //make the file
    string name;
public:
    File(string a = ".txt"); // Constructor
    ~File(); // Destructor
    Write();
};
void File::Write(const string& line){
    if (!file) {
        throw runtime_error("File object is not initialized.");
    }
    ofstream file(name,ios_base::app);
    file << Line <<endl;
    file.close();
}
File(string a) {
    
    name = a + ".txt";
    file = new ofstream(a); //allocate the ofstream
    if (!file->is_open()) {
        delete  file;
        throw runtime_error("file not opened" + name);
    }
}
~File() {
    if (file) {
        file->close(); // Close the file
        delete file;   // Free memory
    }
}
