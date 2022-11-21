package data;

import java.io.Serializable;

public class Data implements Serializable {

    private int player;
    // data.Data object to send between Server side (ConnectionHandler) and Client.
    // Remember that this class have to be identical for client and server.


    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
