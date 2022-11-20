package server;

import data.Data;

public class Game {

    private ServerConnection p1, p2;

    protected void setPlayers(ServerConnection p1, ServerConnection p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    protected void protocol(Data data) {
        p1.sendData(data); // test
        p2.sendData(data); // test
    }

}
