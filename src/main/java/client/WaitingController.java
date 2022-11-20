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
    }

    public void onCloseClick(ActionEvent event) {

        dataHandler.chosenCategory(null); // test to send data to server.


       // System.exit(0); // Temporary
    }
}
