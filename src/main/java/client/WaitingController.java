package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WaitingController {

    @FXML
    private Label messageField;
    @FXML
    private Button closeButton;

    protected void setLayout(String message, String buttonText) {
        messageField.setText(message);
        closeButton.setText(buttonText);
    }

    public void onCloseClick(ActionEvent event) {
        System.exit(0); // Change this to controlled disconnection later
    }
    protected Node getNode() {
        return messageField;
    }
}
