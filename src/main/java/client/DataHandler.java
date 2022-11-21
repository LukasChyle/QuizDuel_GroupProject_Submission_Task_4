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
    private Node currentNode;
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

    private void setOpponentInfo(Data data) {
        opponentAvatar = data.avatar;
        opponentNickname = data.opponentNickname;
    }

    private void setWait(Data data) {
        // TODO: Create wait scene with specific message.
    }

    private void setScore(Data data) {
        // TODO: Create scoreboard scene
    }


    // makes the client go to the category scene to pick a category.
    protected void setCategory(Data data) throws IOException {

        // TODO: get String[] with categories from Data object.
        String[] categories = new String[]{"Sport"}; //test

        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScene.fxml"));
        Parent root = loader.load();
        CategoryController categoryCon = loader.getController();
        categoryCon.setCategories(categories, this);
        Stage stage = (Stage) currentNode.getScene().getWindow();
        Scene scene = new Scene(root);
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
            Movable.setMovable(scene, stage);
        });
    }

    protected void chosenCategory(String category) { // Client returns a category for the server.
        Data data = new Data();
        // TODO: send the category to the server.
    }


    private void setPlayer(Data data) { // set if player 1 or 2
        this.player = data.player;
        System.out.println(player); // test
    }

    protected void setNode(Node node) {
        currentNode = node;
    } // node from current scene to change for a new one.
}
