package server;

import java.net.*;
import java.io.*;

/**
 * TCPServer handles client connections over TCP.
 */
public class TCPServer {
    private int port;
    private ServerLogger logger;
    private KeyValueStore keyValueStore;

    public TCPServer(int port) {
        this.port = port;
        this.logger = ServerLogger.getInstance();
        this.keyValueStore = new KeyValueStore();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.log("TCP Server started on port " + port);

            while (true) {
                // Accept a client connection
                Socket clientSocket = serverSocket.accept();
                logger.log("Accepted connection from " + clientSocket.getInetAddress());

                // Handle client in a separate method
                handleClient(clientSocket);

                // Close client socket after handling
                clientSocket.close();
                logger.log("Connection with client closed");
            }
        } catch (IOException e) {
            logger.logError("Server exception: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            while (true) {
                String request = in.readUTF();
                logger.log("Received: " + request);

                String response = processRequest(request, clientSocket.getInetAddress());
                out.writeUTF(response);
                logger.log("Sent: " + response);
            }
        } catch (EOFException e) {
            logger.log("Client disconnected");
        } catch (IOException e) {
            logger.logError("IO Exception: " + e.getMessage());
        }
    }

    private String processRequest(String request, InetAddress clientAddress) {
        String[] parts = request.trim().split("\\s+");
        if (parts.length < 1) {
            String response = "ERROR Malformed request";
            logger.logError(response + " from " + clientAddress);
            return response;
        }

        String command = parts[0].toUpperCase();
        String response;

        switch (command) {
            case "PUT":
                if (parts.length != 3) {
                    response = "ERROR PUT requires key and value";
                } else {
                    String key = parts[1];
                    String value = parts[2];
                    response = keyValueStore.put(key, value);
                }
                break;
            case "GET":
                if (parts.length != 2) {
                    response = "ERROR GET requires key";
                } else {
                    String key = parts[1];
                    response = keyValueStore.get(key);
                }
                break;
            case "DELETE":
                if (parts.length != 2) {
                    response = "ERROR DELETE requires key";
                } else {
                    String key = parts[1];
                    response = keyValueStore.delete(key);
                }
                break;
            case "SHOW":
                response = keyValueStore.show();
                break;
            default:
                response = "ERROR Unknown command";
                logger.logError("Unknown command: " + command + " from " + clientAddress);
                break;
        }
        return response;
    }
}
