package client;

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

    protected DataHandler(String nickname, int avatar, ClientConnection connection) {
        ownNickname = nickname;
        ownAvatar = avatar;
        this.connection = connection;
    }

    protected void readData(Data data) { // Read the data from server and decide what to do.

    }

    // makes the client go to the category scene to pick a category.
    protected void switchToCategoryScene(String[] categories) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScene.fxml"));
        Parent root = loader.load();
        CategoryController categoryCon = loader.getController();
        categoryCon.setCategories(categories, this);
        /* TODO: needs another solution to change the scene, because "event" from current scene is not available.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
         */
    }

    protected void chosenCategory(String category) { // Client returns a category for the server.
        Data data = new Data();
        // TODO: send the category to the server.



        connection.sendData(data);
    }







}
