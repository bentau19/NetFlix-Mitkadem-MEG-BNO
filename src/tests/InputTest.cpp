#include <gtest/gtest.h>
#include "../Menu/ConsoleMenu.h"
#include <string>        // Include string

TEST(InputTest, Basic){
   ConsoleMenu* menu = new ConsoleMenu();
   EXPECT_NO_THROW(ConsoleMenu* menu = new ConsoleMenu());
   string str = "thats that me esspreso";
   EXPECT_EQ(menu->getCommand(str), "thats");
   EXPECT_EQ(menu->getCommandAsk(str), "that me esspreso");
}
int main(int argc, char **argv){
  ::testing::InitGoogleTest(&argc, argv);
  return RUN_ALL_TESTS();
}