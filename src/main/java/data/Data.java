package data;

import java.io.Serializable;

public class Data implements Serializable {

    public Tasks task;
    public int player;
    public int opponentAvatar;
    public String opponentNickname;
    public String message;
    public String[] categoriesToChoose;
}
