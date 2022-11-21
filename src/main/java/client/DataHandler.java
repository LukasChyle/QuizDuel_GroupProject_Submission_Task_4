package client;


import data.Data;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        // fill data with test values.
        connection.sendData(data);
    }

    protected void readData(Data data) throws IOException { // income data from server
        System.out.println("Data came to client"); // test
        switchToCategoryScene(new String[]{"Sport"}); // test
        this.player = data.player;
        System.out.println(player);
    }

    protected void setNode(Node node) {
        currentNode = node;
    } // node from current scene to change for a new one.


    // makes the client go to the category scene to pick a category.
    protected void switchToCategoryScene(String[] categories) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScene.fxml"));
        Parent root = loader.load();
        CategoryController categoryCon = loader.getController();
        categoryCon.setCategories(categories, this);
        Stage stage = (Stage) currentNode.getScene().getWindow();
        Scene scene = new Scene(root);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.show();
                Movable.setMovable(scene, stage);
            }
        });
    }

    protected void chosenCategory(String category) { // Client returns a category for the server.
        Data data = new Data();
        // TODO: send the category to the server.
    }




}
