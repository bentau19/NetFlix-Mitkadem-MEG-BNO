#include <gtest/gtest.h>
#include "../File_Classes/FileIO.h"
#include "../File_Classes/UserFile.h"
#include <vector>
#include "../File_Classes/MovieFile.h"
#include "Commands/Data_Manipulation/GetCmd.h"
#include "Commands/Add_Data/PostCmd.h"
#include "Commands/Add_Data/PatchCmd.h"
#include "Commands/General/Validity.h"
#include "Commands/Delete_Data/DeleteCmd.h"
#include "Commands/General/HelpCmd.h"

void DelTemp(){
    //delete trash
    UserFile userFile;
    MovieFile movieFile;
    userFile.deleteItem();
    movieFile.deleteItem();
}


TEST(RecomedionCommand,Movie){
    DelTemp();

    PostCmd start;
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
    GetCmd hey;
    std::vector<unsigned long> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<unsigned long> expected = {105, 106, 111, 110, 112, 113, 107, 108, 109, 114};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie3){
    DelTemp();

    PostCmd start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    GetCmd hey;
    std::vector<unsigned long> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<unsigned long> expected = {105, 106};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie5){
    DelTemp();
    PostCmd start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 104 105 106");
    start.execute("3 1 2 4 5 6");
    GetCmd hey;
    std::vector<unsigned long> res = hey.TestExFunc("1 104");

//     Use Google Test's `ASSERT_EQ` macro for container comparison
    std::vector<unsigned long> expected = {105, 106};
    ASSERT_EQ(res, expected);
//    ASSERT_NO_THROW(MovieFile u);
}
TEST(RecomedionCommand,Movie4){
    DelTemp();
    PostCmd start;
    start.execute("1 100 101 102 103");
    start.execute("2 101 102 105 106");
    GetCmd hey;

    ASSERT_EQ(hey.execute("1 104"),Validity::ValidityAlert(GetSuc)+" \n\n" );

//    ASSERT_NO_THROW(MovieFile u);
}

TEST(RecomedionCommand,Movie2){
    DelTemp();
    PostCmd start;
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
    GetCmd hey;
    ASSERT_EQ(hey.execute("1 1"),Validity::ValidityAlert(GetSuc)+" \n\n" );
//    ASSERT_NO_THROW(MovieFile u);
}

TEST(ADDCMD,manySpaces1){
    //delete trash
    UserFile userFile;
    DelTemp();
    //make many spaces
    PostCmd b;
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
    PostCmd b;
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
    PostCmd a;
    PatchCmd b;
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
    PostCmd a;
    ASSERT_EQ(a.execute("1"), Validity::ValidityAlert(syntaxErr));
}
TEST(ADDCMD,commendNotValid2){
//delete trash
    DelTemp();
    PostCmd a;
    ASSERT_EQ(a.execute("hello its ben!"), Validity::ValidityAlert(syntaxErr));
}


TEST(Post,userNoExist){
    //delete trash
    UserFile userFile;
    DelTemp();
    PostCmd a;
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
    PostCmd a;
    a.execute("1 2 3");
    ASSERT_EQ(a.execute("1 4 5"), Validity::ValidityAlert(GenFail));

}

TEST(Post,tooManySpaces){
    //delete trash
    UserFile userFile;
    DelTemp();
    PostCmd a;
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
    PostCmd a;
    a.execute("1 2 3");
    ASSERT_EQ(a.execute("2"), Validity::ValidityAlert(syntaxErr));
}


TEST(Patch,userExist){
    //delete trash
    UserFile userFile;
    DelTemp();
    PostCmd a;
    PatchCmd b;
    a.execute("1 2 3");
    b.execute("1 4 5");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3, 4,5};
    ASSERT_EQ(ret1, target);
}

TEST(Patch,userDoesNotExist){
    //delete trash
    DelTemp();
    PatchCmd a;
    ASSERT_EQ(a.execute("1 4 5"), Validity::ValidityAlert(GenFail));

}

TEST(Patch,tooManySpaces){
    //delete trash
    UserFile userFile;
    DelTemp();
    PostCmd a;
    PatchCmd b;
    a.execute("1          2              3");
    b.execute("1          4        6");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {2,3, 4,6};
    ASSERT_EQ(ret1, target);
}
TEST(Patch,wrongInput){
    //delete trash
    DelTemp();
    PostCmd b;
    b.execute("1          2              3");
    PatchCmd a;
    ASSERT_EQ(a.execute("1"), Validity::ValidityAlert(syntaxErr));
}

TEST(Delete,Valid_Proccess){
    //delete trash
    DelTemp();
    UserFile userFile;
    PostCmd n;
    n.execute("1 2 4 ");
    PatchCmd a;
    a.execute("1 3 5");
    DeleteCmd d;
    d.execute("1 2 3");
    vector<unsigned long> ret1 = FileIO::IdList(1,&userFile);
    vector<unsigned long> target = {4,5};
    ASSERT_EQ(ret1, target);
}
TEST(Delete,No_user){
    DelTemp();
    DeleteCmd d;
    ASSERT_EQ(d.execute("1 2 3"), Validity::ValidityAlert(GenFail));
}
TEST(Delete,No_Movie){
    DelTemp();
    PostCmd n;
    n.execute("1 2 4 ");
    DeleteCmd d;
    ASSERT_EQ(d.execute("1 2 3"), Validity::ValidityAlert(GenFail));
}
TEST(Delete,invalid_Commend){
    DelTemp();
    PostCmd n;
    n.execute("1 2 4 ");
    DeleteCmd d;
    ASSERT_EQ(d.execute("KJD 12 DJ"), Validity::ValidityAlert(syntaxErr));
}
TEST(Delete,DelUser){
    DelTemp();
    PostCmd n;
    n.execute("1 2 4 ");
    DeleteCmd d;
    d.execute("1 2 4");
    UserFile userFile;
    ASSERT_EQ(FileIO::isExists(1, &userFile), true);
}
TEST(HelpCmd, valid){
    DelTemp();
    HelpCmd n;
    std::string res=Validity::ValidityAlert(GetSuc)+
            "\nDELETE, arguments: [userid] [movieid1] [movieid2] ...\nGET, arguments: [userid] [movieid]\nPATCH, arguments: [userid] [movieid1] [movieid2] ...\nPOST, arguments: [userid] [movieid1] [movieid2] ...\nHELP\n";
    ASSERT_EQ(n.execute(""), res);
}
TEST(HelpCmd, no_valid){
    DelTemp();
    HelpCmd n;
      ASSERT_EQ(n.execute("1"), Validity::ValidityAlert(syntaxErr));
}