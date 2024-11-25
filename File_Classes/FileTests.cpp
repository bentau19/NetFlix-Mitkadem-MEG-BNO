#include "IFile.h"  // Include the header where IFile is declared
#include "MovieFile.h"  // Include the file containing the MovieFile class
using namespace std;

int main() {
    try {
        // Create a MovieFile object
        MovieFile* f = new MovieFile("m1");
  
        // Display contents of the created file
        f->display();

        delete f;  // Free memory
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl; // Print error message
    }

    return 0;
}
