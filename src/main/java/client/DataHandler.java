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

    protected final ClientConnection connection;
    protected int ownAvatar, opponentAvatar;
    protected String ownNickname, opponentNickname;
    protected Node currentNode;
    protected int player;

    protected DataHandler(String nickname, int avatar, ClientConnection connection) {
        ownNickname = nickname;
        ownAvatar = avatar;
        this.connection = connection;
    }

    protected void readData(Data data) throws IOException { // income data from server
        switch (data.task) {
            case PICK_CATEGORY -> setCategory(data);
            case SET_SCORE -> setScore(data);
            case SET_PLAYER -> setPlayer(data);
            case WAIT -> setWait(data);
            case ROUND -> setRound(data);
            case OPPONENT_INFO -> setOpponentInfo(data);
        }
    }

    private void setRound(Data data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("questionScene.fxml"));
        Parent root = loader.load();
        QuestionController questionCon = loader.getController();
        questionCon.setScene(data.questions, data.message, connection);
        startNewScene(root);
        currentNode = questionCon.getNode();
    }

    protected void setWait(Data data) throws IOException {
        String cancelButtonText;
        if (data.task != null) {
            cancelButtonText = "Surrender";
        } else {
            cancelButtonText = "Exit";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("waitingScene.fxml"));
        Parent root = loader.load();
        WaitingController waitCon = loader.getController();
        waitCon.setLayout(data.message, cancelButtonText);
        startNewScene(root);
        currentNode = waitCon.getNode();
    }

    private void setScore(Data data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreScene.fxml"));
        Parent root = loader.load();
        ScoreController scoreCon = loader.getController();
        scoreCon.setScene(connection, ownNickname, opponentNickname, ownAvatar, opponentAvatar,
                data.playerOneScore, data.playerTwoScore, data.lastRound, data.player);
        startNewScene(root);
        currentNode = scoreCon.getNode();
    }

    // makes the client go to category scene to pick a category from a selection.
    private void setCategory(Data data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScene.fxml"));
        Parent root = loader.load();
        CategoryController categoryCon = loader.getController();
        categoryCon.setCategories(data.categoriesToChoose, connection);
        startNewScene(root);
        currentNode = categoryCon.getNode();
    }

    private void setPlayer(Data data) { // set if player 1 or 2. Set opponent avatar and nick
        this.player = data.player;
        Data newData = new Data();
        newData.task = Tasks.OPPONENT_INFO;
        newData.opponentNickname = ownNickname;
        newData.opponentAvatar = ownAvatar;
        newData.player = player;
        connection.sendData(newData);
    }

    private void setOpponentInfo(Data data) {
        opponentAvatar = data.opponentAvatar;
        opponentNickname = data.opponentNickname;
        Data newData = new Data();
        newData.task = Tasks.READY_ROUND;
        newData.player = player;
        connection.sendData(newData);
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