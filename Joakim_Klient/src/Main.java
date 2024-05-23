import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        Socket client = null;

        // Port number we are going to use
        int portNumber = 1234;

        String msg = "";

        try {
            // Create a client socket
            client = new Socket(InetAddress.getLocalHost(), portNumber);
            System.out.println("Client socket is created " + client);

            // Create an output stream of the client socket
            OutputStream clientOut = client.getOutputStream();
            PrintWriter pw = new PrintWriter(clientOut,true);

            // Create an input stream of the client socket
            InputStream clientIn = client.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));

            // Create BufferedReader for a standard input
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Enter a number to calculate(use +,-,* or /)");

                // Read data from standard input device and write it
                // to the output stream from the client socket.
                msg = stdIn.readLine().trim();
                pw.println(msg);

                // Read data from the input stream of the client socket.
                System.out.println(br.readLine());

                if (msg.equalsIgnoreCase("bye")) {
                    pw.close();
                    br.close();
                    client.close();
                    break;
                }
            }
        } catch (IOException ie) {
            System.out.println("I/O error " + ie);
        }
    }
}