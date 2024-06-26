import com.sun.jdi.connect.Connector;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

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
                System.out.println("Message received from client: " + msgFromClient);

                // Calculate value
                double response = 0;
                if (getNumbers(msgFromClient) != null) {
                    String symbol = getNumbers(msgFromClient)[0];
                    double number1 = Double.parseDouble(getNumbers(msgFromClient)[1]);
                    double number2 = Double.parseDouble(getNumbers(msgFromClient)[2]);
                    response = calculate(symbol,number1,number2);
                }

                // Send response to client
                OutputStream clientOut = client.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut,true);
                if (msgFromClient.equalsIgnoreCase("bye")) {
                    pw.println("Message from server: Goodbye");
                    server.close();
                    client.close();
                    pw.close();
                    br.close();
                    break;

                } else if (getNumbers(msgFromClient) != null) {
                    pw.println("Message from server: The answer becomes " + response);
                } else {
                    pw.println("Message from server: That can not be calculated");
                }

            } catch (IOException ie) {
                System.out.println("I/O error " + ie);
            }
        }
    }

    // Calculate the result of the problem
    public static double calculate(String symbol, double number, double number2) {
        return switch (symbol) {
            case "\\+" -> number + number2;
            case "\\-" -> number - number2;
            case "\\*" -> number * number2;
            case "\\/" -> number / number2;
            default -> 0;
        };
    }

    // Get the two numbers that are used in the problem
    public static String[] getNumbers(String msg) {
        String[] numbers = null;
        String number1 = null;
        String number2 = null;
        String symbol = null;
        Character[] symbols = {'+','-','*','/'};
        boolean validMessage = false;

        // Get the numbers and symbol if the message is a valid math problem
        if (Character.isDigit(msg.charAt(0)) || (Character.isDigit(msg.charAt(1)) && msg.charAt(0) == '-')) {
            for (int i = 0; i < msg.length(); i++) {
                if (List.of(symbols).contains(msg.charAt(i))) {
                    if (checkStringNumber(msg.substring(0,i)) && checkStringNumber(msg.substring(i+1))) {
                        number1 = msg.substring(0,i);
                        number2 = msg.substring(i+1);
                        symbol = "\\" + msg.charAt(i);
                        validMessage = true;
                    }
                }
            }
        }

        if (validMessage) {
            numbers = new String[]{symbol,number1,number2};
        }

        return numbers;
    }

    // Check if the String is a valid number
    public static boolean checkStringNumber(String number) {
        boolean dotAllowed = true;
        int validChars = 1;
        for (int i = 1; i < number.length(); i++) {
            if (dotAllowed && number.charAt(i - 1) != '-' && number.charAt(i) == '.') {
                validChars++;
                dotAllowed = false;
            } else if (Character.isDigit(number.charAt(i))) {
                validChars++;
            }
        }

        return validChars == number.length();
    }
}