#include "ThreadPool.h"
#include <iostream>

ThreadPool::ThreadPool(size_t numThreads) : stop(false) {
    for (size_t i = 0; i < numThreads; ++i) {
        workers.emplace_back([this]() { workerFunction(); });//create threads pool for num of threads
    }
}

ThreadPool::~ThreadPool() {
    {
        std::unique_lock<std::mutex> lock(queueMutex);
        stop = true;
    }
    condition.notify_all();
    for (std::thread &worker : workers) {
        if (worker.joinable()) { //if there is place add the wirker
            worker.join();
        }
    }
}

void ThreadPool::addTask(std::function<void()> task) {
    {
        std::unique_lock<std::mutex> lock(queueMutex);
        tasks.push(task); //add the task
    }
    condition.notify_one();
}

void ThreadPool::workerFunction() {
    while (true) {
        std::function<void()> task;
        {
            std::unique_lock<std::mutex> lock(queueMutex);
            condition.wait(lock, [this]() { return stop || !tasks.empty(); });

            if (stop && tasks.empty()) { //if there is no more task/need to stop end
                return;
            }

            task = tasks.front();
            tasks.pop(); //remove task for it done
        }
        task();  // Execute the task
    }
}
