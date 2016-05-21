State Employment Relations Board (SERB) Version 2
====================

Project Communication - [Slack](https://xlnsystems.slack.com/messages/general/)
---------------------

* serb

This channel contains all Trello and GitHub notifications, as well as all development team commnication

* serbapplication

This channel contains all actions and error reporting for the SERB system.  Anything here is auto generated from the "live" application

Project Requirements
---------------------
* SQL Server
* Java 1.8 or greater
* Maven 
* Netbeans

Database Setup
---------------------
* Production
 
Holds all production data that is in use and is not able to be overwritten by a user, can only be handled by development team.  This is backup on a automated weekly basis, but admin's can backup manually within application

* Training

Holds a form of a production database, this will be used when new employees are trying to learn the system 

* Development

This database will only be used for the development team to continue to develop new features and will hold test data, that is not accurate or proper.  This can be updated to hold production data to resolve bugs or test for errors.

