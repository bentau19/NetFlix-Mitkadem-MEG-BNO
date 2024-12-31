#include "ThreadPool.h"
#include <unistd.h> // For close()
#include <iostream>
#include <sys/socket.h>
#include <mutex>
#include <map>
#include "../App/App.h" // Include the App header for your structure
#include "../Menu/ServerMenu.h"
#include "../Commands/General/ICommand.h"
#include "../Commands/General/HelpCmd.h"
#include "../Commands/Add_Data/PatchCmd.h"
#include "../Commands/Add_Data/PostCmd.h"
#include "../Commands/Delete_Data/DeleteCmd.h"
#include "../Commands/Data_Manipulation/GetCmd.h"

std::mutex command_mutex;

map<string, ICommand*> commands;

ThreadPool::ThreadPool(size_t numThreads) : stop(false) {
    for (size_t i = 0; i < numThreads; ++i) {
        workers.emplace_back([this]() { workerFunction(); });
    }
}

ThreadPool::~ThreadPool() {
    {
        std::unique_lock<std::mutex> lock(queueMutex);
        stop = true;
    }
    condition.notify_all();
    for (std::thread &worker : workers) {
        if (worker.joinable()) {
            worker.join();
        }
    }
}

void ThreadPool::addTask(int clientSocket) {
    {
        std::unique_lock<std::mutex> lock(queueMutex);
        tasks.push(clientSocket);
    }
    condition.notify_one();
}

void ThreadPool::workerFunction() {
    while (true) {
        int clientSocket;
        {
            std::unique_lock<std::mutex> lock(queueMutex);
            condition.wait(lock, [this]() { return stop || !tasks.empty(); });

            if (stop && tasks.empty()) {
                return;
            }

            clientSocket = tasks.front();
            tasks.pop();
        }
        handleClient(clientSocket);
    }
}

void ThreadPool::handleClient(int clientSocket) {
    // Initialize necessary data for creating App object
    Data* data = new Data();
    data->client_sock = clientSocket;
    commands["help"] = new HelpCmd();
    commands["GET"] = new GetCmd();
    commands["POST"] = new PostCmd();
    commands["PATCH"] = new PatchCmd();
    commands["DELETE"] = new DeleteCmd();
    // Lock before creating App instance
    command_mutex.lock();
    App* app = new App(new ServerMenu(), commands, data); // Create App instance
    command_mutex.unlock();

    // Run the App to handle the client
    app->run();

    // Clean up the App instance after the communication ends
    delete app;
    delete data;

    // Close the client socket after handling
    close(clientSocket);
}