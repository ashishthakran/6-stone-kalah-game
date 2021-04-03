# Kalah Game
The application exposes *Java RESTful Web Service* that runs a game of 6-stone Kalah. 

The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah and also below in this document. Please
note that the Wikipedia article explains 3 and 4-stone Kalah; we would like your implementation to be 6-stone.

This web service should enable to let 2 human players to play the game, each in his own computer. There is no AI
required.

#### Kalah Rules
Each of the two players has **six pits** in front of him/her. To the right of the six pits, each player has a larger pit, his
Kalah or house.

At the start of the game, six stones are put in each pit.

The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in
each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last
stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other
player's turn.

When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the
other players' pit) and puts them in his own Kalah.

The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps
them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.

#### Application Modules

Application contains different modules to decouple of modules as much as possible so that change in one module doesn't affect too much on another module.

| Module Name | Description |
| --- | --- |
| kalah-core-domain | This module contains all the classes and service that are the core of the application like Game and Game Engine which defined the game rules. Core Domain will be the one that would contain all the validations and business rules and will be the common communication point among various components. |
| kalah-data-repository | This module contains all configuration and classes that will be responsible for data storage. |
| kalah-rest-api | This module contains all the REST APIs for the application |
| kalah-service | This module contains the configuration to initialize and boot the application using spring boot.|

#####Request Flow

User's Request ➡ Kalah REST API ➡ Kalah Core Domain ➡ Kalah Data Repository ➡ Storage (DB, In-Memory etc.)
## Install & Running
 
### Prerequisites
* JDK 1.8
* Maven 3.5.2

Maven and JDK 1.8 much be exported in user's PATH variable.

### Pull from git 
```
$ git clone https://github.com/ashishthakran/6-stone-kalah-game.git
$ cd 6-stone-kalah-game
```

### Build & run 

* Build
```
$ mvn clean package
```

* Run test
```
$ mvn test
```

* Run the application
```
$ cd libs
$ java -jar kalah-service-1.0.jar
```

### API documentation
Application contains Swagger UI Dashboard
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Application API

* Create & Initialize a new game
###### Request
```
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```

###### Response
```
HTTP Status - 201

{
  "id": "1",
  "uri": "http://localhost:8080/games/1"
}
```

* Make a move
###### Request
```
curl --header "Content-Type: application/json" --request PUT http://localhost:8080/games/1/pits/2
```

###### Response
```
HTTP Status - 200

{"id":"1234","url":"http://localhost:8080/games/1","status":{"1":"4","2":"4","3":"4","4":"4","5":"4","6":"4","7":"0","8":"4","
9":"4","10":"4","11":"4","12":"4","13":"4","14":"0"}}

```

#### API Error Response
```
{
  "message": "Unknown error occurred while processing the request."
}
```
