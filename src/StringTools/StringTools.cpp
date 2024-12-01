//
// Created by User on 30/11/2024.
//

#include <stdexcept>
#include <sstream>
#include "StringTools.h"




std::vector<std::string> StringTools::splitString(const std::string& str) {
    std::vector<std::string> res;
    std::stringstream ss(str);
    std::string item;
    try{
        while (getline(ss, item, ' ')) {
            if (!item.empty()) {
                for (char ch : item) {
                    if (!isdigit(ch)) {
                        throw std::invalid_argument("");  // Found a non-digit character
                    }
                }
                res.push_back(item);
            }
        }}catch(...){
        throw std::invalid_argument("");
    }
    return res;
}