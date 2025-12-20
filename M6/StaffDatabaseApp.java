import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class StaffDatabaseApp extends Application {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name"; // Replace with your DB name
    private static final String DB_USER = "root";       // Replace with your username
    private static final String DB_PASSWORD = "your_password"; // Replace with your password

    private TextField tfId = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMi = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfTelephone = new TextField();
    private TextField tfEmail = new TextField();

    private Button btView = new Button("View");
    private Button btInsert = new Button("Insert");
    private Button btUpdate = new Button("Update");

    private Label lblStatus = new Label();

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Row 0: ID
        gridPane.add(new Label("ID"), 0, 0);
        gridPane.add(tfId, 1, 0);
        tfId.setPromptText("e.g., 123456789");

        // Row 1: Last Name
        gridPane.add(new Label("Last Name"), 0, 1);
        gridPane.add(tfLastName, 1, 1);

        // Row 2: First Name
        gridPane.add(new Label("First Name"), 0, 2);
        gridPane.add(tfFirstName, 1, 2);

        // Row 3: MI
        gridPane.add(new Label("MI"), 0, 3);
        gridPane.add(tfMi, 1, 3);
        tfMi.setPrefColumnCount(1);

        // Row 4: Address
        gridPane.add(new Label("Address"), 0, 4);
        gridPane.add(tfAddress, 1, 4);

        // Row 5: City
        gridPane.add(new Label("City"), 0, 5);
        gridPane.add(tfCity, 1, 5);

        // Row 6: State
        gridPane.add(new Label("State"), 0, 6);
        gridPane.add(tfState, 1, 6);
        tfState.setPrefColumnCount(2);

        // Row 7: Telephone
        gridPane.add(new Label("Telephone"), 0, 7);
        gridPane.add(tfTelephone, 1, 7);

        // Row 8: Email
        gridPane.add(new Label("Email"), 0, 8);
        gridPane.add(tfEmail, 1, 8);

        // Buttons
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(btView, 0, 0);
        buttonPane.add(btInsert, 1, 0);
        buttonPane.add(btUpdate, 2, 0);

        gridPane.add(buttonPane, 1, 9);
        gridPane.add(lblStatus, 1, 10);

        // Event handlers
        btView.setOnAction(e -> viewStaff());
        btInsert.setOnAction(e -> insertStaff());
        btUpdate.setOnAction(e -> updateStaff());

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setTitle("Staff Database Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void viewStaff() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            lblStatus.setText("Please enter an ID to view.");
            return;
        }

        String sql = "SELECT * FROM Staff WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tfLastName.setText(rs.getString("lastName"));
                tfFirstName.setText(rs.getString("firstName"));
                tfMi.setText(rs.getString("mi"));
                tfAddress.setText(rs.getString("address"));
                tfCity.setText(rs.getString("city"));
                tfState.setText(rs.getString("state"));
                tfTelephone.setText(rs.getString("telephone"));
                tfEmail.setText(rs.getString("email"));
                lblStatus.setText("Record found and displayed.");
            } else {
                clearFields();
                lblStatus.setText("No record found for ID: " + id);
            }
        } catch (SQLException ex) {
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    private void insertStaff() {
        if (!validateFields(true)) return;

        String sql = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setParameters(pstmt);
            pstmt.executeUpdate();
            lblStatus.setText("Record inserted successfully.");
        } catch (SQLException ex) {
            lblStatus.setText("Insert failed: " + ex.getMessage());
        }
    }

    private void updateStaff() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            lblStatus.setText("Please enter an ID to update.");
            return;
        }
        if (!validateFields(false)) return;

        String sql = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, " +
                     "city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setParameters(pstmt);
            pstmt.setString(9, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                lblStatus.setText("Record updated successfully.");
            } else {
                lblStatus.setText("No record found to update for ID: " + id);
            }
        } catch (SQLException ex) {
            lblStatus.setText("Update failed: " + ex.getMessage());
        }
    }

    private void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, tfId.getText().trim());
        pstmt.setString(2, tfLastName.getText().trim());
        pstmt.setString(3, tfFirstName.getText().trim());
        pstmt.setString(4, tfMi.getText().trim());
        pstmt.setString(5, tfAddress.getText().trim());
        pstmt.setString(6, tfCity.getText().trim());
        pstmt.setString(7, tfState.getText().trim());
        pstmt.setString(8, tfTelephone.getText().trim());
        pstmt.setString(9, tfEmail.getText().trim());
    }

    private boolean validateFields(boolean requireId) {
        if (requireId && tfId.getText().trim().isEmpty()) {
            lblStatus.setText("ID is required for insert.");
            return false;
        }
        // Additional validation can be added (e.g., length checks, formats)
        return true;
    }

    private void clearFields() {
        tfLastName.clear();
        tfFirstName.clear();
        tfMi.clear();
        tfAddress.clear();
        tfCity.clear();
        tfState.clear();
        tfTelephone.clear();
        tfEmail.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}