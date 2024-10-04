package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCPClient class allows the user to interact with the key-value store server over TCP.
 */
public class TCPClient {
    private String hostname;
    private int port;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ClientLogger logger;

    public TCPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.logger = ClientLogger.getInstance();
    }

    public void start() {
        try {
            // Establish connection to the server
            socket = new Socket(hostname, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            System.out.println("Connected to the server at " + hostname + ":" + port);

            // Pre-populate the key-value store
            prePopulate();

            // User input loop
            try (Scanner scanner = new Scanner(System.in)) {
                boolean running = true;
                while (running) {
                    System.out.print("Enter command (PUT key value | GET key | DELETE key | SHOW | exit): ");
                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                        running = false;
                        break;
                    }

                    // Process the input
                    String[] tokens = input.split("\\s+");
                    if (tokens.length < 1) {
                        System.out.println("Invalid command. Please try again.");
                        continue;
                    }

                    String command = tokens[0].toUpperCase();
                    String request = "";

                    switch (command) {
                        case "PUT":
                            if(tokens.length != 3){
                                System.out.println("PUT command requires a key and a value.");
                                continue;
                            }
                            String putKey = tokens[1];
                            String putValue = tokens[2];
                            request = "PUT " + putKey + " " + putValue;
                            break;
                        case "GET":
                            if(tokens.length != 2){
                                System.out.println("GET command requires a key.");
                                continue;
                            }
                            String getKey = tokens[1];
                            request = "GET " + getKey;
                            break;
                        case "DELETE":
                            if(tokens.length != 2){
                                System.out.println("DELETE command requires a key.");
                                continue;
                            }
                            String delKey = tokens[1];
                            request = "DELETE " + delKey;
                            break;
                        case "SHOW":
                            if (tokens.length != 1) {
                                System.out.println("SHOW command does not require additional arguments.");
                                continue;
                            }
                            request = "SHOW";
                            break;
                        default:
                            System.out.println("Unknown command. Please use PUT, GET, or DELETE.");
                            continue;
                    }

                    // Send request and receive response
                    String response = sendRequest(request);
                    System.out.println("Server response: " + response);
                }
            }

            // Close resources
            socket.close();
            out.close();
            in.close();

            System.out.println("Disconnected from the server.");

        } catch (IOException e) {
            logger.logError("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void prePopulate() {
        System.out.println("Pre-populating the key-value store with initial data...");
        String[] initialData = {
                "PUT apple red",
                "PUT banana yellow",
                "PUT grape purple",
                "PUT lemon yellow",
                "PUT cherry red"
        };

        for (String request : initialData) {
            String response = sendRequest(request);
            System.out.println("Sent: " + request);
            System.out.println("Received: " + response);
        }

        System.out.println("Pre-population completed.");
    }

    private String sendRequest(String request) {
        try {
            logger.log("Sent: " + request);
            out.writeUTF(request);
            out.flush();

            String response = in.readUTF();
            logger.log("Received: " + response);
            return response;
        } catch (IOException e) {
            logger.logError("Communication error: " + e.getMessage());
            return "ERROR Communication error.";
        }
    }
}
