Javaimport javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Exercise14_01 extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(30));
        pane.setHgap(30);
        pane.setVgap(30);

        // Load images using getResource()
        Image germany = new Image(getClass().getResource("/M5/M5A1/Germany.gif").toExternalForm());
        Image china    = new Image(getClass().getResource("/M5/M5A1/China.gif").toExternalForm());
        Image france   = new Image(getClass().getResource("/M5/M5A1/France.gif").toExternalForm());
        Image usa      = new Image(getClass().getResource("/M5/M5A1/US.gif").toExternalForm());

        // Create ImageViews
        ImageView ivGermany = new ImageView(germany);
        ImageView ivChina   = new ImageView(china);
        ImageView ivFrance  = new ImageView(france);
        ImageView ivUSA     = new ImageView(usa);

        // Preserve aspect ratio
        ivGermany.setPreserveRatio(true);
        ivChina.setPreserveRatio(true);
        ivFrance.setPreserveRatio(true);
        ivUSA.setPreserveRatio(true);

        //set a max size so flags don't get too big
        ivGermany.setFitWidth(200);
        ivChina.setFitWidth(200);
        ivFrance.setFitWidth(200);
        ivUSA.setFitWidth(200);

        // Add to grid
        pane.add(ivGermany, 0, 0);
        pane.add(ivChina,   1, 0);
        pane.add(ivFrance,  0, 1);
        pane.add(ivUSA,     1, 1);

        // Align to corners
        GridPane.setHalignment(ivGermany, HPos.LEFT);
        GridPane.setHalignment(ivChina,   HPos.RIGHT);
        GridPane.setHalignment(ivFrance,  HPos.LEFT);
        GridPane.setHalignment(ivUSA,     HPos.RIGHT);

        GridPane.setValignment(ivGermany, VPos.TOP);
        GridPane.setValignment(ivChina,   VPos.TOP);
        GridPane.setValignment(ivFrance,  VPos.BOTTOM);
        GridPane.setValignment(ivUSA,     VPos.BOTTOM);

        Scene scene = new Scene(pane, 700, 500);
        primaryStage.setTitle("Exercise14_01");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}