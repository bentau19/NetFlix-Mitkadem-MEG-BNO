#include "UserMovies.h"  // Include UserMovies header
#include "UserFile.h"
#include "stringhandler.h"
#include <vector>  // You can also include this directly here if needed
#include <string>  // Same for string if required
#include "MovieFile.h"
using namespace std;

int main() {
    try {
        MovieFile f;
        cout << std::endl;  // End with a newline
        vector<string> movies;
        movies.push_back("65");
        movies.push_back("2");
        cout << UserMovies::AddIdsToId(movies,2,&f);
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl; // Print error message
    }

    return 0;
}
