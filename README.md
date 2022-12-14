# <h1 align="center">TestQuest</h1>

## Description:

Servlet project for creating and completing test quests

<hr>

## Short view:

<img width="700" alt="quest gif" src="https://user-images.githubusercontent.com/96682553/204340032-0ddb0e71-fd0b-4750-b211-6085efddf972.gif">

<hr>

## Tech stacks has been used:
<br/>:white_check_mark: Servlets API
<br/>:white_check_mark: JSP, JSTL
<br/>:white_check_mark: HTML/CSS, JavaScript
<br/>:white_check_mark: Bootstrap
<br/>:white_check_mark: Tomcat 9
<br/>:white_check_mark: JUnit, Mockito
<br/>:white_check_mark: H2 database

## It's planned to add the following features soon:

- [X] add JaCoCo + supplement tests for Servlets and Repositories
- [ ] add error handler filter
- [ ] sign in/sign up capability, personal account of the registered user
- [ ] the ability to add own quest and get statistic for completing it other users in personal account: CRUD operation with it
- [ ] control panel for admin



<hr>

## Want to run this project? ##
Create image and spin container:

`docker build -t quest .  && docker run -p 8080:8080 quest`

After that,  server is available at http://localhost:8080/welcome.

<hr>
