# ChatApplication

A Web based chat application which helps you connect with your friends and family members. Supports full-fledged client-server interactions, created using Java Spring Boot library and Vanilla JavaScript.

### Functionalities:

The application currently supports broadcast messages across the server. There exists a common server chat to which each client can send a message to. The plan is to also provide the functionality to send P2P messages to the current connected clients.

Since the application is web-based as long as the IP address is reachable any client throughout the world can connect and communicate with each other.

---

### Running the application locally:

The project comes with a runnable jar file encasing the tomcat server as well as front-end code. To run the application simply go the the `runnable_app ` directory and run the following command:

```
$ java -jar ChatApplication-1.0.0-SNAPSHOT.jar
```

This starts the server which can be accessed at `localhost:8080`. You can start interacting with it from different machines by replacing localhost with the IP address of the host machine.

---

### Project Structure:

This a maven project and can be built using simple maven commands, the front-end code is housed inside the `./src/main/resources` directory. To build the project simply clone the repository and run the following command in the root directory:

```
$ mvn clean install
```

Alternatively an IDE such as IntelliJ m
