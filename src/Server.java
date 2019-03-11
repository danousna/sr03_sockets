import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

abstract class Server {
    protected ServerSocket conn;
    protected Socket comm;

    public Server(int port) {
        try {
            this.conn = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void run() {
        try {
            comm = conn.accept();
            System.out.println("Connexion acceptée");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while(!comm.isClosed()) {
            this.read();
        }
    }

    abstract void read();
    abstract void send();
}
