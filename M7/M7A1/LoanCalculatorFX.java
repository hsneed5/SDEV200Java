import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoanCalculatorFX extends Application {

    // Input fields
    private TextField tfLoanAmount = new TextField();
    private TextField tfInterestRate = new TextField(); // Annual interest rate in %
    private TextField tfNumberOfYears = new TextField();

    // Output fields
    private TextField tfMonthlyPayment = new TextField();
    private TextField tfTotalPayment = new TextField();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(12);

        // Labels and fields
        grid.add(new Label("Loan Amount:"), 0, 0);
        grid.add(tfLoanAmount, 1, 0);

        grid.add(new Label("Annual Interest Rate (%):"), 0, 1);
        grid.add(tfInterestRate, 1, 1);

        grid.add(new Label("Number of Years:"), 0, 2);
        grid.add(tfNumberOfYears, 1, 2);

        Button btCompute = new Button("Compute Loan Payment");
        grid.add(btCompute, 1, 3);
        GridPane.setMargin(btCompute, new Insets(10, 0, 10, 0));

        grid.add(new Label("Monthly Payment:"), 0, 4);
        grid.add(tfMonthlyPayment, 1, 4);
        tfMonthlyPayment.setEditable(false);

        grid.add(new Label("Total Payment:"), 0, 5);
        grid.add(tfTotalPayment, 1, 5);
        tfTotalPayment.setEditable(false);

        // Set prompt text and alignment
        tfLoanAmount.setPromptText("e.g., 10000");
        tfInterestRate.setPromptText("e.g., 5.75");
        tfNumberOfYears.setPromptText("e.g., 30");

        tfMonthlyPayment.setStyle("-fx-background-color: #f0f0f0;");
        tfTotalPayment.setStyle("-fx-background-color: #f0f0f0;");

        // Right-align input fields
        tfLoanAmount.setAlignment(Pos.BASELINE_RIGHT);
        tfInterestRate.setAlignment(Pos.BASELINE_RIGHT);
        tfNumberOfYears.setAlignment(Pos.BASELINE_RIGHT);
        tfMonthlyPayment.setAlignment(Pos.BASELINE_RIGHT);
        tfTotalPayment.setAlignment(Pos.BASELINE_RIGHT);

        btCompute.setOnAction(e -> computeLoan());

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Loan Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void computeLoan() {
        // Validate input
        double loanAmount;
        double annualInterestRate;
        int numberOfYears;

        try {
            loanAmount = Double.parseDouble(tfLoanAmount.getText().trim());
            annualInterestRate = Double.parseDouble(tfInterestRate.getText().trim());
            numberOfYears = Integer.parseInt(tfNumberOfYears.getText().trim());

            if (loanAmount <= 0 || annualInterestRate < 0 || numberOfYears <= 0) {
                showAlert("Please enter positive values.");
                return;
            }
        } catch (NumberFormatException ex) {
            showAlert("Please enter valid numeric values.");
            return;
        }

        // Use the Loan class logic
        Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);

        // Display results with 2 decimal places
        tfMonthlyPayment.setText(String.format("$%.2f", loan.getMonthlyPayment()));
        tfTotalPayment.setText(String.format("$%.2f", loan.getTotalPayment()));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner Loan class
    public static class Loan {
        private double annualInterestRate;
        private int numberOfYears;
        private double loanAmount;
        private java.util.Date loanDate;

        /** Default constructor */
        public Loan() {
            this(2.5, 1, 1000);
        }

        public Loan(double annualInterestRate, int numberOfYears,
                    double loanAmount) {
            this.annualInterestRate = annualInterestRate;
            this.numberOfYears = numberOfYears;
            this.loanAmount = loanAmount;
            loanDate = new java.util.Date();
        }

        /** Return annualInterestRate */
        public double getAnnualInterestRate() {
            return annualInterestRate;
        }

        /** Set a new annualInterestRate */
        public void setAnnualInterestRate(double annualInterestRate) {
            this.annualInterestRate = annualInterestRate;
        }

        /** Return numberOfYears */
        public int getNumberOfYears() {
            return numberOfYears;
        }

        /** Set a new numberOfYears */
        public void setNumberOfYears(int numberOfYears) {
            this.numberOfYears = numberOfYears;
        }

        /** Return loanAmount */
        public double getLoanAmount() {
            return loanAmount;
        }

        /** Set a new loanAmount */
        public void setLoanAmount(double loanAmount) {
            this.loanAmount = loanAmount;
        }

        /** Find monthly payment */
        public double getMonthlyPayment() {
            double monthlyInterestRate = annualInterestRate / 1200;
            double monthlyPayment = loanAmount * monthlyInterestRate /
                    (1 - (1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12)));
            return monthlyPayment;
        }

        /** Find total payment */
        public double getTotalPayment() {
            return getMonthlyPayment() * numberOfYears * 12;
        }

        /** Return loan date */
        public java.util.Date getLoanDate() {
            return loanDate;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}