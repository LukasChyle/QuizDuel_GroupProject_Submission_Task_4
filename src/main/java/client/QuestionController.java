package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public class QuestionController {

    @FXML
    private TextArea questionTextArea;
    @FXML
    private Label countdownLabel, categoryLabel;
    @FXML
    private Button button1, button2, button3, button4, closeButton;
    private List<String[]> questions;
    private DataHandler dataHandler;

    protected void setLayout(List<String[]> questions, String category, DataHandler dataHandler) {
        this.questions = questions;
        this.dataHandler = dataHandler;
        categoryLabel.setText(category);


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

    protected Node getNode() {
        return categoryLabel;
    }
}
