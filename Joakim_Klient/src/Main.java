import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        Socket client = null;

        // Port number we are going to use
        int portNumber = 1234;

        while (true) {
            try {
                String msg = "";

                // Create a client socket
                client = new Socket(InetAddress.getLocalHost(), portNumber);
                System.out.println("Client socket is created " + client);

                // Create an output stream of the client socket
                OutputStream clientOut = client.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut,true);

                // Create BufferedReader for a standard input
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Enter a number to calculate(use +,-,* or /");

                // Read data from standard input device and write it
                // to the output stream from the client socket.
                msg = stdIn.readLine().trim();
                p
            } catch (IOException ie) {
                System.out.println("I/O error " + ie);
            }
        }
    }
}