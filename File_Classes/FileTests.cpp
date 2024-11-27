#include "UserMovies.h"  // Include UserMovies header
#include "UserFile.h"
#include "stringhandler.h"
#include <vector>  // You can also include this directly here if needed
#include <string>  // Same for string if required

using namespace std;

int main() {
    try {
        UserFile f;
        vector<int> m = UserMovies::UserList(1);
        for (int num : m) {
            std::cout << num << " ";  // Print each element followed by a space
        }
        cout << std::endl;  // End with a newline
        vector<string> movies;
        movies.push_back("65");
        movies.push_back("2");
        cout << UserMovies::AddUserMovies(movies,2);
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl; // Print error message
    }

    return 0;
}
