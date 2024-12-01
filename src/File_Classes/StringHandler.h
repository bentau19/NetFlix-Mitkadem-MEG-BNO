#ifndef STRINGHANDLER_H
#define STRINGHANDLER_H

#include <string>
#include <vector>

class StringHandler {
public:
    // Splits a given string by a delimiter and returns a vector of strings
    static std::vector<std::string> split(const std::string& str, char delimiter);

    // Joins a vector of strings into a single string using the given delimiter
    static std::string join(const std::vector<std::string>& array, char delimiter);

   static std::vector<std::string> StringHandler::splitString(const std::string& str);
};

#endif // STRINGHANDLER_H
