import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdditionQuizFX extends Application {

    private static final int NUM_QUESTIONS = 10;
    private static final int MAX_NUMBER = 20;

    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int correctCount = 0;

    private Label lblQuestion;
    private TextField tfAnswer;
    private Button btNext;
    private Button btSubmit;
    private Label lblStatus;

    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        generateQuestions();

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label lblTitle = new Label("Addition Quiz");
        lblTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        lblQuestion = new Label();
        lblQuestion.setStyle("-fx-font-size: 20px;");

        tfAnswer = new TextField();
        tfAnswer.setPrefWidth(100);
        tfAnswer.setAlignment(Pos.CENTER);
        tfAnswer.setStyle("-fx-font-size: 18px;");

        btNext = new Button("Next");
        btNext.setPrefWidth(100);
        btNext.setOnAction(e -> handleNext());

        btSubmit = new Button("Submit");
        btSubmit.setPrefWidth(100);
        btSubmit.setVisible(false); // Shown only on last question
        btSubmit.setOnAction(e -> showResults(primaryStage));

        lblStatus = new Label("");
        lblStatus.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

        root.getChildren().addAll(lblTitle, lblQuestion, tfAnswer, btNext, btSubmit, lblStatus);

        showCurrentQuestion();

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Addition Quiz");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Focus on answer field
        tfAnswer.requestFocus();
    }

    private void generateQuestions() {
        questions.clear();
        for (int i = 0; i < NUM_QUESTIONS; i++) {
            int num1 = random.nextInt(MAX_NUMBER + 1);
            int num2 = random.nextInt(MAX_NUMBER + 1);
            questions.add(new Question(num1, num2));
        }
    }

    private void showCurrentQuestion() {
        Question q = questions.get(currentQuestionIndex);
        lblQuestion.setText(String.format("Question %d/%d: What is %d + %d ?",
                currentQuestionIndex + 1, NUM_QUESTIONS, q.num1, q.num2));

        tfAnswer.clear();
        lblStatus.setText("");

        // Show Submit on last question
        btSubmit.setVisible(currentQuestionIndex == NUM_QUESTIONS - 1);
        btNext.setVisible(currentQuestionIndex < NUM_QUESTIONS - 1);

        tfAnswer.requestFocus();
    }

    private void handleNext() {
        String answerText = tfAnswer.getText().trim();
        if (answerText.isEmpty()) {
            lblStatus.setText("Please enter an answer.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int userAnswer = Integer.parseInt(answerText);
            Question q = questions.get(currentQuestionIndex);
            q.userAnswer = userAnswer;

            if (userAnswer == q.correctAnswer()) {
                correctCount++;
                lblStatus.setText("Correct!");
                lblStatus.setStyle("-fx-text-fill: green;");
            } else {
                lblStatus.setText("Wrong. The correct answer is " + q.correctAnswer());
                lblStatus.setStyle("-fx-text-fill: red;");
            }

            // Move to next after a short delay or immediately
            currentQuestionIndex++;
            if (currentQuestionIndex < NUM_QUESTIONS) {
                showCurrentQuestion();
            }
        } catch (NumberFormatException ex) {
            lblStatus.setText("Please enter a valid number.");
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private void showResults(Stage primaryStage) {
        // Final check for last question
        if (currentQuestionIndex == NUM_QUESTIONS - 1) {
            handleNext(); // Process last answer if not already done
        }

        VBox resultPane = new VBox(15);
        resultPane.setPadding(new Insets(30));
        resultPane.setAlignment(Pos.CENTER);

        Label lblResultTitle = new Label("Quiz Completed!");
        lblResultTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label lblScore = new Label(String.format("You scored %d out of %d (%.0f%%)",
                correctCount, NUM_QUESTIONS, (correctCount * 100.0 / NUM_QUESTIONS)));
        lblScore.setStyle("-fx-font-size: 18px;");

        TextArea taDetails = new TextArea();
        taDetails.setEditable(false);
        taDetails.setPrefHeight(200);

        StringBuilder details = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String status = (q.userAnswer == q.correctAnswer()) ? "Correct" : "Wrong";
            details.append(String.format("Question %d: %d + %d = %d   Your answer: %d   [%s]\n",
                    i + 1, q.num1, q.num2, q.correctAnswer(), q.userAnswer, status));
        }
        taDetails.setText(details.toString());

        Button btRestart = new Button("Take New Quiz");
        btRestart.setPrefWidth(150);
        btRestart.setOnAction(e -> restartQuiz(primaryStage));

        resultPane.getChildren().addAll(lblResultTitle, lblScore, new Label("Detailed Results:"), taDetails, btRestart);

        Scene resultScene = new Scene(resultPane, 600, 500);
        primaryStage.setScene(resultScene);
        primaryStage.setTitle("Quiz Results");
    }

    private void restartQuiz(Stage primaryStage) {
        correctCount = 0;
        currentQuestionIndex = 0;
        generateQuestions();
        start(primaryStage); // Restart the quiz
    }

    // Simple inner class to hold question data
    private static class Question {
        int num1, num2;
        int userAnswer = -1; // -1 means not answered

        Question(int num1, int num2) {
            this.num1 = num1;
            this.num2 = num2;
        }

        int correctAnswer() {
            return num1 + num2;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}