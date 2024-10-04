package client;

/**
 * The ClientApp class allows user to select the protocol (TCP or UDP) and send requests to the server.
 */
public class ClientApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please try again: java client.ClientApp <hostname> <port> <protocol>");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String protocol = args[2].toLowerCase();

        if (protocol.equalsIgnoreCase("tcp")) {
            TCPClient tcpClient = new TCPClient(hostname, port); // if choose tcp protocol, create tcp object
            tcpClient.start();
        } else if (protocol.equalsIgnoreCase("udp")) {
            UDPClient udpClient = new UDPClient(hostname, port); // if choose tcp protocol, create udp object
            udpClient.start();
        } else {
            System.out.println("Invalid protocol. Please specify 'tcp' or 'udp'.");
        }
    }
}
