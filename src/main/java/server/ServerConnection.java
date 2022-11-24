package server;

import data.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Tasks;

public class ServerConnection implements Runnable {

    private final Socket socket;
    private final Game game;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final int player;

    protected ServerConnection(Socket socket, Game game, int player) {
        this.socket = socket;
        this.game = game;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Data data = new Data();
            data.task = Tasks.SET_PLAYER;
            data.player = player;
            sendData(data);

            while (true) {
                Data inData = (Data) in.readObject();
                System.out.println("(Sever) received " + inData.task + " from client " + player);
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
            disconnected();
        }
    }

    private void disconnected() {
        Data data = new Data();
        data.task = Tasks.LEFT_GAME;
        data.player = player;
        game.protocol(data);
    }

    public void sendData(Data outData) {
        try {
            System.out.println("(Sever) tries to send " + outData.task + " to client " + player);
            out.writeObject(outData);
            out.flush();
            out.reset();
            System.out.println("(Sever) did send " + outData.task + " to client " + player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
