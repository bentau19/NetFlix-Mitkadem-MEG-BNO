#ifndef THREADPOOL_H
#define THREADPOOL_H

#include <thread>
#include <vector>
#include <queue>
#include <mutex>
#include <condition_variable>
#include <functional>  // For std::function

class ThreadPool {
public:
    ThreadPool(size_t numThreads);
    ~ThreadPool();

    void addTask(std::function<void()> task);  // Accept callable tasks

private:
    void workerFunction();

    std::vector<std::thread> workers;
    std::queue<std::function<void()>> tasks;  // Queue of tasks
    std::mutex queueMutex;
    std::condition_variable condition;
    bool stop;
};

#endif  // THREADPOOL_H
