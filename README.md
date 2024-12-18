# NetFlix-Mitkadem-MEG-BNO

first part of the project.

Each command was encapsulated as a separate class to adhere to the Command Design Pattern.

Application Commands:
- HelpCmd: Provides a list and explanation of all available commands in the application.

- Recommend: Accepts a userid and a movieid as inputs and generates up to 10 movie recommendations based on user similarities.

- Add: Accepts a userid and at least one movieid and associates the specified movies with the user in the system.

* Invalid or unsupported commands were explicitly skipped, as per the project requirements.

-----------------------------------------------------------------------------------------------------------------------------------------

program testing and running instractions:
    to build the project :)-
    
        docker build -t supercoolproject .

    to run the tests:

        docker run -dit --name server_client_container cpp-python-server-client

        ./Tests

    to run the server (first time in contnier):

       docker run -dit --name server_client_container supercoolproject
       ./build/server 12345


    to run the client:

        docker exec -it server_client_container bash
        python3 ./src/Client.py 127.0.0.1 12345


    to run the server :

        docker exec -it server_client_container bash
       ./build/server 12345

-----------------------------------------------------------------------------------------------------------------------------------------



running examples:
regular run:
![alt text](image.png)

exiting the app and returning:
![alt text](image-1.png)

tests runs:
![alt text](image-2.png)