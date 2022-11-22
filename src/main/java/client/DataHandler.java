package client;


import data.Data;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import data.Tasks;

import java.io.IOException;

public class DataHandler {

    private final ClientConnection connection;
    private int ownAvatar, opponentAvatar;
    private String ownNickname, opponentNickname;
    protected Node currentNode;
    private int player;

    protected DataHandler(String nickname, int avatar, ClientConnection connection) {
        ownNickname = nickname;
        ownAvatar = avatar;
        this.connection = connection;
    }

    protected void sendTestData() { // test metod to send data with.
        Data data = new Data();
        data.task = Tasks.PICK_CATEGORY;
        // fill data with test values.
        connection.sendData(data);
    }

    protected void readData(Data data) throws IOException { // income data from server

        switch (data.task) {
            case PICK_CATEGORY -> setCategory(data);
            case SET_SCORE ->  setScore(data);
            case SET_PLAYER -> setPlayer(data);
            case WAIT -> setWait(data);
            case OPPONENT_INFO -> setOpponentInfo(data);
        }

        System.out.println("Data came to client"); // test
        this.player = data.player;
        System.out.println(player);
    }

    protected void setWait(Data data) throws IOException {
        String cancelButtonText;
        if (data.task != null) {
            cancelButtonText = "Exit";
        } else {
            cancelButtonText = "Surrender";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("waitingScene.fxml"));
        Parent root = loader.load();
        WaitingController waitCon = loader.getController();
        waitCon.setLayout(data.message, cancelButtonText, this);
        startNewScene(root);
        currentNode = waitCon.getNode();
    }

    private void setScore(Data data) {
        // TODO: Create scoreboard scene
    }


    // makes the client go to category scene to pick a category from a selection.
    private void setCategory(Data data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScene.fxml"));
        Parent root = loader.load();
        CategoryController categoryCon = loader.getController();
        categoryCon.setCategories(data.categoriesToChoose, this);
        startNewScene(root);
        currentNode = categoryCon.getNode();
    }

    protected void chosenCategory(String category) { // Client returns a category for the server.
        Data data = new Data();
        // TODO: send the category to the server.
    }

    private void setPlayer(Data data) { // set if player 1 or 2
        this.player = data.player;
        System.out.println(player); // test
    }

    private void setOpponentInfo(Data data) {
        opponentAvatar = data.avatar;
        opponentNickname = data.opponentNickname;
    }

    private void startNewScene(Parent root) {
        Stage stage = (Stage) currentNode.getScene().getWindow();
        Scene scene = new Scene(root);
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            Movable.setMovable(scene, stage);
        });
    }
}
