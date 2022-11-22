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
    private DataHandler dataHandler;

    protected void setLayout(String message, String buttonText, DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        messageField.setText(message);
        closeButton.setText(buttonText);

        closeButton.setText("send test data"); // test
    }

    public void onCloseClick(ActionEvent event) {
        dataHandler.sendTestData(); // test to send data to server.
       // System.exit(0); // Change this to controlled disconnection later
    }

    protected Node getNode() {
        return messageField;
    }
}
