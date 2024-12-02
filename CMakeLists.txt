cmake_minimum_required(VERSION 3.14)
project(netflix_project)

# GoogleTest requires at least C++14 (if you still want to use GoogleTest)
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

# Include directories
include_directories(src)

# Add the source files for the main executable
add_executable(
 # InputTest
#  src/tests/InputTest.cpp
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

# Link the main executable with GoogleTest (optional, remove if not needed)
target_link_libraries(
  #InputTest
  my_project
  gtest_main  # GoogleTest library (if you need it, otherwise remove this line)
)
