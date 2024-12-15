#ifndef DATA_H
#define DATA_H

class Data {
public:
    // Members of the class
    char buffer[4096];   // To store received message from client
    int buffer_size;     // Size of the received message
    int client_sock;     // Client's socket descriptor

    // Constructor to initialize the class members
      Data(char* buffer, int buffer_size, int client_sock  ); // Constructor
      Data();
    ~Data(); // Destructor
};

#endif  // DATA_H
