## Chat

[![Build Status](https://app.travis-ci.com/fortncom/job4j_chat.svg?branch=master)](https://app.travis-ci.com/fortncom/job4j_chat)

**The goal of this project is messenger for communication.**

You can create rooms with other users. The rooms can be designed for two users or for many. 

***

#### Dependencies

* Spring Boot 2
* Spring Security(JWT for authentication and authorization)
* Spring Data JPA with Hibernate + PostgreSQL
* Java 14
* Maven
* Liquibase
* Travis CI
* Checkstyle

#### Provides simple REST API endpoints:

##### Message:

*GET: /message?name*

*GET: /message/*

*GET: /message/{id}*

*POST: /message/ + body with Message*

*PUT: /message/ + body with Message*

*DELETE: /message/{id}*

*PATCH: /message/patch + body with MessageDTO*

##### Person:

*GET: /users/*

*GET: /users/{id}*

*POST: /users/sign-up + body with Person*

*PUT: /users/ + body with Person*

*DELETE: /users/{id}*

*PATCH: /users/patch + body with Person*

##### Role:

*GET: /role/*

*GET: /role/{id}*

*POST: /role/ + body with Role*

*PUT: /role/ + body with Role*

*DELETE: /role/{id}*

##### Room:

*GET: /room/messages?name*

*GET: /room/*

*GET: /room/{id}*

*POST: /room/ + body with Room*

*PUT: /room/ + body with Room*

*DELETE: /room/{id}*

*PATCH: /room/patch + body with RoomDTO*
