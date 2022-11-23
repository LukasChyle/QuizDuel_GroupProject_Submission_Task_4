package client;

import data.Data;
import data.Tasks;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.util.Random;

public class CategoryController implements Runnable {

    @FXML
    private Button category1Button, category2Button, category3Button;
    @FXML
    private ProgressBar progressBar;
    private ClientConnection clientConnection;
    private int numberOfCategories;
    private boolean breakTimer;

    protected void setCategories(String[] categories, ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        numberOfCategories = categories.length;

        category1Button.setText(categories[0]);
        if (categories.length >= 2 && categories[1] != null) {
            category2Button.setText(categories[1]);
        } else {
            category2Button.setVisible(false);
        }
        if (categories.length >= 3 && categories[2] != null) {
            category3Button.setText(categories[2]);
        } else {
            category3Button.setVisible(false);
        }
        new Thread(this).start();
    }

    @FXML
    protected void onButton1Click() {
        chosenCategory(category1Button.getText());
    }

    @FXML
    protected void onButton2Click() {
        chosenCategory(category2Button.getText());
    }

    @FXML
    protected void onButton3Click() {
        chosenCategory(category3Button.getText());
    }

    @FXML
    protected void onSurrenderClick(ActionEvent event) {
        System.exit(0); // Change this to controlled disconnection later.
    }

    protected void chosenCategory(String category) { // Client returns a category for the server.
        breakTimer = true;
        Data data = new Data();
        data.task = Tasks.PICK_CATEGORY;
        data.message = category;
        clientConnection.sendData(data);
    }

    protected Node getNode() {
        return progressBar;
    }

    @Override // Timer that will pick random category if player don't chose.
    public void run() {
        double drawInterval = 1_000_000_000;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        breakTimer = false;
        for (double i = 1; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (breakTimer) {
                return;
            }
            if (delta >= 0.01) {
                i -= 0.001;
                double j = i;
                Platform.runLater(() -> {
                    progressBar.setProgress(j);
                });
                delta -= 0.01;
            }
        }
        Random random = new Random();
        int number = random.nextInt(numberOfCategories);
        switch (number) {
            case 0 -> onButton1Click();
            case 1 -> onButton2Click();
            case 2 -> onButton3Click();
        }
    }
}
