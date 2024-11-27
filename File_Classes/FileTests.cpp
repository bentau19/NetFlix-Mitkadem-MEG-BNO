#include "UserMovies.h"  // Include UserMovies header
#include "UserFile.h"
#include "stringhandler.h"
#include <vector>  // You can also include this directly here if needed
#include <string>  // Same for string if required

using namespace std;

int main() {
    try {
        vector<int> m = UserMovies::UserList(1);
        for (int num : m) {
        std::cout << num << " ";  // Print each element followed by a space
        }
        std::cout << std::endl;  // End with a newline
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl; // Print error message
    }

    return 0;
}
