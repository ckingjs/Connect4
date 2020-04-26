import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * GUI for players to use the Connect4 game
 *
 * @author Chris King
 * @version 1.0
 */
public class Connect4Client extends Application {

    // IO streams
    Socket socket;
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private ObjectInputStream objFromServer;
    private char player;
    private Connect4 c4;

    /**
     * Starts the GUI
     *
     * @param primaryStage JavaFX Stage object
     */
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        Group group = new Group();
        ArrayList<Rectangle> grid;
        TextField text = new TextField();
        text.setLayoutY(620);
        text.setPrefWidth(45);
        Button b = new Button("Submit");
        Button b2 = new Button("Update");
        b.setLayoutY(620);
        b.setLayoutX(50);
        b2.setLayoutY(620);
        b2.setLayoutX(120);

        grid = createBoard();
        group.getChildren().addAll(grid);
        group.getChildren().add(text);
        group.getChildren().add(b);
        group.getChildren().add(b2);

        try {
            // Create a socket to connect to the server
            socket = new Socket("localhost", 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream( socket.getInputStream() );
            objFromServer = new ObjectInputStream( socket.getInputStream() );

            // Create an output stream to send data to the server
            toServer =  new DataOutputStream( socket.getOutputStream() );
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //Finding out which player we are
        try {
            player = fromServer.readChar();

            // Display to the text area
            Text t = new Text("You are player: " + (player == 'X' ? "Red" : "Black" ));
            t.setY(620);
            group.getChildren().add(t);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        b2.setOnAction(e -> {
            try {
                // Send the radius to the server
                toServer.writeInt(-1);
                toServer.flush();

                c4 = null;
                c4 = (Connect4) objFromServer.readObject();

                if (c4 != null) {
                    group.getChildren().clear();
                    group.getChildren().addAll(grid);
                    group.getChildren().add(text);
                    group.getChildren().add(b);
                    group.getChildren().add(b2);
                    try {
                        group.getChildren().addAll(fillBoard(c4));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }

                    if (c4.getWinner() != 0) {
                        char winner = c4.getWinner();
                        String s = winner == '?' ? "Game over, no winner!" : winner == 'X' ? "Game over, RED wins!" : "Game over, BLACK wins!";

                        Text t = new Text(s);
                        t.setY(620);
                        group.getChildren().add(t);

                        group.getChildren().remove(b);
                        group.getChildren().remove(text);
                    }
                }
                else {
                    System.out.println("Not your turn!");
                }

            }
            catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex);
            }
        });

        b.setOnAction(e -> {
            try {
                // Send the radius to the server
                toServer.writeInt(Integer.parseInt(text.getText()));
                toServer.flush();

                c4 = null;
                c4 = (Connect4) objFromServer.readObject();

                if (c4 != null) {
                    group.getChildren().clear();
                    group.getChildren().addAll(grid);
                    group.getChildren().add(text);
                    group.getChildren().add(b);
                    group.getChildren().add(b2);
                    try {
                        group.getChildren().addAll(fillBoard(c4));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }

                    if (c4.getWinner() != 0) {
                        char winner = c4.getWinner();
                        String s = winner == '?' ? "Game over, no winner!" : winner == 'X' ? "Game over, RED wins!" : "Game over, BLACK wins!";

                        Text t = new Text(s);
                        t.setY(620);
                        group.getChildren().add(t);

                        group.getChildren().remove(b);
                        group.getChildren().remove(text);
                    }
                }
                else {
                    System.out.println("Not your turn!");
                }

            }
            catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex);
            }
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(new Pane(group), 700, 700);
        primaryStage.setTitle("Connect4GUI"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage

        primaryStage.show(); // Display the stage
    }

    private ArrayList<Rectangle> createBoard() {
        ArrayList<Rectangle> recs = new ArrayList<>();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Rectangle r = new Rectangle(col * 100.0d, row * 100.0d, 100.0d, 100.0d);
                r.setStroke(Color.BLACK);
                r.setFill(Color.WHITE);

                recs.add(r);
            }
        }

        return recs;
    }

    private ArrayList<Circle> fillBoard(Connect4 c4) throws Exception {
        ArrayList<Circle> circs = new ArrayList<>();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                char piece = c4.getPieceAtPosition(row+1, col+1);

                if (piece != ' ') {
                    Circle c = new Circle();
                    c.setCenterX(50 + col * 100);
                    c.setCenterY(550 - 100 * row);
                    c.setRadius(49);

                    if (piece == 'X') c.setFill(Color.RED);
                    else c.setFill(Color.BLACK);

                    circs.add(c);
                }
            }
        }

        return circs;
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

