# SCB Party Server
## Description 
This is a part of Join the Party Web Application of SCB10X test. SCB Party server is a server which manage API request, 
generate Authentication token, and response.
## Dependency and Development
- Spring boot
- Java 11
- Maven 3.8.4
- Database: JPA
- Authtication: JWTT
## Configuration
- Port `http://localhost:8080`
## Overview
### User
- Registeration
- LogIn : It will generate authendication token by JWT
### Party
- Create : user can create party with name and amount of member
- PartyList : get all party with detail 
- Join : user can join not full member
### Running
- Let check you already have mavan and java
`mvn -v` and `java -v`
- Run mavaen package
`mvn clean package`
- Run server
`mvn spring-boot:run`
