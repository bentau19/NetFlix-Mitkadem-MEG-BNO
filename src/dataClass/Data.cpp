#include "Data.h"

// Constructor to initialize the class members (if needed)
Data::Data() {
    // Initialize buffer_size and client_sock with defaults (optional)
    buffer_size = 0;
    client_sock = -1;
}
Data::  Data(char* input_buffer, int size, int sock) : buffer_size(size), client_sock(sock) {
        // Ensure the buffer doesn't overflow
        if (size > sizeof(buffer)) {
            size = sizeof(buffer);
        }
        
        // Manually copy the bytes from input_buffer to the buffer array
        for (int i = 0; i < size; ++i) {
            buffer[i] = input_buffer[i];
        }
    }
// Destructor (if needed)
Data::~Data() {
    // Cleanup if needed (e.g., releasing dynamic memory, etc.)
}
