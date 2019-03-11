import java.io.*;
import java.net.Socket;

abstract class Client {
    protected static Socket comm;

    public Client(int port) {
        try {
            comm = new Socket("localhost", port);
        } catch (IOException e) {
            System.out.println("Erreur, hôte inconnu");
        }
    }

    abstract void read();
    abstract void send();
}
