package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection implements Runnable {

    private ObjectOutputStream out;
    private InetAddress host;
    private final DataHandler dataHandler;

    protected ClientConnection(String nickname, int avatar) {
        dataHandler = new DataHandler(nickname, avatar, this);
    }

    protected DataHandler getDataHandler(){
        return dataHandler;
    }

    protected void sendData(Data outData) {
        if (out != null) {
            try {
                out.writeObject(outData);
                out.flush();
                out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        int port = 5000;

        try {
            host = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (Socket socket = new Socket(host, port);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                Data inData = (Data) in.readObject();
                System.out.println("Data came to client"); // test
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
