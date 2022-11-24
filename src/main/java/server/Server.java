package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class Server implements Runnable {
    Properties p = new Properties();

    @Override
    public void run() {
        int port = 5000;

        try {
            p.load(new FileInputStream("src/main/resources/server/Settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ServerSocket sSocket = new ServerSocket(port)) {
            while (true) {
                Game game = new Game(p);

                ServerConnection p1 = new ServerConnection(sSocket.accept(), game, 1);
                ServerConnection p2 = new ServerConnection(sSocket.accept(), game, 2);

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
