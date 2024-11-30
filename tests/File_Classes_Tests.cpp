#include <gtest/gtest.h>
#include "../File_Classes/UserMovies.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/stringhandler.h"
#include <vector>
#include <string>
#include "../File_Classes/MovieFile.h"
#include "../File_Classes/AddBuilder.h"
TEST(FileCreation,Movie){
    ASSERT_NO_THROW(MovieFile u);
}
TEST(FileCreation,User){
    ASSERT_NO_THROW(UserFile u);
}
TEST(FileDeletion, Movie_File){
    MovieFile m;
    ASSERT_NO_THROW(m.deleteItem());
}
TEST(FileDeletion, User_File){
    UserFile u;
    ASSERT_NO_THROW(u.deleteItem());
}
TEST(FileWrite,BaseFileWrite){
    UserFile u;
    MovieFile m;
    ASSERT_NO_THROW(u.Write("check")); // Ensure write does not throw for UserFile
    cout<< u.display()<<endl;
    ASSERT_EQ(u.display(),"Users check");
    ASSERT_NO_THROW(m.Write("check2")); // Ensure write does not throw for MovieFile
    ASSERT_EQ(m.display(),"Movies check2");
}
