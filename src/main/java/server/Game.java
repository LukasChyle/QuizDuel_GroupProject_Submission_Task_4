package server;

import data.Data;
import data.Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Game {
    private final CategoryHandler categoryHandler;
    private ServerConnection p1, p2;
    private Boolean playerOneIsReady = false, playerTwoIsReady = false, gameFinnish = false;
    private int round = 0, currentPlayer = 1;
    private final int totalRounds;
    private final List<Boolean[]> playerOneScore = new ArrayList<>(), playerTwoScore = new ArrayList<>();
    private int playerOneAvatar, playerTwoAvatar;
    private String playerOneNickname, playerTwoNickname;
    private GetHighScore g;

    protected Game(Properties p) {
        int rounds = Integer.parseInt(p.getProperty("roundsPerGame", "3"));
        int questions = Integer.parseInt(p.getProperty("questionPerRound", "3"));

        if (rounds > 6 || rounds < 1) {
            rounds = 3;
        }
        if (questions > 5 || questions < 1) {
            questions = 3;
        }
        totalRounds = rounds;
        categoryHandler = new CategoryHandler(questions);
        g = new GetHighScore();
    }

    protected void protocol(Data data) { // income data from Client
        if (!gameFinnish) {
            switch (data.task) {
                case PICK_CATEGORY -> setCategory(data);
                case SET_SCORE -> setScore(data);
                case OPPONENT_INFO -> setPlayer(data);
                case READY_ROUND -> startRound(data);
                case LEFT_GAME -> playerLeftGame(data);
            }
        }
    }

    private void playerLeftGame(Data data) {
        if (!gameFinnish) {
            gameFinnish = true;
            Data data1 = new Data();
            data1.task = Tasks.SET_SCORE;
            data1.lastRound = true;
            data1.player = data.player;
            data1.playerOneScore = playerOneScore;
            data1.playerTwoScore = playerTwoScore;
            if (data.player == 1) {
                p2.sendData(data1);
            } else {
                p1.sendData(data1);
            }
        }
    }

    protected void setPlayers(ServerConnection p1, ServerConnection p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    private void setPlayer(Data data) {
        if (data.player == 1) {
            playerOneAvatar = data.opponentAvatar;
            playerOneNickname = data.opponentNickname;
            p2.sendData(data);
        } else if (data.player == 2) {
            playerTwoAvatar = data.opponentAvatar;
            playerTwoNickname = data.opponentNickname;
            p1.sendData(data);
        }
    }

    private void startRound(Data data) { // waits for both players, each round current player is switched.
        if (data.player == 1) {
            playerOneIsReady = true;
        } else if (data.player == 2) {
            playerTwoIsReady = true;
        }
        if (playerOneIsReady && playerTwoIsReady) {
            round++;
            Data data1 = new Data();
            data1.task = Tasks.PICK_CATEGORY;
            data1.categoriesToChoose = categoryHandler.categoriesToChoose();
            Data data2 = new Data();
            data2.task = Tasks.WAIT;
            data2.message = "Opponent is picking a category";

            if (currentPlayer == 1) {
                p1.sendData(data1);
                p2.sendData(data2);
                currentPlayer = 2;
            } else {
                p1.sendData(data2);
                p2.sendData(data1);
                currentPlayer = 1;
            }
            playerOneIsReady = false;
            playerTwoIsReady = false;
        }
    }

    private void setCategory(Data data) {
        List<String[]> ar = categoryHandler.getQuestions(data.message);
        Data data1 = new Data();
        data1.task = Tasks.ROUND;
        data1.message = data.message;
        data1.questions = ar;
        p1.sendData(data1);
        p2.sendData(data1);
    }

    private void setScore(Data data) {
        Data data2 = new Data();
        data2.task = Tasks.WAIT;
        data2.message = "Waiting for opponent to finnish";

        if (data.player == 1) {
            playerOneScore.add(data.roundScore);
            playerOneIsReady = true;
        } else if (data.player == 2) {
            playerTwoScore.add(data.roundScore);
            playerTwoIsReady = true;
        }

        if (playerOneIsReady && !playerTwoIsReady) {
            p1.sendData(data2);
        } else if (playerTwoIsReady && !playerOneIsReady) {
            p2.sendData(data2);
        }

        if (playerOneIsReady && playerTwoIsReady) {
            Data data1 = new Data();
            data1.task = Tasks.SET_SCORE;
            if (round < totalRounds) {
                data1.lastRound = false;
            } else {
                data1.lastRound = true;
                gameFinnish = true;
                data1.highScoreList = g.getHighScore(playerOneScore,playerOneNickname,playerOneAvatar);
                data1.highScoreList= g.getHighScore(playerTwoScore,playerTwoNickname,playerTwoAvatar);
            }
            data1.playerOneScore = playerOneScore;
            data1.playerTwoScore = playerTwoScore;
            p1.sendData(data1);
            p2.sendData(data1);
            playerOneIsReady = false;
            playerTwoIsReady = false;
        }
    }
}
