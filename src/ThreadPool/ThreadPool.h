#ifndef THREADPOOL_H
#define THREADPOOL_H

#include <vector>
#include <queue>
#include <thread>
#include <mutex>
#include <atomic>
#include <condition_variable>

class ThreadPool {
private:
    std::vector<std::thread> workers; // Worker threads
    std::queue<int> tasks;           // Queue of tasks (client sockets)
    std::mutex queueMutex;           // Mutex for task queue
    std::condition_variable condition; // Condition variable for worker notification
    std::atomic<bool> stop;          // Stop flag

    void workerFunction();           // Function executed by workers
    void handleClient(int clientSocket); // Handle a client connection

public:
    ThreadPool(size_t numThreads);   // Constructor
    ~ThreadPool();                   // Destructor
    void addTask(int clientSocket);  // Add a task to the queue
};

#endif
