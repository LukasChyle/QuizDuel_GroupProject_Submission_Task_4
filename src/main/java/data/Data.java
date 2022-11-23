package data;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    public Tasks task;
    public int player;
    public int opponentAvatar;
    public String opponentNickname;
    public String message;
    public String[] categoriesToChoose;
    public List<String[]> questions;
    public Boolean[] roundScore;
    public List<Boolean[]> playerOneScore;
    public List<Boolean[]> playerTwoScore;
    public boolean lastRound;
}
