# NetFlix-Mitkadem-MEG-BNO
first part of project

running example:
![alt text](image.png)

program testing and running instractions:
    to build the project :)-
    
        docker build -t supercoolproject .

    to run the tests:

        docker run -it supercoolproject
        ./Tests

    to run the main first time(after ctr+c or any way you get out of container):

        docker run --name WoWThisProjectIsSoGood -v ${pwd}/data:/app/data -it cpp
        ./main

    to run the main after first time(after ctr+c and ctr+z or any way you get out of container):

        docker start -ai WoWThisProjectIsSoGood
