#include <gtest/gtest.h>
#include "../File_Classes/FileIO.h"
#include "../File_Classes/UserFile.h"
#include <vector>
#include "../File_Classes/MovieFile.h"
#include "Commands/Data_Manipulation/Get.h"
#include "Commands/Add_Data/Post.h"
#include "Commands/Add_Data/Patch.h"
#include "Commands/General/Validity.h"
#include "Commands/Delete_Data/Delete.h"

void DelTemp(){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();
}


TEST(RecomedionCommand,Movie){
    DelTemp();

    Post start;
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
    Get hey;
    std::vector<unsigned long> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<unsigned long> expected = {105, 106, 111, 110, 112, 113, 107, 108, 109, 114};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie3){
    DelTemp();

    Post start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    Get hey;
    std::vector<unsigned long> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<unsigned long> expected = {105, 106};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie5){
    DelTemp();
    Post start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    start.execute("3 1 2 4 5 6");
    Get hey;
    std::vector<unsigned long> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<unsigned long> expected = {105, 106};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie4){
    DelTemp();
    Post start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 105 106");
    Get hey;

    EXPECT_THROW(hey.TestExFunc("1 104");, std::invalid_argument);

//    ASSERT_NO_THROW(MovieFile u);
}

TEST(RecomedionCommand,Movie2){
    DelTemp();
    Post start;
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
    Get hey;

    EXPECT_THROW(hey.TestExFunc("1 1");, std::invalid_argument);
//    ASSERT_NO_THROW(MovieFile u);
}

TEST(ADDCMD,manySpaces1){
    //delete trash
    UserFile userFile;
    DelTemp();
    //make many spaces
    Post b;
    b.execute("1     2    3   3   4  4 5  6  7  8     8 9");
    vector<unsigned long> watchedList = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3,4,5,6,7,8,9};
    ASSERT_EQ(watchedList, target);
}
TEST(ADDCMD,manySpaces2){
    //delete trash
    UserFile userFile;
    DelTemp();
    //make many spaces
    Post b;
    b.execute("       1     2                               3   3   4  4 5  6                                                                             7  8     8 9                        ");
    vector<unsigned long> watchedList = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3,4,5,6,7,8,9};
    ASSERT_EQ(watchedList, target);
}

//
TEST(ADDCMD,SimpleRun){
//delete trash
    UserFile userFile;
    DelTemp();
    Post a;
    Patch b;
    a.execute("1 2 3 4");
    a.execute("2 3 4 5");
    b.execute("2 6 7 8");
    b.execute("1 5 6 7");

    vector<unsigned long> watchedList = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3,4,5,6,7};
    vector<unsigned long> watchedList2 = FileIO::IdList(2,&userFile);
    vector<unsigned long> target2 = {3,4,5,6,7,8};
    ASSERT_EQ(watchedList, target);
    ASSERT_EQ(watchedList2, target2);
}
TEST(ADDCMD,commendNotValid1){
//delete trash
    DelTemp();
    Post a;
    ASSERT_EQ(a.execute("1"), Validity::ValidityAlert(GenFail));
}
TEST(ADDCMD,commendNotValid2){
//delete trash
    DelTemp();
    Post a;
    ASSERT_EQ(a.execute("hello its ben!"), Validity::ValidityAlert(GenFail));
}


TEST(Post,userNoExist){
    //delete trash
    UserFile userFile;
    DelTemp();
    Post a;
    a.execute("1 2 3");
    a.execute("2 2 3");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> ret2 = FileIO::IdList(2,&userFile);
    vector<unsigned long> target = {2,3};
    ASSERT_EQ(ret1, target);
    ASSERT_EQ(ret2, target);
}

TEST(Post,userDoesExist){
    //delete trash
    DelTemp();
    Post a;
    a.execute("1 2 3");
    ASSERT_EQ(a.execute("1 4 5"), Validity::ValidityAlert(GenFail));

}

TEST(Post,tooManySpaces){
    //delete trash
    UserFile userFile;
    DelTemp();
    Post a;
    a.execute("1              2        3");
    a.execute("          2 2 3                      ");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> ret2 = FileIO::IdList(2,&userFile);
    vector<unsigned long> target = {2,3};
    ASSERT_EQ(ret1, target);
    ASSERT_EQ(ret2, target);
}
TEST(Post,wrongInput){
    //delete trash
    DelTemp();
    Post a;
    a.execute("1 2 3");
    ASSERT_EQ(a.execute("2"), Validity::ValidityAlert(GenFail));
}


TEST(Patch,userExist){
    //delete trash
    UserFile userFile;
    DelTemp();
    Post a;
    Patch b;
    a.execute("1 2 3");
    b.execute("1 4 5");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3, 4,5};
    ASSERT_EQ(ret1, target);
}

TEST(Patch,userDoesNotExist){
    //delete trash
    DelTemp();
    Patch a;
    ASSERT_EQ(a.execute("1 4 5"), Validity::ValidityAlert(GenFail));

}

TEST(Patch,tooManySpaces){
    //delete trash
    UserFile userFile;
    DelTemp();
    Post a;
    Patch b;
    a.execute("1          2              3");
    b.execute("1          4        6");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3, 4,6};
    ASSERT_EQ(ret1, target);
}
TEST(Patch,wrongInput){
    //delete trash
    DelTemp();
    Post b;
    b.execute("1          2              3");
    Patch a;
    ASSERT_EQ(a.execute("1"), Validity::ValidityAlert(GenFail));
}

TEST(Delete,Valid_Proccess){
    //delete trash
    DelTemp();
    UserFile userFile;
    Post n;
    n.execute("1 2 4 ");
    Patch a;
    a.execute("1 3 5");
    Delete d;
    d.execute("1 2 3");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {4,5};
    ASSERT_EQ(ret1, target);
}
TEST(Delete,No_user){
    DelTemp();
    Delete d;
    ASSERT_EQ(d.execute("1 2 3"), Validity::ValidityAlert(GenFail));
}
TEST(Delete,No_Movie){
    DelTemp();
    Post n;
    n.execute("1 2 4 ");
    Delete d;
    ASSERT_EQ(d.execute("1 2 3"), Validity::ValidityAlert(GenFail));
}
TEST(Delete,invalid_Commend){
    DelTemp();
    Post n;
    n.execute("1 2 4 ");
    Delete d;
    ASSERT_EQ(d.execute("KJD 12 DJ"), Validity::ValidityAlert(GenFail));
}
TEST(Delete,DelUser){
    DelTemp();
    Post n;
    n.execute("1 2 4 ");
    Delete d;
    d.execute("1 2 4");
    UserFile userFile;
    ASSERT_EQ(Get::isExist(1,&userFile), false);
}
