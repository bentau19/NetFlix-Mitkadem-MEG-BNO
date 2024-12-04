# NetFlix-Mitkadem-MEG-BNO

first part of the project.

Each command was encapsulated as a separate class to adhere to the Command Design Pattern.

Application Commands:
- Help: Provides a list and explanation of all available commands in the application.

- Recommend: Accepts a userid and a movieid as inputs and generates up to 10 movie recommendations based on user similarities.

- Add: Accepts a userid and at least one movieid and associates the specified movies with the user in the system.

* Invalid or unsupported commands were explicitly skipped, as per the project requirements.

running example:
![alt text](image.png)

program running instractions:
cd build
cmake ..
cmake --bulid .
cd debug
./my_project

testing running intruction: