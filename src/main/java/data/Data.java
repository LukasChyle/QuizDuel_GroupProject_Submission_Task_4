package data;

import java.io.Serializable;

public class Data implements Serializable {

    public int player;
    public int avatar;
    public String nickname;
    // data.Data object to send between Server side (ConnectionHandler) and Client.
    // Remember that this class have to be identical for client and server.

}
