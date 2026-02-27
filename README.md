# Client-Server-Reservation
A multi-threaded client-server reservation system built with Java, Quarkus, TCP Sockets, and PostgreSQL.

Technologies Used
* **Backend Framework:** Quarkus 
* **Communication:** TCP Sockets & Multithreading 
* **Database:** PostgreSQL 
* **ORM:** Hibernate with Panache (JPA)

##Features
When a client connects, the server generates a unique Client ID. The client can then use the following commands via the console:
* `LIST` - View all currently reserved slots 
* `RESERVE [time]` - Create a new reservation (prevents double-booking) 
* `MY` - View your own reservations 
* `CANCEL [time]` - Cancel an existing reservation 
* `EXIT` - Disconnect from the server

  
## How to Run the Application

### 1. Prerequisites
* Java 17 or newer installed.
* PostgreSQL database running and configured in your `application.properties`.
* Maven installed.

### 2. Start the Server
The Socket Server is configured to run automatically on port **8081** when the Quarkus application starts. 
Navigate to the root directory of the project and run:
```bash
./mvnw compile quarkus:dev
