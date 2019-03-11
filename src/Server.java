import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

abstract class Server {
    protected ServerSocket conn;
    protected Socket comm;

    public Server() {
        try {
            this.conn = new ServerSocket(10080);
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du serveur");
        }
    }

    protected void run() {
        try {
            this.comm = this.conn.accept();
            System.out.println("connexion acceptée");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'acceptation");
        }
        while(!this.comm.isClosed()) {
            this.read();
        }
    }

    abstract void read();
    abstract void send();
}
