# NetFlix-Mitkadem-MEG-BNO
third part of the project.
(secend part is now in branch Sprint2complete)
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### updates:
- pogram now running on New Node.js server , using RESTful API and mongoDB data base.
- old server now is use as recommendation system.
- The recommendation server is now using a thread pool instead of multi-threading (3 threads).


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
### The new Node.js application was implemented using the MVC structure.

Alow the flowing roots:
- http://localhost:3000/api/users
    suport: POST
- http://localhost:3000/api/users/:id
    suport: GET
  - http://localhost:3000/api/tokens
    suport: POST 
  - http://localhost:3000/api/categories
    suport: GET , POST
- http://localhost:3000/api/categories/:id
    suport: GET , PATCH ,DELETE
  - http://localhost:3000/api/movies
    suport: GET , POST
- http://localhost:3000/api/movies/:id
    suport: GET , PUT ,DELETE
  - http://localhost:3000/api/movies/:id/recommend/
    suport: GET , POST
- http://localhost:3000/api/search/:query/
    suport: GET
  
*Invalid or unsupported roots are met with a corresponding error message.
  
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
       
       cd build
       ./server 12345 

    to run the nodejs:
        docker exec -it netflix-mitkadem-meg-bno-nodejs-1 /bin/bash
        cd nodejs
        npm start

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

(For the clearty we made the running test with enter command and server respose, at the actual project we not printing it as the guide says)
### running examples:

## Running Servers
1. **Run C++ Server**  
   ![Run C++ Server](photos/curlcommands/1%20(run%20cpp%20server).png)

2. **Run Node.js Server**  
   ![Run Node.js Server](photos/curlcommands/2%20(run%20nodejs%20server).png)

## Curl Commands
3. **Create User**  
   ![Create User](photos/curlcommands/3%20(create%20user).png)

4. **Get User**  
   ![Get User](photos/curlcommands/4%20(get%20user).png)

5. **Tokens POST**  
   ![Tokens POST](photos/curlcommands/5%20(tokens%20POST).png)

6. **POST Categories**  
   ![POST Categories](photos/curlcommands/6%20(POST%20categories).png)

7. **GET Categories**  
   ![GET Categories](photos/curlcommands/7%20(GET%20categories).png)

8. **GET All Categories**  
   ![GET All Categories](photos/curlcommands/8%20(GET%20all%20categories).png)

9. **PATCH Categories**  
   ![PATCH Categories](photos/curlcommands/9%20(PATCH%20categories).png)

10. **POST Movies**  
    ![POST Movies](photos/curlcommands/10%20(POST%20movies).png)

11. **GET Movies**  
    ![GET Movies](photos/curlcommands/11%20(GET%20movies).png)

12. **GET Recommend**  
    ![GET Recommend](photos/curlcommands/12%20(GET%20recommend).png)

tests runs:

![Description of Image](photos/TESTS.png)

-----------------docker exec -it netflix-mitkadem-meg-bno-cpp-1 /bin/bash-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


