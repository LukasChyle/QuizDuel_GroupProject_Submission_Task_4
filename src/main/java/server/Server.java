package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {

    @Override
    public void run() {
        int port = 5000;

        try (ServerSocket sSocket = new ServerSocket(port)) {
            while (true) {
                Game game = new Game();

                ServerConnection p1 = new ServerConnection(sSocket.accept(), game);
                ServerConnection p2 = new ServerConnection(sSocket.accept(), game);

                game.setPlayers(p1, p2);

                new Thread(p1).start();
                new Thread(p2).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }
}
