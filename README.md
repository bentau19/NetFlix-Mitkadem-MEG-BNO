# NetFlix-Mitkadem-MEG-BNO
second part of the project.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### updates:
-  The server and client applications (use threads to allow multiple clients to connect simultaneously).
-  The delete command is now supported.
  
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Each command was encapsulated as a separate class to adhere to the Command Design Pattern.

Application Commands:
- help: Provides a list and explanation of all available commands in the application.
- GET: Accepts a userid and a movieid as inputs and generates up to 10 movie recommendations based on user similarities.
- POST/PATCH: Accepts a userid and at least one movieid and associates the specified movies with the user in the system only if
   the user is not/already exists.
- DELETE: Accepts a userid and at least one movieid and delete the movies from the user watchlist.
  
*Invalid or unsupported commands are met with a corresponding error message.
  
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


### program testing and running instractions:

    to build the project :
        docker-compose build
        docker-compose up -d
    to run cpp tests:
        docker exec -it netflix-mitkadem-meg-bno-cpp-1 /bin/bash
        cd build
        ./Tests

    to run the cpp server:
       docker exec -it netflix-mitkadem-meg-bno-cpp-1 /bin/bash
       cd build
       ./server 12345 

    to run the nodejs:
        docker exec -it netflix-mitkadem-meg-bno-nodejs-1 /bin/bash
        cd nodejs
        npm start

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

(For the clearty we made the running test with enter command and server respose, at the actual project we not printing it as the guide says)
### running examples:

regular run:

![Description of Image](photos/multy1.png)
![Description of Image](photos/multy2.png)
![Description of Image](photos/multy3.png)
![Description of Image](photos/multy4.png)

exiting the app and returning:

![Description of Image](photos/RESUME.png)
![Description of Image](photos/RESUME2.png)


tests runs:

![Description of Image](photos/TESTS.png)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

 **answer to guideline questions:**

1. *"Did the fact that the command names changed require you to modify code that is supposed to be 'closed to changes but open to extensions'?"*

This fact didn’t require us to touch the closed code (e.g., the add function or the recommend function). We just had to verify that 
the new rules were valid (the extended ones) and then call the closed code.



2. *"Did the fact that new commands were added require you to modify code that is supposed to be 'closed to changes but open to extensions'?"*

This fact again didn’t really require us to touch the closed code because all the new commands are just an extension of the original ones. We simply checked the new rules and then called the previous commands.

file wise:  the addition of the function to delete only meant adding 1 more function to the fileio class,
            which handles all the complex reading and writing.



3. *"Did the fact that the output of the commands changed require you to modify code that is supposed to be 'closed to changes but open to extensions'?"*

Again, not really. We placed all the outputs in a define statement, so it was easy to just change the string there, and it updated 
all the outputs across the closed code.



4. *"Did the fact that the input/output now comes from sockets instead of the console require you to modify code that is supposed to be 'closed to changes but open to extensions'?"*

Only minor changes were needed, such as adding a print function instead of having the execute method print the output directly, and adding a print command to the menu interface. Other than that, we only extended the functionality by creating a server menu where receiving input and printing results are done via server and client sockets instead of through the console.
Also  a mutex is implemented to control access to the app data, ensuring that client threads do not override each other.
 
file wise : the socket addition did pose a challange as reading and writing at the same time is problematic
            the easiest way to see it is if a user tries deleting a file and another adds one the writing of the adding could override the writing of the delete,
            this meant changing the code in basefile, as it was needed to add locks to basefile and addbuild

