# CS 6650 Scalable Distributed System 
# Project 1: Key-Value Store Application 

This project implements a simple key-value store server and client application supporting both TCP and UDP protocols. The server maintains a key-value store, and clients can perform `PUT`, `GET`, and `DELETE` operations. The application demonstrates network communication, concurrency, and error handling in Java.

## Project Structure
```bash
src
├── Readme.md
├── Executive Summary.txt
├── client
│   ├── ClientApp.java
│   ├── ClientLogger.java
│   ├── TCPClient.java
│   └── UDPClient.java
└── server
    ├── KeyValue.java
    ├── ServerApp.java
    ├── ServerLogger.java
    ├── TCPHandler.java
    └── UDPHandler.java

## Requirements

- **Java Development Kit (JDK) 8 or higher**
  - Ensure `javac` and `java` commands are available in your terminal.
- **Operating System**
  - The application should work on any OS with Java support (e.g., macOS, Linux, Windows).
- **Terminal or Command Prompt**
  - Required to compile and run the application.


## Compilation Instructions

Open a terminal window and navigate to the root directory containing the `src` folder, e.g: my `src` pathname on Mac is: /Users/weiwenwu/Desktop/CS 6650/Project/src

**Compile the Server Code**
- On a new terminal, navigate to the project directory, e.g. /Users/weiwenwu/Desktop/CS 6650/Project/src
- compile the server code: javac server/*.java client/*.java
- After compilation, you should have a server directory containing .class files matching your Java files. 
	

## Running the Server and Client
### 1. **Run the Server**
***Run TCP Server
- Open a same or new terminal and navigate to the project directory: 
  
  	```bash
  	cd /path/to/your/project/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project/src
  	java server.ServerApp tcp 12345

- You should see output indicating that the TCP server has started: [YYYY-MM-DD HH:mm:ss.SSS] [Server] TCP Server started on port 12345
	- e.g. [2024-10-02 12:11:59.828] [Server] TCP Server started on port 12345

**Run UDP Server**
- Open new terminal and navigate to the project directory: 
  
  	```bash
  	cd /path/to/your/project/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project/src
	java server.ServerApp udp 12346
- You should see output indicating that the TCP server has started: [YYYY-MM-DD HH:mm:ss.SSS] [Server] UDP Server started on port 12346
	- e.g. [2024-10-02 12:15:55.745] [Server] UDP Server started on port 12346

### 3. **Run the Client**
**Run TCP Client
- Open new terminal and navigate to the project directory: 
  
  	```bash
  	cd /path/to/your/project/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project/src
	java client.ClientApp <hostname> <tcp-port-number> tcp
		- e.g:  java client.ClientApp localhost 12345 tcp

**Sample Output for TCP protocol**
Here I have pre-population and also performed 15 operations.

➜  src git:(main) ✗ java client.ClientApp localhost 12345 tcp
Connected to the server at localhost:12345
Pre-populating the key-value store with initial data...
Sent: PUT apple red
Received: OK
Sent: PUT banana yellow
Received: OK
Sent: PUT grape purple
Received: OK
Sent: PUT lemon yellow
Received: OK
Sent: PUT cherry red
Received: OK
Pre-population completed.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put neu sv
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put cmu pa
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put nyu nyc
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put ucsd sd
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put ucla la
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get usla
Server response: ERROR Key not found
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get cherry
Server response: OK red
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get neu
Server response: OK sv
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get grape
Server response: OK purple
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get nyu
Server response: OK nyc
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): show 
Server response: banana : yellow
apple : red
cherry : red
lemon : yellow
ucla : la
ucsd : sd
cmu : pa
grape : purple
nyu : nyc
neu : sv

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete nyu
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete cmu
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete neu
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete apple
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete lemon
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): show
Server response: banana : yellow
cherry : red
ucla : la
ucsd : sd
grape : purple

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): exit
Disconnected from the server.


**Run UDP Client
- Open a same or new terminal and navigate to the project directory: 
  
  	```bash
  	cd /path/to/your/project/src, e.g. /Users/weiwenwu/Desktop/CS 6650/Project/src
	java client.ClientApp <hostname> <tcp-port-number> tcp
		- e.g:  java client.ClientApp localhost 12346 udp

**Sample Output for TCP protocol**
Here I have pre-population and also performed 15 operations.

➜  src git:(main) ✗ java client.ClientApp localhost 12346 udp
Connected to the server at localhost:12346
Pre-populating the key-value store with initial data...
Sent: PUT apple red
Received: OK
Sent: PUT banana yellow
Received: OK
Sent: PUT grape purple
Received: OK
Sent: PUT lemon yellow
Received: OK
Sent: PUT cherry red
Received: OK
Pre-population completed.
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put neu sv
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put cmu pa
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put nyu nyc
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put ucsd sd
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): put ucla la
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get usla
Server response: ERROR Key not found
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get cherry
Server response: OK red
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get neu
Server response: OK sv
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get grape
Server response: OK purple
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): get nyu
Server response: OK nyc
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): show 
Server response: banana : yellow
apple : red
cherry : red
lemon : yellow
ucla : la
ucsd : sd
cmu : pa
grape : purple
nyu : nyc
neu : sv

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete nyu
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete cmu
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete neu
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete apple
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): delete lemon
Server response: OK
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): show
Server response: banana : yellow
cherry : red
ucla : la
ucsd : sd
grape : purple

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): exit
Disconnected from the server.


## Usage Instructions

After running the client, you can interact with the key-value store using the following commands:

- **PUT key value**
  - Inserts or updates a key-value pair in the store.
  - *Example:* `PUT apple red`

- **GET key**
  - Retrieves the value associated with a key.
  - *Example:* `GET apple`

- **DELETE key**
  - Removes the key-value pair from the store.
  - *Example:* `DELETE apple`

- **SHOW**
  - Displays all key-value pairs currently stored.
  - *Example:* `SHOW`

- **exit**
  - Exits the client application.
  - *Example:* `exit`

**Sample Interaction:**
Enter command (PUT key value | GET key | DELETE key | SHOW | exit): GET apple
Server response: OK red

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): PUT orange orange
Server response: OK

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): DELETE banana
Server response: OK

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): SHOW
Server response:
apple : red
grape : purple
lemon : yellow
cherry : red
orange : orange

Enter command (PUT key value | GET key | DELETE key | SHOW | exit): exit
Disconnected from the server.


## Logging

- **Client Logs:**
  - The client application logs events to `client.log` in the project directory.
  - Logs include timestamps and details of sent requests and received responses.

- **Server Logs:**
  - The server application outputs logs to the console (standard output and error).
  - Logs include timestamps and details of received requests and sent responses.


## Error Handling

- **Invalid Commands:**
  - The client will display an error message if an unknown or malformed command is entered.
  - *Example:*

    ```
    Unknown command. Please use PUT, GET, DELETE, SHOW, or exit.
    ```

- **Server Responses:**
  - The server sends error messages for invalid requests.
  - Error messages start with `ERROR` followed by a description.

- **Connection Issues:**
  - If the client cannot connect to the server, it will log an error and display a message.



