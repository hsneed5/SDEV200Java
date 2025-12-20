import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchUpdateDemo extends Application {

    private String jdbcUrl = "";
    private String username = "";
    private String password = "";

    private Label lblStatus = new Label("Not connected to database.");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Top: Title and Connect button
        HBox topBox = new HBox(10);
        topBox.setPadding(new Insets(15));
        topBox.setAlignment(Pos.CENTER);
        Label title = new Label("Batch Update Performance Test");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button btConnect = new Button("Connect to Database");
        btConnect.setOnAction(e -> showConnectionDialog(primaryStage));
        topBox.getChildren().addAll(title, btConnect);

        // Center: Buttons for execution
        GridPane centerPane = new GridPane();
        centerPane.setHgap(20);
        centerPane.setVgap(20);
        centerPane.setPadding(new Insets(20));
        centerPane.setAlignment(Pos.CENTER);

        Button btWithoutBatch = new Button("Execute Without Batch");
        Button btWithBatch = new Button("Execute With Batch");

        btWithoutBatch.setPrefWidth(200);
        btWithBatch.setPrefWidth(200);

        btWithoutBatch.setOnAction(e -> executeInsert(false));
        btWithBatch.setOnAction(e -> executeInsert(true));

        centerPane.add(btWithoutBatch, 0, 0);
        centerPane.add(btWithBatch, 0, 1);

        // Bottom: Status
        lblStatus.setStyle("-fx-font-size: 14px;");
        VBox bottomBox = new VBox(lblStatus);
        bottomBox.setPadding(new Insets(15));
        bottomBox.setAlignment(Pos.CENTER);

        root.setTop(topBox);
        root.setCenter(centerPane);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Batch Update Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showConnectionDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Connect to Database");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        TextField tfDriver = new TextField("com.mysql.cj.jdbc.Driver"); // Common for MySQL
        TextField tfUrl = new TextField("jdbc:mysql://localhost:3306/your_db_name");
        TextField tfUsername = new TextField("root");
        PasswordField tfPassword = new PasswordField();

        pane.add(new Label("JDBC Driver:"), 0, 0);
        pane.add(tfDriver, 1, 0);
        pane.add(new Label("Database URL:"), 0, 1);
        pane.add(tfUrl, 1, 1);
        pane.add(new Label("Username:"), 0, 2);
        pane.add(tfUsername, 1, 2);
        pane.add(new Label("Password:"), 0, 3);
        pane.add(tfPassword, 1, 3);

        Button btConnect = new Button("Connect");
        HBox buttonBox = new HBox(btConnect);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        pane.add(buttonBox, 1, 4);

        btConnect.setOnAction(e -> {
            String driver = tfDriver.getText().trim();
            jdbcUrl = tfUrl.getText().trim();
            username = tfUsername.getText().trim();
            password = tfPassword.getText();

            try {
                Class.forName(driver); 
                // Test connection
                try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
                    lblStatus.setText("Connected successfully. Ready to execute.");
                    dialog.close();
                }
            } catch (Exception ex) {
                lblStatus.setText("Connection failed: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Connection failed: " + ex.getMessage());
                alert.show();
            }
        });

        Scene dialogScene = new Scene(pane, 500, 300);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private void executeInsert(boolean useBatch) {
        if (jdbcUrl.isEmpty()) {
            lblStatus.setText("Please connect to database first.");
            return;
        }

        String sql = "INSERT INTO Temp (num1, num2, num3) VALUES (?, ?, ?)";

        long startTime = System.currentTimeMillis();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (useBatch) {
                conn.setAutoCommit(false); // Important for batch
            }

            for (int i = 0; i < 1000; i++) {
                double n1 = Math.random() * 1000;
                double n2 = Math.random() * 1000;
                double n3 = Math.random() * 1000;

                pstmt.setDouble(1, n1);
                pstmt.setDouble(2, n2);
                pstmt.setDouble(3, n3);

                if (useBatch) {
                    pstmt.addBatch();
                    if ((i + 1) % 100 == 0) { // Execute every 100 statements
                        pstmt.executeBatch();
                    }
                } else {
                    pstmt.executeUpdate();
                }
            }

            if (useBatch) {
                pstmt.executeBatch(); // Execute remaining
                conn.commit();
                conn.setAutoCommit(true);
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            lblStatus.setText(String.format("%s batch: Inserted 1000 records in %d ms.",
                    useBatch ? "With" : "Without", duration));

        } catch (SQLException ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}