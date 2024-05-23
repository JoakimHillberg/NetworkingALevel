import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        // Default port number we are going to use
        int portNumber = 1234;

        // Create server side socket
        try {
            server = new ServerSocket(portNumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket. " + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created " + server);

        // Wait for the data from the client and reply
        while (true) {
            try {
                // Listens for a connection to be made to
                // this socket and accepts it. The method blocks
                // a connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();

                // Calculate value
                if (msgFromClient.contains("+") || msgFromClient.contains()) {

                }
            } catch (IOException ie) {
                System.out.println("I/O error " + ie);
            }
        }
    }

    public String getSymbol(String msg) {
        if (msg.contains("+")) {
            return "+";
        } else if (msg.contains("-")) {
            return "-";
        } else if (msg.contains("*")) {
            
        }
    }
}