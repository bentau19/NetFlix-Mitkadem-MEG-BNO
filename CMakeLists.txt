cmake_minimum_required(VERSION 3.27)
project(netflix)

set(CMAKE_CXX_STANDARD 17)

add_executable(netflix src/main.cpp
        src/Commands/AddCommand.cpp
        src/Commands/AddTest.cpp
        Data_Classes/Movie.cpp
        Data_Classes/User.cpp
        src/File_Classes/AddBuilder.cpp
        src/File_Classes/BaseFile.cpp
        src/File_Classes/FileTests.cpp
        src/File_Classes/main.cpp
        src/File_Classes/MovieFile.cpp
        src/File_Classes/StringHandler.cpp
        src/File_Classes/UserFile.cpp
        src/File_Classes/UserMovies.cpp
        src/Commands/RecomedionCommand.cpp
        src/Commands/RecomedionCommand.h
        src/StringTools/StringTools.cpp
        src/StringTools/StringTools.h)
