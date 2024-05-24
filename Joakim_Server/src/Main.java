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
            checkForNumber(msgFromClient);

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

    public static boolean checkForNumber(String msg) {
        String symbols = "+-*/";
        String number1;
        String number2;
        boolean invalidMsg = msg.charAt(0) != '-' || !Character.isDigit(msg.charAt(0));

        int start = 0;
        if (msg.charAt(0) == '-') {
            start++;
        }

        if (!Character.isDigit(msg.charAt(start))) {
            invalidMsg = true;
        }

        boolean dotAllowed = true;
        for (int i = start + 1; i < msg.length(); i++) {
            if (dotAllowed) {
                if (!Character.isDigit(msg.charAt(i)) || msg.charAt(i) != '.') {
                    invalidMsg = true;
                    if (symbols.contains(String.valueOf(msg.charAt(i)))) {
                        number1 = msg.substring(0,i);
                        System.out.println(number1);
                    }
                } else if (msg.charAt(i) == '.') {
                    dotAllowed = false;
                }
            } else {
                if (!Character.isDigit(msg.charAt(i))) {

                }
            }
        }

        double[] numbers = new double[2];
        return invalidMsg;
    }
}