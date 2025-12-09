import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ColorPickerWithSliders extends Application {

    private Label lblText = new Label("Welcome to Java");

    // Sliders for Red, Green, Blue, and Opacity
    private Slider slRed = new Slider(0, 255, 128);
    private Slider slGreen = new Slider(0, 255, 128);
    private Slider slBlue = new Slider(0, 255, 128);
    private Slider slOpacity = new Slider(0, 100, 100); // 0–100%

    // Labels to show current values
    private Label lblRedValue = new Label("128");
    private Label lblGreenValue = new Label("128");
    private Label lblBlueValue = new Label("128");
    private Label lblOpacityValue = new Label("100%");

    @Override
    public void start(Stage primaryStage) {
        // Configure the main text
        lblText.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        lblText.setAlignment(Pos.CENTER);

        // Configure sliders
        configureSlider(slRed, 0, 255, 1);
        configureSlider(slGreen, 0, 255,1);
        configureSlider(slBlue, 0, 255,1);
        configureSlider(slOpacity, 0, 100,1);

        slRed.setMajorTickUnit(64);
        slGreen.setMajorTickUnit(64);
        slBlue.setMajorTickUnit(64);
        slOpacity.setMajorTickUnit(25);

        slRed.setShowTickLabels(true);
        slGreen.setShowTickLabels(true);
        slBlue.setShowTickLabels(true);
        slOpacity.setShowTickLabels(true);

        slRed.setShowTickMarks(true);
        slGreen.setShowTickMarks(true);
        slBlue.setShowTickMarks(true);
        slOpacity.setShowTickMarks(true);

        // Grid for sliders and labels
        GridPane sliderPane = new GridPane();
        sliderPane.setPadding(new Insets(15));
        sliderPane.setHgap(10);
        sliderPane.setVgap(10);
        sliderPane.setAlignment(Pos.CENTER);

        sliderPane.add(new Label("Red:"), 0, 0);
        sliderPane.add(slRed, 1, 0);
        sliderPane.add(lblRedValue, 2, 0);

        sliderPane.add(new Label("Green:"), 0, 1);
        sliderPane.add(slGreen, 1, 1);
        sliderPane.add(lblGreenValue, 2, 1);

        sliderPane.add(new Label("Blue:"), 0, 2);
        sliderPane.add(slBlue, 1, 2);
        sliderPane.add(lblBlueValue, 2, 2);

        sliderPane.add(new Label("Opacity:"), 0, 3);
        sliderPane.add(slOpacity, 1, 3);
        sliderPane.add(lblOpacityValue, 2, 3);

        // Main layout
        BorderPane root = new BorderPane();
        root.setCenter(lblText);
        root.setBottom(sliderPane);
        BorderPane.setAlignment(lblText, Pos.CENTER);
        BorderPane.setMargin(sliderPane, new Insets(10));

        // Add listeners to update color whenever any slider changes
        ChangeListener<Number> colorListener = (observable, oldValue, newValue) -> updateTextColor();

        slRed.valueProperty().addListener(colorListener);
        slGreen.valueProperty().addListener(colorListener);
        slBlue.valueProperty().addListener(colorListener);
        slOpacity.valueProperty().addListener(colorListener);

        // Also update the value labels
        slRed.valueProperty().addListener((obs, oldVal, newVal) ->
                lblRedValue.setText(String.format("%.0f", newVal.doubleValue())));
        slGreen.valueProperty().addListener((obs, oldVal, newVal) ->
                lblGreenValue.setText(String.format("%.0f", newVal.doubleValue())));
        slBlue.valueProperty().addListener((obs, oldVal, newVal) ->
                lblBlueValue.setText(String.format("%.0f", newVal.doubleValue())));
        slOpacity.valueProperty().addListener((obs, oldVal, newVal) ->
                lblOpacityValue.setText(String.format("%.0f%%", newVal.doubleValue())));

        // Initial color update
        updateTextColor();

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setTitle("Color Picker with Sliders - Exercise 16.17");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configureSlider(Slider slider, double min, double max, double blockIncrement) {
        slider.setMin(min);
        slider.setMax(max);
        slider.setBlockIncrement(blockIncrement);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
    }

    private void updateTextColor() {
        int r = (int) slRed.getValue();
        int g = (int) slGreen.getValue();
        int b = (int) slBlue.getValue();
        double opacity = slOpacity.getValue() / 100.0; // convert percentage to 0.0–1.0

        Color color = Color.rgb(r, g, b, opacity);
        lblText.setTextFill(color);
    }

    public static void main(String[] args) {
        launch(args);
    }
}