#include "File.h"  // Include header, not .cpp

int main() {
    try {
        // Create a File object (with default name "file.txt")
        File* f = new File("example");  // Dynamically allocate File object

        // Write something to the file
        f->Write("Hello, world!");

        // Clean up the dynamically allocated memory (Destructor will be called automatically when `f` goes out of scope)
        delete f;
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
    }

    return 0;
}
