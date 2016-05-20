###########################################################################
                           Team Members
###########################################################################
Chandni Balchandani - 010725052 - chandni.balchandani@sjsu.edu
Parteek Makar - 010722062 - parteek.makkar@sjsu.edu
Rakshith Koravadi Hatwar - 010718474 - rakshithkoravadi.hatwar@sjsu.edu
Sneha Jain - 010694697 - sneha.l.jain@sjsu.edu

###########################################################################
                   Application Access URL (Amazon EC2)
###########################################################################
1) ApplicationHomePage - http://52.33.41.84:8080/term/
2) SignupPage - http://52.33.41.84:8080/term/signup
3) LoginPage - http://52.33.41.84:8080/term/login - Common Login Page for ADMIN and USER
4) UserHomepage - http://52.33.41.84:8080/term/user/
5) AdminHomePage - http://52.33.41.84:8080/term/admin/

###########################################################################
                       Administrator Credentials
###########################################################################

----------------------------------------------------
||  UserName  ||       chandni.world@yahoo.com    ||
----------------------------------------------------
||  Password  ||               123456             ||
----------------------------------------------------

###########################################################################
                        Bitbucket Repository URL
###########################################################################

WebPage URL          -   https://bitbucket.org/cmpe275termprojectteam/termproject

Repository Clone URL -   https://rakshiithk@bitbucket.org/cmpe275termprojectteam/termproject.git

###########################################################################
                        Build Instruction
###########################################################################

Prerequisite ==> Oracle Java 8 JRE and JDK, Apache Maven 3.3.9 ,Apache Tomcat 8 Server

1) Clone the Repository from https://rakshiithk@bitbucket.org/cmpe275termprojectteam/termproject.git or copy the Archive of Project in the Submission to Local folder.
2) Navigate to Project Folder from the previous Step and open the terminal at current folder location.
3) Clean the Build Folder by using the Command ------------------------------- mvn clean
4) Build the WAR File to be deployed in the Server by using the command ------ mvn package
5) Once the build is complted Sucessfully "term.war" will be created in the Project's"target"Folder. 
6) Copy the "term.war" generated in the project directory under the "target" Folder to Tomcat 8 "webapps" folder.
7) Start the Server and the application should be deployed onto the Server under the virtual directory "/term".
8) you can now view the application running on Tomcat 8 Server using the URL http://<IP_ADRESSS>:8080/term/