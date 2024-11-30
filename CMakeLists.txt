cmake_minimum_required(VERSION 3.14)
project(my_project)

# GoogleTest requires at least C++14
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/03597a01ee50ed33e9dfd640b249b4be3799d395.zip
)
# For Windows: Prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)


# Add the tests folder to the include path if needed
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/tests)

# Add the source files for the test executable
add_executable(File_Classes_Tests
  tests/File_Classes_Tests.cpp
  File_Classes/BaseFile.cpp
  File_Classes/UserMovies.cpp
  File_Classes/UserFile.cpp
  File_Classes/MovieFile.cpp
  File_Classes/AddBuilder.cpp
  File_Classes/stringhandler.cpp
)


# Link the test executable with GoogleTest
target_link_libraries(
  File_Classes_Tests  # Target executable
  gtest_main  # GoogleTest library
)
