import java.io.*;
import java.net.Socket;

abstract class Client {
    protected static Socket comm;

    public Client(int port) {
        try {
            comm = new Socket("localhost", port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    abstract void read();
    abstract void send();
}
