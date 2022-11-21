package server;

import data.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private final Socket socket;
    private final Game game;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int player;
    Data data = new Data();

    protected ServerConnection(Socket socket, Game game,int player) {
        this.socket = socket;
        this.game = game;
        this.player = player;
        this.data.player = player;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            sendData(data);

            while (true) {
                Data inData = (Data) in.readObject();
                game.protocol(inData);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendData(Data outData) {
        try {
            out.writeObject(outData);
            out.flush();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
