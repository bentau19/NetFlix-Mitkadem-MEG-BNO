#include <gtest/gtest.h>
#include "../File_Classes/FileIO.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/StringHandler.h"
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
    ASSERT_EQ(u.display(),"Users check");
    ASSERT_NO_THROW(m.Write("check2")); // Ensure write does not throw for MovieFile
    ASSERT_EQ(m.display(),"Movies: \ncheck2");
    ASSERT_NO_THROW(m.deleteItem());
    ASSERT_NO_THROW(u.deleteItem());
}
TEST(ReadList,empty){
    UserFile u;
    MovieFile m;
    vector<unsigned long> listu = FileIO::IdList(1,&u);
    vector<unsigned long> listm = FileIO::IdList(1,&m);
    ASSERT_EQ(listu.size(),0);
    ASSERT_EQ(listm.size(),0);
}
TEST(Add,threeToEach){
    vector<string> movies1;
    vector<string> movies3;
    vector<string> movies2;
    movies1.push_back("11");
    movies1.push_back("12");
    movies1.push_back("13");
    movies2.push_back("11");
    movies2.push_back("22");
    movies2.push_back("23");
    movies3.push_back("11");
    movies3.push_back("32");
    movies3.push_back("33");
    ASSERT_TRUE(AddBuilder::BuildAdd(1,movies1));
    ASSERT_TRUE(AddBuilder::BuildAdd(2,movies2));
    ASSERT_TRUE(AddBuilder::BuildAdd(3,movies3));
}
TEST(ReturnList,User){
    UserFile u;
    vector<unsigned long> listu = FileIO::IdList(2,&u);
    ASSERT_EQ(listu[0],11);
    ASSERT_EQ(listu[1],22);
    ASSERT_EQ(listu[2],23);
}
TEST(ReturnList,movies){
    MovieFile u;
    vector<unsigned long> listu = FileIO::IdList(11,&u);
    ASSERT_EQ(listu[0],1);
    ASSERT_EQ(listu[1],2);
    ASSERT_EQ(listu[2],3);
}
TEST(RemoveList,RemoveMoviesFromUser){
    vector<string> movies3;
    movies3.push_back("32");
    movies3.push_back("33");
    ASSERT_TRUE(AddBuilder::BuildRemove(3,movies3));
    ASSERT_FALSE(AddBuilder::BuildRemove(1,movies3));
    ASSERT_FALSE(AddBuilder::BuildRemove(5,movies3));
}