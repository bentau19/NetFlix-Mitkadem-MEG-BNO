#include <gtest/gtest.h>
#include "ConsoleMenu.cpp"
TEST(InputTests, sainty){
   ConsoleMenu menu = new ConsoleMenu();
   string str = "thats that me esspreso";
   EXPECT_EQ(menu->getCommand(str), "thats");
   EXPECT_EQ(menu->getCommandAsk(str), "that me esspreso");
}