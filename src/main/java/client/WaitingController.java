package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WaitingController {

    @FXML
    private Label MessageField;
    @FXML
    private Button closeButton;
    private DataHandler dataHandler;

    protected void setLayout(String message, String buttonText, DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        MessageField.setText(message);
        closeButton.setText(buttonText);
        dataHandler.setNode(MessageField);

        closeButton.setText("send test data"); // test
    }

    public void onCloseClick(ActionEvent event) {
        dataHandler.sendTestData(); // test to send data to server.
       // System.exit(0); // Temporary
    }
}
