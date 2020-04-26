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
public class Connect4GUI extends Application {

    /**
     * Starts the GUI
     *
     * @param primaryStage JavaFX Stage object
     */
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        Connect4 c4 = new Connect4();
        Group group = new Group();
        ArrayList<Rectangle> grid;
        TextField text = new TextField();
        text.setLayoutY(620);
        text.setPrefWidth(45);
        Button b = new Button("Submit");
        b.setLayoutY(620);
        b.setLayoutX(50);

        grid = createBoard();
        group.getChildren().addAll(grid);
        group.getChildren().add(text);
        group.getChildren().add(b);

        b.setOnAction(e -> {
            try {
                c4.addPiece(Integer.parseInt(text.getText()));
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            group.getChildren().clear();
            group.getChildren().addAll(grid);
            group.getChildren().add(text);
            group.getChildren().add(b);
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
