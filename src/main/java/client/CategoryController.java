package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Random;

public class CategoryController implements Runnable {

    @FXML
    private Button category1Button, category2Button, category3Button;
    @FXML
    private Label countdownField;
    private DataHandler dataHandler;

    protected void setCategories(String[] categories, DataHandler dh) {
        this.dataHandler = dh;
        category1Button.setText(categories[0]);
        category2Button.setText(categories[1]);
        category3Button.setText(categories[2]);
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
        System.exit(0); // Temporary
    }

    @Override // Timer that will pick random category if player don't chose.
    public void run() {
        double drawInterval = 1_000_000_000;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        countdownField.setText("10");
        for (int i = 10; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                i--;
                countdownField.setText(String.valueOf(i));
                delta--;
            }
        }
        Random random = new Random();
        int number = random.nextInt(3);
        switch (number) {
            case 0 -> onButton1Click();
            case 1 -> onButton2Click();
            case 2 -> onButton3Click();
        }
    }
}
