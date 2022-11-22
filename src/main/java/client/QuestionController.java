package client;

import data.Data;
import data.Tasks;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Random;

public class QuestionController implements Runnable {

    @FXML
    private TextArea questionTextArea;
    @FXML
    private Label countdownLabel, categoryLabel;
    @FXML
    private Button button1, button2, button3, button4, closeButton;
    private List<String[]> questionsList;
    private ClientConnection clientConnection;
    private int questionNumber, currentAnswer;
    private Boolean[] answers = new Boolean[3];
    private boolean breakTimer;

    protected void setScene(List<String[]> questionsList, String category, ClientConnection clientConnection) {
        this.questionsList = questionsList;
        this.clientConnection = clientConnection;
        categoryLabel.setText(category);
        questionNumber = 0;
        setLayout();
    }

    public void onButton1Click() {
        setAnswer(1);
    }

    public void onButton2Click() {
        setAnswer(2);
    }

    public void onButton3Click() {
        setAnswer(3);
    }

    public void onButton4Click() {
        setAnswer(4);
    }

    public void onCloseClick() {
        System.exit(0);
    }

    protected Node getNode() {
        return categoryLabel;
    }

    private void setLayout() {
        Platform.runLater(() -> {
            String[] question = questionsList.get(questionNumber);
            questionTextArea.setText(question[0].trim());
            button1.setText(question[1].trim());
            button2.setText(question[2].trim());
            button3.setText(question[3].trim());
            button4.setText(question[4].trim());
            currentAnswer = Integer.parseInt(question[5].trim());
            new Thread(this).start();
        });
    }

    private void setAnswer(int choice) {
        breakTimer = true;
        if (choice == currentAnswer) {
            answers[questionNumber] = true;
        } else {
            answers[questionNumber] = false;
        }

        if (questionNumber <= 2) {
            questionNumber++;
            setLayout();
        } else {
            Data data = new Data();
            data.task = Tasks.SET_SCORE;
            data.roundScore = answers;
            data.player = clientConnection.getDataHandler().player;
            clientConnection.sendData(data);
        }

    }

    @Override // Timer
    public void run() {
        double drawInterval = 1_000_000_000;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        breakTimer = false;
        setCountdownText("15");
        for (int i = 15; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                i--;
                setCountdownText(String.valueOf(i));
                delta--;
            }
            if (breakTimer) {
                return;
            }
        }
        answers[currentAnswer] = false;
    }

    private void setCountdownText(String text) {
        Platform.runLater(() -> {
            countdownLabel.setText("Time Left: " + text);
        });
    }
}
