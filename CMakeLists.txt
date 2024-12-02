cmake_minimum_required(VERSION 3.14)
project(netflix_project)

# Use at least C++14
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Fetch GoogleTest
include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/03597a01ee50ed33e9dfd640b249b4be3799d395.zip
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

# Include directories
include_directories(src)

# Main executable
add_executable(
  my_project
  src/main.cpp 
  src/ConsoleMenu.cpp
  src/App.cpp
  src/File_Classes/UserFile.cpp
  src/File_Classes/BaseFile.cpp
  src/File_Classes/StringHandler.cpp
  src/File_Classes/UserMovies.cpp
  src/File_Classes/MovieFile.cpp
  src/File_Classes/AddBuilder.cpp
  src/Commands/AddCommand.cpp
  src/Commands/HelpCommand.cpp
  src/Commands/RecommendCommand.cpp
)

# Tests executable
add_executable(
  Tests
  src/tests/File_Classes_Tests.cpp
  src/tests/Commends_Tests.cpp
  src/tests/InputTest.cpp
  src/ConsoleMenu.cpp
  src/App.cpp
  src/File_Classes/UserFile.cpp
  src/File_Classes/BaseFile.cpp
  src/File_Classes/StringHandler.cpp
  src/File_Classes/UserMovies.cpp
  src/File_Classes/MovieFile.cpp
  src/File_Classes/AddBuilder.cpp
  src/Commands/AddCommand.cpp
  src/Commands/HelpCommand.cpp
  src/Commands/RecommendCommand.cpp
)

# Link GoogleTest to the test target
target_link_libraries(
  Tests
  gtest_main
)
