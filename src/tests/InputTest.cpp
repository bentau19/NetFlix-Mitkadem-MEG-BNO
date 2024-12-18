#include <gtest/gtest.h>
#include "../Menu/ServerMenu.h"
#include <string>        // Include string

TEST(InputTest, Basic){
   ServerMenu* menu = new ServerMenu();
   EXPECT_NO_THROW(ServerMenu* menu = new ServerMenu());
   string str = "thats that me esspreso";
   EXPECT_EQ(menu->getCommand(str), "thats");
   EXPECT_EQ(menu->getCommandAsk(str), "that me esspreso");
}
int main(int argc, char **argv){
  ::testing::InitGoogleTest(&argc, argv);
  return RUN_ALL_TESTS();
}