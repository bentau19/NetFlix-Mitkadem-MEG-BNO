# NetFlix-Mitkadem-MEG-BNO

second part of the project.

Each command was encapsulated as a separate class to adhere to the Command Design Pattern.

Application Commands:
- HelpCmd: Provides a list and explanation of all available commands in the application.

- Recommend: Accepts a userid and a movieid as inputs and generates up to 10 movie recommendations based on user similarities.

- Add: Accepts a userid and at least one movieid and associates the specified movies with the user in the system.

* Invalid or unsupported commands result in appropriate error output
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



running examples:
regular run:


exiting the app and returning:

tests runs:




answer to guideline questions:
1. האם העובדה ששמות הפקודות השתנו הצריך מכם לגעת בקוד שאמור להיות ״סגור
   לשינויים אבל פתוח להרחבה״?

1. This fact didn’t make us to touch the closed code (the add func or the recommend func), 
we just had to check first that the new rules are valid (the extended) and then call to the closed code.


2.האם העובדה שנוספו פקודות חדשות הצריך מכם לגעת בקוד שאמור להיות ״סגור
לשינויים אבל פתוח להרחבה״?
2. This fact again didn’t really make us touch the closed code, 
because all the new commend is just an extended to the original one so we just checked the new rules and called to the prev commends.

3. האם העובדה שפלט הפקודות השתנה הצריך מכם לגעת בקוד שאמור להיות ״סגור
  לשינויים אבל פתוח להרחבה״?
3.Again not really, 
we putted all the outputs in a define so it was just easy to change just the string of it and it change all the outputs all over the closed code
  
4. "האם העבודה שהקלט/פלט מגיע מסוקטים במקום  הקונסול מחייב ממכם לגעת בקוד שאמור להיות 'סגור לשינויים אבל פתוח להרחבה'?"

4. 
only minors changes as, add a print function instead of having excute print the output ,and add to the menu interface a print command for printing the result.
Other than that, we only needed to extend it so there is a server menu where the actions of receiving input and printing are done via the server and client sockets instead of through the console.