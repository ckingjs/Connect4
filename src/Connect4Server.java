import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 * A server for Connect4
 *
 * @author Chris King, SER216 Staff
 * @version 1.0
 */
public class Connect4Server extends JFrame {

    // Text area for displaying contents
    private JTextArea jta = new JTextArea();
    private ArrayList<Connect4> games = new ArrayList<>();

    /**
     * Launching point for the Connect4 Server
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new Connect4Server();
    }

    /**
     * Constuctor for the Connect4 Server
     */
    public Connect4Server() {

        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("Connect4 Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        try {

            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("Connect4 Server started at " + new Date() + '\n');

            // Number a client
            int clientNo = 1;

            while (true) {

                // Listen for a new connection request
                Socket socket = serverSocket.accept();

                // Display the client number
                jta.append("Starting thread for client " + clientNo + " at " + new Date() + '\n');

                // Find the client's host name, and IP address
                InetAddress inetAddress = socket.getInetAddress();
                jta.append("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                jta.append("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");

                HandleAPlayer task;

                // Create a new game and thread for the connection
                if (clientNo % 2 == 1) {
                    Connect4 c4 = new Connect4();
                    games.add(c4);

                    task = new HandleAPlayer(socket, 'X', c4);
                }
                else {
                    task = new HandleAPlayer(socket, 'O', games.get(games.size()-1));
                }

                // Start the new thread
                new Thread(task).start();

                // Increment clientNo
                clientNo++;
            }
        }
        catch(IOException ex) {
            System.err.println(ex);
        }
    }

    // Inner class
    // Define the thread class for handling new connection
    class HandleAPlayer implements Runnable {

        private Socket socket; // A connected socket
        private Connect4 game;  //The game board
        private char player; //'X' or 'O'

        /** Construct a thread */
        public HandleAPlayer(Socket socket, char player, Connect4 c4) {
            this.socket = socket;
            this.game = c4;
            this.player = player;
        }

        /** Run a thread */
        public void run() {
            try {
                // Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
                ObjectOutputStream objOutputToClient = new ObjectOutputStream(socket.getOutputStream());

                outputToClient.writeChar(player);
                outputToClient.flush();

                // Continuously serve the client
                while (true) {

                    // Receive column from the client
                    int col = inputFromClient.readInt();

                    if (game.getPlayerByTurn() == player && col != -1) game.addPiece(col);

                    // Send board back to client
                    objOutputToClient.reset();
                    objOutputToClient.writeObject(game);
                    objOutputToClient.flush();

                    jta.append("Column received from client: " + col + '\n');
                }
            }
            catch(IOException e) {
                System.err.println(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}