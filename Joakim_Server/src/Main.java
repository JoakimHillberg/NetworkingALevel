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
        try {
            server = new ServerSocket(portNumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket. " + ie);
            System.exit(1);
        }
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

        // Wait for the data from the client and reply
        while (true) {
            try {
                // Read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println(msgFromClient);

                // Calculate value
                String symbol = getSymbol(msgFromClient);
                String[] numbers = msgFromClient.split(symbol);
                String response = "";
                if (!symbol.isEmpty() && canParse(numbers[0]) && canParse(numbers[1])) {
                    response = Integer.toString(calculate(symbol,Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1])));
                }

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

                } else if (!symbol.isEmpty()) {
                    pw.println(ans + "The answer becomes " + response);
                } else {
                    pw.println(ans + "That can not be calculated");
                }

            } catch (IOException ie) {
                System.out.println("I/O error " + ie);
            }
        }
    }

    public static boolean canParse(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getSymbol(String msg) {
        if (msg.contains("/")) {
            return "\\/";
        } else if (msg.contains("*")) {
            return "\\-";
        } else if (msg.contains("+")) {
            return "\\+";
        } else if (msg.contains("-")) {
            return "\\-";
        }
        return "";
    }

    public static int calculate(String symbol, int number, int number2) {
        return switch (symbol) {
            case "\\+" -> number + number2;
            case "\\-" -> number - number2;
            case "\\*" -> number * number2;
            case "\\/" -> number / number2;
            default -> 0;
        };
    }
}