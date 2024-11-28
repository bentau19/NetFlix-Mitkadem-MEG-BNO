
using namespace std;
#include <iostream>
#include <fstream>

string getCommandAsk(string task) {
    int i = 0;

    // Step 1: Skip leading spaces
    while (i < task.length() && task[i] == ' ') {
        ++i;
    }

    // Step 2: Find the end of the first word
    while (i < task.length() && task[i] != ' ') {
        ++i;
    }

    while (i < task.length() && task[i] == ' ') {
        ++i;
    }

    // Step 4: Capture and return the remaining string
    if (i < task.length()) {
        string remaining = task.substr(i); // Return substring starting from i
        return remaining;
    } else {
        return ""; // No remaining string
    }
}
    int main(){
     while ((true))
     {
        string task;
        cin>>task;
        cout<<getCommandAsk(task)<<endl;
     }
     

}
