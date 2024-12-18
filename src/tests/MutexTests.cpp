#include <gtest/gtest.h>
#include <thread>
#include <mutex>
#include "../File_Classes/FileIO.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/StringHandler.h"
#include <vector>
#include <string>
#include "../File_Classes/MovieFile.h"
#include "../File_Classes/AddBuilder.h"

std::mutex testMutex;  // Mutex for testing

// Test for thread safety in file write operation
TEST(MutexTest, WriteConcurrency) {
    UserFile u;
    MovieFile m;

    auto writeOperation = [](BaseFile* file, const std::string& content) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        file->Write(content);
    };

    // Launch multiple threads to write to the file concurrently
    std::thread t1(writeOperation, &u, "user_content1");
    std::thread t2(writeOperation, &m, "movie_content1");
    std::thread t3(writeOperation, &u, "user_content2");
    std::thread t4(writeOperation, &m, "movie_content2");

    t1.join();
    t2.join();
    t3.join();
    t4.join();
    std::string u_display = u.display();
    std::string m_display = m.display();
    // Verify that the file content is consistent and not corrupted
    ASSERT_TRUE(u_display == "Users user_content1user_content2" || u_display == "Users user_content2user_content1");

    ASSERT_TRUE(m_display == "Movies: \nmovie_content1\nmovie_content2" || m_display == "Movies: \nmovie_content2\nmovie_content1");

    // Clean up after test
    ASSERT_NO_THROW(u.deleteItem());
    ASSERT_NO_THROW(m.deleteItem());
}

// Test for thread safety in file read operation
TEST(MutexTest, ReadConcurrency) {
    MovieFile m;
    UserFile u;

    auto readOperation = [](BaseFile* file) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        std::string content = file->display();
    };

    // Launch multiple threads to read from the file concurrently
    std::thread t1(readOperation, &u);
    std::thread t2(readOperation, &m);
    std::thread t3(readOperation, &u);
    std::thread t4(readOperation, &m);

    t1.join();
    t2.join();
    t3.join();
    t4.join();

    // The test case is not verifying the content as we are only checking thread safety
    // Clean up after test
    ASSERT_NO_THROW(u.deleteItem());
    ASSERT_NO_THROW(m.deleteItem());
}

// Test for thread safety in file deletion operation
TEST(MutexTest, DeleteConcurrency) {
    UserFile u;
    MovieFile m;

    auto deleteOperation = [](BaseFile* file) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        file->deleteItem();
    };

    // Launch multiple threads to delete the file concurrently
    std::thread t1(deleteOperation, &u);
    std::thread t2(deleteOperation, &m);

    t1.join();
    t2.join();

    // Verify that the files have been deleted
    ASSERT_FALSE(u.doesFileExist());
    ASSERT_FALSE(m.doesFileExist());
}

// Test for race condition handling: multiple threads reading and writing at the same time
TEST(MutexTest, RaceConditionHandling) {
    UserFile u;
    MovieFile m;

    auto writeOperation = [](BaseFile* file, const std::string& content) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        file->Write(content);
    };

    auto readOperation = [](BaseFile* file) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        std::string content = file->display();
    };

    // Launch multiple threads: some for reading and others for writing
    std::thread t1(writeOperation, &u, "user_content1");
    std::thread t2(readOperation, &m);
    std::thread t3(writeOperation, &m, "movie_content1");
    std::thread t4(readOperation, &u);

    t1.join();
    t2.join();
    t3.join();
    t4.join();

    // Verify that all operations completed without error and with consistent results
    ASSERT_EQ(u.display(), "Users user_content1");
    ASSERT_EQ(m.display(), "Movies: \nmovie_content1");

    // Clean up after test
    ASSERT_NO_THROW(u.deleteItem());
    ASSERT_NO_THROW(m.deleteItem());
}
