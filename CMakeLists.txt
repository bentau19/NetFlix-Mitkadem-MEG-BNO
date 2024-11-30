cmake_minimum_required(VERSION 3.27)
project(netflix)

set(CMAKE_CXX_STANDARD 17)

add_executable(netflix main.cpp
        Commands/Add.cpp
        Commands/AddTest.cpp
        Data_Classes/Movie.cpp
        Data_Classes/User.cpp
        File_Classes/AddBuilder.cpp
        File_Classes/BaseFile.cpp
        File_Classes/FileTests.cpp
        File_Classes/main.cpp
        File_Classes/MovieFile.cpp
        File_Classes/StringHandler.cpp
        File_Classes/UserFile.cpp
        File_Classes/UserMovies.cpp
        Commands/Recommend.cpp
        Commands/Recommend.h
        StringTools/StringTools.cpp
        StringTools/StringTools.h)
