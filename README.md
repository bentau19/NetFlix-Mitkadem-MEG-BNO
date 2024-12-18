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

        docker run -it --name CoolProjectContainer supercoolproject
        ./Tests

    to run the server:

       (if not run the tests) docker run -it --name CoolProjectContainer supercoolproject
       (if yes run the tests) docker exec -it CoolProjectContainer bash
                                cd build
       ./server 12345 
       12345 for port 12345


    to run the client:

        docker exec -it CoolProjectContainer bash
        python3 ./src/Client.py 127.0.0.1 12345
        for ip 127.0.0.1 and port 12345

-----------------------------------------------------------------------------------------------------------------------------------------
SOLID PART2 NOTES:
    the files:
        1) the addition of the function to delete only meant adding 1 more function to the fileio class,
            which handles all the complex reading and writing.
        2) the socket addition did pose a challange as reading and writing at the same time is problematic
            the easiest way to see it is if a user tries deleting a file and another adds one the writing of the adding could override the writing of the delete,
            this meant *changing the code in basefile*, as it was needed to add locks to basefile and addbuild


running examples:
regular run:
![alt text](image.png)

exiting the app and returning:
![alt text](image-1.png)

tests runs:
![alt text](image-2.png)