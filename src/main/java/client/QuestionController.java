package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QuestionController {

    @FXML
    private TextArea questionTextArea;
    @FXML
    private Label countdownLabel;
    @FXML
    private Button button1, button2, button3, button4, closeButton;

    protected QuestionController() {

    }

    public void onButton1Click(ActionEvent event) {
    }

    public void onButton2Click(ActionEvent event) {
    }

    public void onButton3Click(ActionEvent event) {
    }

    public void onButton4Click(ActionEvent event) {
    }

    public void onCloseClick(ActionEvent event) {
        System.exit(0);
    }
}
