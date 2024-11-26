#ifndef USER_FILE
#define USER_FILE
#include <string>
class UserFile : public Ifile
{
private:
    void create(const std::string name);
    void deleteItem(const std::string name);
public:
    void display();
    UserFile(/* args */);
    ~UserFile();
};






#endif