package server;

import java.net.*;
import java.io.*;

/**
 * UDPServer handles client requests over UDP.
 */
public class UDPServer {
    private int port;
    private ServerLogger logger;
    private KeyValueStore keyValueStore;

    public UDPServer(int port) {
        this.port = port;
        this.logger = ServerLogger.getInstance();
        this.keyValueStore = new KeyValueStore();
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            logger.log("UDP Server started on port " + port);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);

                // Handle client request
                new Thread(() -> handleRequest(socket, requestPacket)).start();
            }
        } catch (IOException e) {
            logger.logError("Server exception: " + e.getMessage());
        }
    }

    private void handleRequest(DatagramSocket socket, DatagramPacket requestPacket) {
        String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
        InetAddress clientAddress = requestPacket.getAddress();
        int clientPort = requestPacket.getPort();

        logger.log("Received: \"" + request + "\" from " + clientAddress + ":" + clientPort);

        String response = processRequest(request, clientAddress, clientPort);

        byte[] responseData = response.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(
                responseData, responseData.length, clientAddress, clientPort);
        try {
            socket.send(responsePacket);
            logger.log("Sent: \"" + response + "\" to " + clientAddress + ":" + clientPort);
        } catch (IOException e) {
            logger.logError("Failed to send response to " + clientAddress + ":" + clientPort + " - " + e.getMessage());
        }
    }

    private String processRequest(String request, InetAddress clientAddress, int clientPort) {
        String[] parts = request.trim().split("\\s+");
        if (parts.length < 1) {
            String response = "ERROR Malformed request";
            logger.logError(response + " from " + clientAddress + ":" + clientPort);
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
                logger.logError("Unknown command: " + command + " from " + clientAddress + ":" + clientPort);
                break;
        }
        return response;
    }
}
