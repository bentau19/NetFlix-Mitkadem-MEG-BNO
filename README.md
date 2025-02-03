# NetFlix-Mitkadem-MEG-BNO
forth part of the project.
(third part is now in branch Sprint3complete)
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### updates:
- Web and Android app.
- login and sign in system.
- watching movies with recommend system.
- admin page.


-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Each command in the recommendation system was encapsulated as a separate class to adhere to the Command Design Pattern.

Application Commands:
- help: Provides a list and explanation of all available commands in the application.
- GET: Accepts a userid and a movieid as inputs and generates up to 10 movie recommendations based on user similarities.
- POST/PATCH: Accepts a userid and at least one movieid and associates the specified movies with the user in the system only if
   the user is not/already exists.
- DELETE: Accepts a userid and at least one movieid and delete the movies from the user watchlist.
  
*Invalid or unsupported commands are met with a corresponding error message.
  
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Screens!!.

Alow the flowing roots:
- home page for log in users and one for not
- sign up
- -log in
- admin
- movie watch
  
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### program testing and running instractions:

    to build the project :
        create the folder c:/shlomyNoNerves
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

    react will be started auto and the android apk will be in the shlomyNoNerves folder (the apk time can take some time...)
    to run the react:
        automaticly running

    to run the nodejs:
        take the apk from shlomyNoNerves folder and install it in your android emulator
            

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

further explantion and ruuning examples are in wiki folder as stated in the ex4 file

-----------------docker exec -it netflix-mitkadem-meg-bno-cpp-1 /bin/bash-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


