package server;

import data.Data;
import data.Tasks;

import java.util.ArrayList;

public class Game {

    private ServerConnection p1, p2;

    protected void setPlayers(ServerConnection p1, ServerConnection p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    protected void protocol(Data data) { // income data from Client

        switch (data.task) {
            case PICK_CATEGORY -> setCategory(data);
            case SET_SCORE ->  setScore(data);
            case OPPONENT_INFO -> setPlayer(data);
            case FINNISH -> endGame(data);
        }
    }

    private void setCategory(Data data) {

    }

    private void setScore(Data data) {
    }

    private void setPlayer(Data data) {
        if (data.player == 1){
            p2.sendData(data);
        } else if (data.player == 2) {
            p1.sendData(data);
        }
    }
    private void endGame(Data data) {
    }
}
