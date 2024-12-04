#include <gtest/gtest.h>
#include "../File_Classes/UserMovies.h"
#include "../File_Classes/UserFile.h"
#include "../File_Classes/StringHandler.h"
#include <vector>
#include <string>
#include "../File_Classes/MovieFile.h"
#include "../File_Classes/AddBuilder.h"
#include "../Commands/RecommendCommand.h"
#include "../Commands/HelpCommand.h"
#include "../Commands/AddCommand.h"
#include "../Commands/ICommand.h"

TEST(RecomedionCommand,Movie){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    start.execute("3 100 104 105 107 108");
    start.execute("4 101 105 106 107 109 110");
    start.execute("5 100 102 103 105 108 111");
    start.execute("6 100 103 104 110 111 112 113");
    start.execute("7 102 105 106 107 108 109 110");
    start.execute("8 101 104 105 106 109 111 114");
    start.execute("9 100 103 105 107 112 113 115");
    start.execute("10 100 102 105 106 107 109 110 116");
    RecommendCommand hey;
    std::vector<int> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<int> expected = {105, 106, 111, 110, 112, 113, 107, 108, 109, 114};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie3){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    RecommendCommand hey;
    std::vector<int> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<int> expected = {105, 106};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie5){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    start.execute("3 1 2 4 5 6");
    RecommendCommand hey;
    std::vector<int> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<int> expected = {105, 106};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie4){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 105 106");
    RecommendCommand hey;

    EXPECT_THROW(hey.TestExFunc("1 104");, std::invalid_argument);

//    ASSERT_NO_THROW(MovieFile u);
}

TEST(RecomedionCommand,Movie2){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    start.execute("3 100 104 105 107 108");
    start.execute("4 101 105 106 107 109 110");
    start.execute("5 100 102 103 105 108 111");
    start.execute("6 100 103 104 110 111 112 113");
    start.execute("7 102 105 106 107 108 109 110");
    start.execute("8 101 104 105 106 109 111 114");
    start.execute("9 100 103 105 107 112 113 115");
    start.execute("10 100 102 105 106 107 109 110 116");
    RecommendCommand hey;

    EXPECT_THROW(hey.TestExFunc("1 1");, std::invalid_argument);
//    ASSERT_NO_THROW(MovieFile u);
}

TEST(ADDCMD,manySpaces1){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    //make many spaces
    AddCommand b;
    b.execute("1     2    3   3   4  4 5  6  7  8     8 9");
    vector<int> watchedList = UserMovies::IdList(1,&userFile);
    vector<int> target = {2,3,4,5,6,7,8,9};
    ASSERT_EQ(watchedList, target);
}
TEST(ADDCMD,manySpaces2){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    //make many spaces
    AddCommand b;
    b.execute("       1     2                               3   3   4  4 5  6                                                                             7  8     8 9                        ");
    vector<int> watchedList = UserMovies::IdList(1,&userFile);
    vector<int> target = {2,3,4,5,6,7,8,9};
    ASSERT_EQ(watchedList, target);
}
TEST(ADDCMD,MovieExist){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand b;
    for (int i = 0; i < 4; ++i) {
        b.execute("1 2 3 4");
    }
    vector<int> watchedList = UserMovies::IdList(1,&userFile);
    vector<int> target = {2,3,4};
    ASSERT_EQ(watchedList, target);
}
//
TEST(ADDCMD,SimpleRun){
//delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();

    AddCommand a;
    AddCommand b;
    a.execute("1 2 3 4");
    b.execute("2 3 4 5");
    a.execute("2 6 7 8");
    b.execute("1 5 6 7");

    vector<int> watchedList = UserMovies::IdList(1,&userFile);
    vector<int> target = {2,3,4,5,6,7};
    vector<int> watchedList2 = UserMovies::IdList(2,&userFile);
    vector<int> target2 = {3,4,5,6,7,8};
    ASSERT_EQ(watchedList, target);
    ASSERT_EQ(watchedList2, target2);
}
TEST(ADDCMD,commendNotValid1){
//delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();
    AddCommand a;
    EXPECT_THROW(a.execute("1"), std::invalid_argument);
}
TEST(ADDCMD,commendNotValid2){
//delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();
    AddCommand a;
    EXPECT_THROW(a.execute("hello its ben!"), std::invalid_argument);
}
