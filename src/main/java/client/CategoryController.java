package client;

import data.Data;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Random;

public class CategoryController implements Runnable {

    @FXML
    private Button category1Button, category2Button, category3Button;
    @FXML
    private Label countdownField;
    private DataHandler dataHandler;
    private int numberOfCategories;

    protected void setCategories(String[] categories, DataHandler dataHandler) {
        this.dataHandler = dataHandler;
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
        dataHandler.chosenCategory(category1Button.getText());
    }

    @FXML
    protected void onButton2Click() {
        dataHandler.chosenCategory(category2Button.getText());
    }

    @FXML
    protected void onButton3Click() {
        dataHandler.chosenCategory(category3Button.getText());
    }

    @FXML
    protected void onSurrenderClick(ActionEvent event) {
        System.exit(0); // Change this to controlled disconnection later.
    }

    private void setCountdownText(String text) {
        Platform.runLater(() -> {
            countdownField.setText(text);
        });
    }

    protected Node getNode() {
        return countdownField;
    }

    @Override // Timer that will pick random category if player don't chose.
    public void run() {
        double drawInterval = 1_000_000_000;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        setCountdownText("10");
        for (int i = 10; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                i--;
                setCountdownText(String.valueOf(i));
                delta--;
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
