# javaMiniProject

This is the readme file related to project created by 19DIT008 AKSHAT CHHAYA AND 19DIT074 DEEP THAKKAR
For this project to run perfectly please ensure that you follow all the steps.
The App is in debug relase and under development hence some features may be missing including logout functionality we have tryed our level best to ensure perfomance.

Backend Configuration:

for backend to able to run please ensure the system has nodejs and mongodb installed on it if not please follow installation instruction for Nodejs and mongodb
on https://nodejs.org/en/ and https://www.mongodb.com/try/download/community and install the same and afterwards continue with below given setup procedure.


first download and unzip the folder

step1: cd over to the folder named javaMiniProject the main folder for our project (i.e root directory for project) (not to AndroidApp)

step2: now run command npm install.

step3: now start mongod service (mentioned on mongodb website https://docs.mongodb.com/guides/server/install/).


note: keep mongod service either running or if you stop it please ensure to start it before you start server.js

step4: now run node databaseConfig.js

step5: now after console log of start node server.js press ctrl+c to stop the server and start server,js file instead, run command node server.js

step6:After console log of server started on port 3000 we are good to go.

App runup setup:

App is now all set to start working only single change is required for it to work correctly!

navigate to  AndroidApp-->main-->java-->akshatchhaya.example.javaminiproject-->api-->API classfile

in the class the very first field is String url in the url enter following http://<your_pc_ip_address>:3000

please ensure you change this variable else it won't be able to communicate to your pc server.

please ensure that you run app in emmulator if possible cause on mobile phone due to firewall restrictions and network diffrences our server may not be reachable
as it is a local server.


