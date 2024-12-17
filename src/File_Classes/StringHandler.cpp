#include "StringHandler.h"
#include <string>
#include "../Menu/ConsoleMenu.h"
#include "Commands/General/Validity.h"
#include <iostream>
#include <fstream>
#include <stdexcept>
#include <sstream>

using namespace std;

std::vector<std::string> StringHandler::split(const std::string& str, char delimiter) {
    std::vector<std::string> result;
    size_t start = 0;
    size_t end = str.find(delimiter);

    // Extract substrings based on delimiter
    while (end != std::string::npos) {
        result.push_back(str.substr(start, end - start));
        start = end + 1; // Move past the delimiter
        end = str.find(delimiter, start);
    }

    // Add the final segment
    result.push_back(str.substr(start));

    return result;
}

std::string StringHandler::join(const std::vector<std::string>& array, char delimiter) {
    std::string result;

    for (size_t i = 0; i < array.size(); ++i) {
        result += array[i];
        if (i < array.size() - 1) { // Add delimiter between elements, not at the end
            result += delimiter;
        }
    }

    return result;
}

std::vector<std::string> StringHandler::splitString(const std::string& str) {
    std::vector<std::string> res;
    std::stringstream ss(str);
    std::string item;
    try{
        while (getline(ss, item, ' ')) {
            if (!item.empty()) {
                for (char ch : item) {
                    if (!isdigit(ch)) {
                        throw std::invalid_argument(ERR400);  // Found a non-digit character
                    }
                }
                res.push_back(item);
            }
        }}catch(...){
        throw std::invalid_argument(ERR400);
    }
    return res;
}