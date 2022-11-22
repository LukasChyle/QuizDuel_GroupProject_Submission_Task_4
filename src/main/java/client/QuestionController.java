package client;

import data.Data;
import data.Tasks;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

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

    public void onButton1Click() throws InterruptedException {
        setAnswer(1, button1);
    }

    public void onButton2Click() throws InterruptedException {
        setAnswer(2, button2);
    }

    public void onButton3Click() throws InterruptedException {
        setAnswer(3, button3);
    }

    public void onButton4Click() throws InterruptedException {
        setAnswer(4, button4);
    }

    public void onCloseClick() {
        System.exit(0);
    }

    protected Node getNode() {
        return categoryLabel;
    }

    private void setLayout() {
        Platform.runLater(() -> {
            button1.setStyle("-fx-background-color: null;");
            button2.setStyle("-fx-background-color: null;");
            button3.setStyle("-fx-background-color: null;");
            button4.setStyle("-fx-background-color: null;");
            button1.setStyle("-fx-border-color: null;");
            button2.setStyle("-fx-border-color: null;");
            button3.setStyle("-fx-border-color: null;");
            button4.setStyle("-fx-border-color: null;");
            button1.setMouseTransparent(false);
            button2.setMouseTransparent(false);
            button3.setMouseTransparent(false);
            button4.setMouseTransparent(false);
            String[] question = questionsList.get(questionNumber);
            questionTextArea.setText(question[1].trim());
            button1.setText(question[2].trim());
            button2.setText(question[3].trim());
            button3.setText(question[4].trim());
            button4.setText(question[5].trim());
            currentAnswer = Integer.parseInt(question[6].trim());
            new Thread(this).start();
        });
    }

    private void setAnswer(int choice, Button button) {
        breakTimer = true;
        button1.setMouseTransparent(true);
        button2.setMouseTransparent(true);
        button3.setMouseTransparent(true);
        button4.setMouseTransparent(true);

        if (choice == currentAnswer) {
            answers[questionNumber] = true;
            setButtonColor(button, true);
        } else {
            answers[questionNumber] = false;
            setButtonColor(button, false);
        }

        System.out.println("came to pause");

        pause();
        if (questionNumber <= 1) {
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
        setCountdownText("Time Left: 15");
        for (int i = 15; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                i--;
                setCountdownText("Time Left: " + i);
                delta--;
            }
            if (breakTimer) {
                break;
            }
        }
        if (!breakTimer) {
            setCountdownText("Time is out");
            setAnswer(0, null);
        }
    }

    private void setButtonColor(Button button, boolean rightAnswer) {
        Button rightButton = switch (currentAnswer) {
            case 1 -> button1;
            case 2 -> button2;
            case 3 -> button3;
            case 4 -> button4;
            default -> null;
        };

        if (rightButton != null) {
            changeColor(rightButton, "-fx-border-color: green;");
        }
        if (rightAnswer) {
            changeColor(button, "-fx-background-color: green;");
        } else {
            if (button != null) {
                changeColor(button, "-fx-background-color: red;");
            } else {
                if (rightButton != button1) {
                    changeColor(button1, "-fx-border-color: red;");
                }
                if (rightButton != button2) {
                    changeColor(button2, "-fx-border-color: red;");
                }
                if (rightButton != button3) {
                    changeColor(button3, "-fx-border-color: red;");
                }
                if (rightButton != button4) {
                    changeColor(button4, "-fx-border-color: red;");
                }
            }
        }
        System.out.println("did change colors");
    }

    private void changeColor(Button button, String color) {
        Platform.runLater(() -> {
            button.setStyle(color);
        });
    }

    private void setCountdownText(String text) {
        Platform.runLater(() -> {
            countdownLabel.setText(text);
        });
    }

    private void pause() {
        double drawInterval = 1_000_000_000;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        for (int i = 2; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                i--;
                delta--;
            }
        }
    }
}
