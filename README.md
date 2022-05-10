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

***

### Запуск проекта через docker-compose:

*1. Собираем проект в jar:*
````
mvn install -Dmaven.test.skip=true
````
*2. Создаём контейнер с бд:*
````
docker run -d \
--name postgres \
-e POSTGRES_PASSWORD=password \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-p 5432:5432 \
-v postgres_db:/var/lib/postgresql/data \
postgres
````
*3. Заходим в контейнер:*
````
docker exec -it postgres bash
````
*4. Открываем psql от юзера postgres:*
````
psql -U postgres
````
*5. Создаём бд:*
````
CREATE DATABASE chat;
````
*6. Выходим из psql и контейнера:*
````
exit
````
*7. Собираем образ с проектом на основе докер файла:*
````
docker build -t chat .
````
*8. Запускаем проект через docker-compose:*
````
docker-compose up
````
Смена владельца файла на Linux: ````sudo chown -R $USER <path-to-folder>````

***

### Запуск проекта в Kubernetes:

*1. Запускаем кластер kubernetes:*
````
minikube start
````

*2. Привязываем файл postgresdb-secret.yml(для внешнего хранения паролей) к kubernetes:*
````
kubectl apply -f postgresdb-secret.yml
````

*3. Привязываем файл postgresdb-configmap.yml(для конфигурации пода с бд) к kubernetes:*
````
kubectl apply -f postgresdb-configmap.yml
````
*4. Привязываем файл postgresdb-deployment.yml(конфигурационный файл развертывания для бд) к kubernetes:*
````
kubectl apply -f postgresdb-deployment.yml
````

*5. Привязываем файл chat-deployment.yml(конфигурационный файл развертывания для сервиса chat) к kubernetes:*
````
kubectl apply -f chat-deployment.yml
````

Получение url сервиса для обращения из вне: ````minikube service --url <name-service>````
