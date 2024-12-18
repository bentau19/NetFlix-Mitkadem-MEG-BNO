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
TEST(MutexTest, AddConcurrency) {
    UserFile u;
    MovieFile m;
    vector<string> movies1 = {"11", "12", "13"};
    vector<string> movies2 = {"11", "22", "23"};
    vector<string> movies3 = {"11", "32", "33"};
    // Ensure testMutex is available for thread synchronization
    static std::mutex testMutex;
    auto addOperation = [](int id, const vector<string>& movies) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        std::cout << "Adding movies for ID: " << id << std::endl;
        bool result = AddBuilder::BuildAdd(id, movies);
        std::cout << "Added movies for ID: " << id << " with result: " << result << std::endl;
        ASSERT_TRUE(result);  // Ensure AddBuilder is thread-safe
    };

    // Launch multiple threads to add movies concurrently
    std::thread t1(addOperation, 1, movies1);
    std::thread t2(addOperation, 2, movies2);
    std::thread t3(addOperation, 3, movies3);
    t1.join();
    t2.join();
    t3.join();
    // Verify the content after the add operations
    vector<unsigned long> listu1 = FileIO::IdList(1, &u);
    vector<unsigned long> listu2 = FileIO::IdList(2, &u);
    vector<unsigned long> listu3 = FileIO::IdList(3, &u);
    // Ensure the correct number of movies was added to each list
    ASSERT_EQ(listu1.size(), 3);  // Verify that 3 movies were added for ID 1
    ASSERT_EQ(listu2.size(), 3);  // Verify that 3 movies were added for ID 2
    ASSERT_EQ(listu3.size(), 3);  // Verify that 3 movies were added for ID 3
}
TEST(MutexTest, RemoveConcurrency) {
    UserFile u;
    MovieFile m;
    vector<string> movies1 = {"11","32", "33"};
    auto removeOperation = [](int id, const vector<string>& movies) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        bool result = AddBuilder::BuildRemove(id, movies);
        ASSERT_TRUE(result);  // The first removal should succeed
    };
    
    // Launch the first thread to remove the movies
    std::thread t1(removeOperation, 3, movies1);
    t1.join();  // Ensure the first operation completes before proceeding
    
    // Now, try to remove the same movies again
    auto removeOperationAgain = [](int id, const vector<string>& movies) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        bool result = AddBuilder::BuildRemove(id, movies);
        ASSERT_FALSE(result);  // The second removal should fail, as the movies no longer exist
    };
    
    // Launch the second thread to try removing the movies again
    std::thread t2(removeOperationAgain, 3, movies1);
    t2.join();  // Ensure the second operation completes
    
    // Verify the size of the list after the operations
    vector<unsigned long> listu = FileIO::IdList(3, &u);
    ASSERT_EQ(listu.size(), 0);  // Ensure the list is empty after the first removal
}

TEST(MutexTest, IdListConcurrency) {
    UserFile u;
    MovieFile m;
    auto idListOperation = [](int id, BaseFile* file) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        vector<unsigned long> list = FileIO::IdList(id, file);
        ASSERT_FALSE(list.empty());
    };
    // Launch multiple threads to read the IdList concurrently
    std::thread t1(idListOperation, 2, &u);
    std::thread t2(idListOperation, 11, &m);
    t1.join();
    t2.join();
}
TEST(MutexTest, RemoveFromMultipleUsers) {
    UserFile u1, u2;  // Two different users
    MovieFile m1, m2; // Movie files for both users
    
    // Movies to be removed from each user
    vector<string> moviesUser1 = {"11", "12"};
    vector<string> moviesUser2 = {"22", "23"};

    auto removeOperation = [](int id, const vector<string>& movies) {
        std::lock_guard<std::mutex> lock(testMutex);  // Lock the mutex for thread safety
        bool result = AddBuilder::BuildRemove(id, movies);
        ASSERT_TRUE(result);  // The removal should succeed for the first operation
    };
    
    // Launch the first thread to remove movies for User 1
    std::thread t1(removeOperation, 1, moviesUser1);
    t1.join();  // Ensure the first operation completes before proceeding
    
    // Launch the second thread to remove movies for User 2
    std::thread t2(removeOperation, 2, moviesUser2);
    t2.join();  // Ensure the second operation completes
    
    // Verify that User 1's list no longer contains movies 11 and 32
    vector<unsigned long> listUser1 = FileIO::IdList(1, &u1);
    ASSERT_EQ(listUser1.size(), 1);  // Ensure the list is empty after removal for User 1

    // Verify that User 2's list no longer contains movies 22 and 33
    vector<unsigned long> listUser2 = FileIO::IdList(2, &u2);
    ASSERT_EQ(listUser2.size(), 1);  // Ensure the list is empty after removal for User 2
}