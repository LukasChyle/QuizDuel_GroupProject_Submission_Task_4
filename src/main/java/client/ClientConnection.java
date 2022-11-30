package client;

import data.Data;
import data.Tasks;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class ClientConnection implements Runnable {

    private ObjectOutputStream out;
    private InetAddress host;
    private final DataHandler dataHandler;
    private final Properties p;

    protected ClientConnection(String nickname, int avatar) {
        dataHandler = new DataHandler(nickname, avatar, this);
        p = new Properties();
        try {
            p.load(new FileInputStream("src/main/resources/client/properties/Settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected DataHandler getDataHandler() {
        return dataHandler;
    }

    protected void sendData(Data outData) {
        if (out != null) {
            try {
                System.out.println("Client " + dataHandler.player + " tries to send " + outData.task + " to server");
                out.writeObject(outData);
                out.flush();
                out.reset();
                System.out.println("Client " + dataHandler.player + " did send " + outData.task + " to server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        int port = 5000;

        try {
            host = InetAddress.getByName(p.getProperty("adress","127.0.0.1"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (Socket socket = new Socket(host, port);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out = new ObjectOutputStream(socket.getOutputStream());

            boolean stayConnected = true;
            while (stayConnected) {
                Data inData = (Data) in.readObject();
                System.out.println("Client " + dataHandler.player + " received " + inData.task + " from server");
                if (inData.task == Tasks.SET_SCORE && inData.lastRound) {
                    stayConnected = false;
                }
                dataHandler.readData(inData);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
