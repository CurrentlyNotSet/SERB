State Employment Relations Board (SERB) Version 2
====================

Project Tracking - [Trello](https://trello.com/b/duAAZOdj/serb)
---------------------

* ON HOLD

    Items listed here are on hold until feedback is gathered from SERB, most tickets are considered to be unable to be completed
  
* Questions for SERB

    Items listed here are awaiting feedback from SERB and their employees

* Questions for XLN

    Items listed here are awaiting feedback from XLN and the development team

* Extra Features

    Items listed here are features that could be developed at a later date.  Use this area to make notes of ideas as they are thought of.

* Backlog

    Items contained in this column are those that are still needed to be prioritized and developed for future development work
  
* Sprint Backlog

    Items in this column are planned to be completed within the current sprint or before the next release

* In Progress

    Items here are currently in development by the assigned developer  

* ON PREM Testing

    Items here are not able to be tested with the current AWS set up, will need to be tested on DEV on OIT servers

* Testing

    Items here are awaiting internal testing, based on the acceptence criteria.

* Deploy

    Items here are awaiting to be pushed to develop branch, but have passed testing
  
* On Dev

    Items here have been pushed to develop and are awaiting the next release

* Release X.X.X

    These columns will contain all items that are in each release, a new column will be created for each release

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

