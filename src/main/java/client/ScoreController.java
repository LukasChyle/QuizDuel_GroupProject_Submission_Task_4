package client;

import data.Data;
import data.Tasks;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScoreController implements Runnable {

    @FXML
    private Button ngButton;
    @FXML
    private Circle thisPortraitCircle, opponentPortraitCircle;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView portraitThis, portraitOpponent;
    @FXML
    private Button closeButton, scoreboardButton;
    @FXML
    private Label thisNickname, opponentNickname, thisScore, opponentScore, leftGameLabel,
            scoreLabel1, scoreLabel2, scoreLabel3, scoreLabel4, scoreLabel5, scoreLabel6, finnishLabel;
    @FXML
    private Circle Tq1r1, Tq2r1, Tq3r1, Tq4r1, Tq5r1, Tq1r2, Tq2r2, Tq3r2, Tq4r2, Tq5r2,
            Tq1r3, Tq2r3, Tq3r3, Tq4r3, Tq5r3, Tq1r4, Tq2r4, Tq3r4, Tq4r4, Tq5r4,
            Tq1r5, Tq2r5, Tq3r5, Tq4r5, Tq5r5, Tq1r6, Tq2r6, Tq3r6, Tq4r6, Tq5r6,
            Oq1r1, Oq2r1, Oq3r1, Oq4r1, Oq5r1, Oq1r2, Oq2r2, Oq3r2, Oq4r2, Oq5r2,
            Oq1r3, Oq2r3, Oq3r3, Oq4r3, Oq5r3, Oq1r4, Oq2r4, Oq3r4, Oq4r4, Oq5r4,
            Oq1r5, Oq2r5, Oq3r5, Oq4r5, Oq5r5, Oq1r6, Oq2r6, Oq3r6, Oq4r6, Oq5r6;
    private Stage stage;
    private Circle[][] thisCircles, opponentCircles;
    private Label[] roundLabels;
    private int thisScoreCounter, opponentScoreCounter;
    private ClientConnection connection;
    private List<Boolean[]> thisScoreList, opponentScoreList;
    private boolean lastRound;
    private int playerLeftGame;

    protected void setScene(ClientConnection connection, String thisNickname, String opponentNickname, int thisAvatar,
                            int opponentAvatar, List<Boolean[]> playerOneScore, List<Boolean[]> playerTwoScore,
                            boolean lastRound, int playerLeftGame, List<String[]> highScoreList) throws IOException {
        this.connection = connection;
        this.thisNickname.setText(thisNickname);
        this.opponentNickname.setText(opponentNickname);
        this.lastRound = lastRound;
        this.playerLeftGame = playerLeftGame;
        if (connection.getDataHandler().player == 1) {
            thisScoreList = playerOneScore;
            opponentScoreList = playerTwoScore;
        } else {
            thisScoreList = playerTwoScore;
            opponentScoreList = playerOneScore;
        }
        File file = new File("src/main/resources/client/avatars/avatar-" + thisAvatar + ".png");
        Image image = new Image(file.toURI().toString());
        this.portraitThis.setImage(image);
        file = new File("src/main/resources/client/avatars/avatar-" + opponentAvatar + ".png");
        image = new Image(file.toURI().toString());
        this.portraitOpponent.setImage(image);

        thisScoreCounter = 0;
        opponentScoreCounter = 0;
        roundLabels = new Label[]{scoreLabel1, scoreLabel2, scoreLabel3, scoreLabel4, scoreLabel5, scoreLabel6};
        thisCircles = new Circle[][]{{Tq1r1, Tq2r1, Tq3r1, Tq4r1, Tq5r1}, {Tq1r2, Tq2r2, Tq3r2, Tq4r2, Tq5r2},
                {Tq1r3, Tq2r3, Tq3r3, Tq4r3, Tq5r3}, {Tq1r4, Tq2r4, Tq3r4, Tq4r4, Tq5r4},
                {Tq1r5, Tq2r5, Tq3r5, Tq4r5, Tq5r5}, {Tq1r6, Tq2r6, Tq3r6, Tq4r6, Tq5r6,}};
        opponentCircles = new Circle[][]{{Oq1r1, Oq2r1, Oq3r1, Oq4r1, Oq5r1}, {Oq1r2, Oq2r2, Oq3r2, Oq4r2, Oq5r2},
                {Oq1r3, Oq2r3, Oq3r3, Oq4r3, Oq5r3}, {Oq1r4, Oq2r4, Oq3r4, Oq4r4, Oq5r4},
                {Oq1r5, Oq2r5, Oq3r5, Oq4r5, Oq5r5}, {Oq1r6, Oq2r6, Oq3r6, Oq4r6, Oq5r6}};
        setScoreBoard();
        if (!lastRound) {
            new Thread(this).start();
        } else {
            closeButton.setText("Exit");
            progressBar.setVisible(false);
            finnishLabel.setVisible(true);
            ngButton.setVisible(true);
            scoreboardButton.setVisible(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("highScoreScene.fxml"));
            Parent root = loader.load();
            HighScoreController hsController = loader.getController();

            Platform.runLater(() -> {
                stage = new Stage();
                stage.setTitle("High Score");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root,350,500);
                Movable.setMovable(scene, stage);
                stage.setScene(scene);
                hsController.setHighScore(highScoreList, stage);
            });
        }
    }

    private void setScoreBoard() {
        for (int i = 0; i < thisScoreList.size(); i++) {
            roundLabels[i].setVisible(true);
            Boolean[] roundThis = thisScoreList.get(i);
            Boolean[] roundOpponent = opponentScoreList.get(i);
            for (int j = 0; j < 5; j++) {
                opponentCircles[i][j].setVisible(true);
                thisCircles[i][j].setVisible(true);
            }
            for (int j = 0; j < thisScoreList.get(i).length; j++) {
                if (roundThis[j]) {
                    thisCircles[i][j].setFill(Color.GREEN);
                    thisScoreCounter++;
                } else {
                    thisCircles[i][j].setFill(Color.RED);
                }
                if (roundOpponent[j]) {
                    opponentCircles[i][j].setFill(Color.GREEN);
                    opponentScoreCounter++;
                } else {
                    opponentCircles[i][j].setFill(Color.RED);
                }
            }
        }
        thisScore.setText("Score: " + thisScoreCounter);
        opponentScore.setText("Score: " + opponentScoreCounter);
        if (playerLeftGame != 0) {
            thisPortraitCircle.setFill(Color.GREEN);
            opponentPortraitCircle.setFill(Color.RED);
            finnishLabel.setText("You won!");
            finnishLabel.setTextFill(Color.GREEN);
            leftGameLabel.setVisible(true);
            leftGameLabel.setText(opponentNickname.getText() + " Left Game");
        } else if (thisScoreCounter < opponentScoreCounter) {
            thisPortraitCircle.setFill(Color.RED);
            opponentPortraitCircle.setFill(Color.GREEN);
            if (lastRound) {
                finnishLabel.setText("You lost!");
                finnishLabel.setTextFill(Color.RED);
            }
        } else if (thisScoreCounter > opponentScoreCounter) {
            thisPortraitCircle.setFill(Color.GREEN);
            opponentPortraitCircle.setFill(Color.RED);
            if (lastRound) {
                finnishLabel.setText("You won!");
                finnishLabel.setTextFill(Color.GREEN);
            }
        } else {
            thisPortraitCircle.setFill(Color.YELLOW);
            opponentPortraitCircle.setFill(Color.YELLOW);
            if (lastRound) {
                finnishLabel.setText("It's a draw!");
            }
        }
    }

    protected Node getNode() {
        return progressBar;
    }

    public void onCloseClick() {
        System.exit(0);
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        for (double i = 1; i > 0; ) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 0.01) {
                i -= 0.001;
                double j = i;
                Platform.runLater(() -> {
                    progressBar.setProgress(j);
                });
                delta -= 0.01;
            }
        }
        Data data = new Data();
        data.task = Tasks.READY_ROUND;
        data.player = connection.getDataHandler().player;
        connection.sendData(data);
    }

    public void onScoreboardButtonClick() {
        stage.setY(getNode().getScene().getWindow().getY());
        stage.setX(getNode().getScene().getWindow().getX());
        stage.show();
    }

    public void onNewGameClick() {
        Data data = new Data();
        data.message = "Waiting for a opponent";
        try {
            connection.getDataHandler().setWait(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(connection).start();
    }
}

