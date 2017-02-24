# Getting JSON returned from the API
- I used a BufferedReader and InputStreamReader object to get the response from the Connection (HttpURLConnection object).
<br/>
- I saved it into a StringBuilder  object.
<br/>

# Using Gson JAR
- I downloaded (gson-2.3.1.jar), created a new folder (lib), added the jar to it, added the jar to the project's java build bath, and finally added it as dependency in pom.xml file.

# Using Postman Google plugin
- I used Postman to organized the JSON object.

# Creating Classes
- After getting the organized JSOn object, I created classes that are exactly the same as the JSON object return.

# Main
- I used created a Gson object to convert the StringBuilder object that contains the JSON object as a string to a BigOffer object that I have created as a reflection of the JSON object.
<br/>
- I created static (so it can be accessed from main method) methods, to organize each section of the Offer's info.
<br/>
- I included the HTML tags in the StringBuilder's object's string. I did not display the data in a table because some data values are too long to be squeezed in a cell.
<br/>
- Finally, after appending all the data into the StringBuilder's object's string, I returned the object's string


# NOTES:
- This is my first time working with JSON, maven project, and GitHub, that's why it took more time that expected.