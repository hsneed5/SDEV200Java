import javafx
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Exercise15_07 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a circle with radius 100
        Circle circle = new Circle(100);
        circle.setFill(Color.WHITE);        // initial color when mouse is not pressed
        circle.setStroke(Color.BLACK);      // border of the circle
        circle.setStrokeWidth(5);

        // Create a StackPane to center the circle
        StackPane pane = new StackPane();
        pane.getChildren().add(circle);

        // when mouse is PRESSED circle becomes BLACK
        circle.setOnMousePressed(e -> circle.setFill(Color.BLACK));

        // Event handler: when mouse is RELEASED → circle becomes WHITE
        circle.setOnMouseReleased(e -> circle.setFill(Color.WHITE));

        //  also change color if mouse is pressed anywhere in the window
        pane.setOnMousePressed(e -> circle.setFill(Color.BLACK));
        pane.setOnMouseReleased(e -> circle.setFill(Color.WHITE));

        // Set up thestage
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Exercise15_07");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}