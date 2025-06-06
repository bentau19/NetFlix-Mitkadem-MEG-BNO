cmake_minimum_required(VERSION 3.14)
project(netflix_project)

# Use at least C++17
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Fetch GoogleTest
include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/03597a01ee50ed33e9dfd640b249b4be3799d395.zip
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)
find_package(Threads REQUIRED)

# Include directories
include_directories(src)


# Add the source files for the main executable
add_executable(
  clientPy
  src/Client.py 
)

add_executable(
  server
  src/server.cpp
  src/dataClass/Data.cpp
  src/Menu/ServerMenu.cpp
  src/App/App.cpp
  src/ThreadPool/ThreadPool.cpp
  src/Commands/General/Validity.h
  src/File_Classes/UserFile.cpp
  src/File_Classes/BaseFile.cpp
  src/File_Classes/StringHandler.cpp
  src/File_Classes/FileIO.cpp
  src/File_Classes/MovieFile.cpp
  src/File_Classes/AddBuilder.cpp
  src/Commands/General/HelpCmd.cpp
  src/Commands/Data_Manipulation/GetCmd.cpp
  src/Commands/Add_Data/PostCmd.cpp
  src/Commands/Add_Data/PatchCmd.cpp
  src/Commands/Delete_Data/DeleteCmd.cpp
        src/Commands/General/Validity.cpp
)
# Tests executable
add_executable(
  Tests
  src/tests/File_Classes_Tests.cpp
  src/tests/MutexTests.cpp
  src/tests/Commends_Tests.cpp
  src/tests/InputTest.cpp
  src/Menu/ServerMenu.cpp
  src/App/App.cpp
  src/File_Classes/UserFile.cpp
  src/File_Classes/BaseFile.cpp
  src/File_Classes/StringHandler.cpp
  src/File_Classes/FileIO.cpp
  src/File_Classes/MovieFile.cpp
  src/File_Classes/AddBuilder.cpp
  src/dataClass/Data.cpp
  src/Commands/General/HelpCmd.cpp
  src/Commands/General/Validity.cpp
  src/Commands/Data_Manipulation/GetCmd.cpp
  src/Commands/Add_Data/PostCmd.cpp
  src/Commands/Add_Data/PatchCmd.cpp
  src/Commands/Delete_Data/DeleteCmd.cpp

)

# Link GoogleTest to the test target
target_link_libraries(
  Tests
  gtest_main
)

# Link the main executable with GoogleTest (optional, remove if not needed)
target_link_libraries(
  clientPy
  gtest_main  # GoogleTest library (if you need it, otherwise remove this line)
  Threads::Threads  # Link pthreads
)
target_link_libraries(
  server
  gtest_main  # GoogleTest library (if you need it, otherwise remove this line)
  Threads::Threads  # Link pthreads
)
