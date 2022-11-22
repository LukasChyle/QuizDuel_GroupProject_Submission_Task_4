package server;

import data.Data;
import data.Tasks;

import java.util.ArrayList;
import java.util.List;

public class Game {
private final CategoryHandler c = new CategoryHandler();

    private ServerConnection p1, p2;

    protected void setPlayers(ServerConnection p1, ServerConnection p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    protected void protocol(Data data) { // income data from Client
        System.out.println("Data came to server"); // test

        switch (data.task) {
            case PICK_CATEGORY -> setCategory(data);
            case SET_SCORE ->  setScore(data);
            case PLAYER_INFO -> setPlayer(data);
            case FINNISH -> endGame(data);
        }

        data.categoriesToChoose = new String[] {"Sport", "Vetenskap", "Godissorter"}; // test
        p1.sendData(data); // test
        p2.sendData(data); // test
    }

    private void setCategory(Data data) {
        List<String[]> ar = c.getQuestions(data.message);
        Data data1 = new Data();
        data1.task = Tasks.ROUND;
        data1.questions = ar;

    }

    private void setScore(Data data) {
    }

    private void setPlayer(Data data) {
    }

    private void endGame(Data data) {
    }

}
