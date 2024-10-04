package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * UDPClient
 * To compile: javac client/UDPClient.java client/ClientLogger.java
 * To run: java client.UDPClient <ServerHost> <Port Number>
 */
public class UDPClient {
    private static final int TIMEOUT_MS = 5000; // 5 seconds
    private String hostname;
    private int port;
    private InetAddress serverAddress;
    private DatagramSocket socket;
    private ClientLogger logger;

    public UDPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.logger = ClientLogger.getInstance();
    }

    public void start() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT_MS); // Set the socket timeout
            serverAddress = InetAddress.getByName(hostname);

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

            System.out.println("Disconnected from the server.");

        } catch (UnknownHostException e) {
            logger.logError("Unknown host: " + e.getMessage());
            e.printStackTrace();
        } catch (SocketException e) {
            logger.logError("Socket error: " + e.getMessage());
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
            byte[] requestData = request.getBytes();
            DatagramPacket requestPacket = new DatagramPacket(
                    requestData, requestData.length, serverAddress, port);
            socket.send(requestPacket);
            logger.log("Sent: \"" + request + "\"");

            // Prepare buffer for the response
            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

            // Receive the response
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            logger.log("Received: \"" + response + "\"");
            return response;
        } catch (SocketTimeoutException e) {
            logger.logError("Timeout waiting for response to: \"" + request + "\"");
            return "ERROR: Timeout waiting for response.";
        } catch (IOException e) {
            logger.logError("IO Exception: " + e.getMessage());
            return "ERROR: Communication error.";
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java client.UDPClient <ServerHost> <Port Number>");
            System.exit(1);
        }
        String serverHostname = args[0];
        int serverPort = Integer.parseInt(args[1]);

        UDPClient udpClient = new UDPClient(serverHostname, serverPort);
        udpClient.start();
    }
}
