import com.sun.jdi.connect.Connector;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket client;

        // Default port number we are going to use
        int portNumber = 1234;

        // Create server side socket
        server = new ServerSocket(portNumber);
        System.out.println("ServerSocket is created " + server);

        // Listens for a connection to be made to
        // this socket and accepts it. The method blocks
        // a connection is made
        System.out.println("Waiting for connect request...");
        client = server.accept();

        System.out.println("Connect request is accepted...");
        String clientHost = client.getInetAddress().getHostAddress();
        int clientPort = client.getPort();
        System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

        while (true) {
            // Read data from the client
            InputStream clientIn = client.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
            String msgFromClient = br.readLine();
            System.out.println(msgFromClient);

            // Calculate value


            // Send response to client
            OutputStream clientOut = client.getOutputStream();
            PrintWriter pw = new PrintWriter(clientOut,true);
            String ans = "Message from server: ";
            if (msgFromClient.equalsIgnoreCase("bye")) {
                pw.println(ans + "Goodbye");
                server.close();
                client.close();
                pw.close();
                br.close();
                break;
            } else {
                pw.println(ans + "That can not be calculated");
            }
        }
    }

}