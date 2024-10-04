package server;

/**
 * The ClientApp class allows user to select the protocol (TCP or UDP) and send requests.
 */
public class ServerApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java server.ServerApp <tcp-port-number> <udp-port-number>");
            return;
        }

        String protocol = args[0];
        int port = Integer.parseInt(args[1]);

        if (protocol.equalsIgnoreCase("tcp")) {
            TCPServer tcpServer = new TCPServer(port);
            tcpServer.start();
        } else if (protocol.equalsIgnoreCase("udp")) {
            UDPServer udpServer = new UDPServer(port);
            udpServer.start();
        } else {
            System.out.println("Invalid protocol. Please specify 'tcp' or 'udp'.");
        }
    }
}

