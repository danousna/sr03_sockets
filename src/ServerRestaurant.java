import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRestaurant {
    private ServerSocket conn;
    private Socket comm;
    public Restaurant[] restaurants;

    public ServerRestaurant() {
        this.restaurants = new Restaurant[3];
        this.restaurants[0] = new Restaurant("Le Premier", "040508", new Point2D(1, 1));
        this.restaurants[1] = new Restaurant("Le Deuxième", "068709", new Point2D(2, 7));
        this.restaurants[2] = new Restaurant("Le Troisième", "923229", new Point2D(10, 1));

        try {
            this.conn = new ServerSocket(10080);
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du serveur");
        }
    }

    public void read() {
        ObjectInputStream ins = new ObjectInputStream(this.comm.getInputStream());
        System.out.println(ins.readObject());
    }

    public void run() {
        while(true) {
            try {
                this.comm = this.conn.accept();
                System.out.println("connexion acceptée");
                this.read();
                this.comm.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de l'acceptation");
            }
        }
    }

    public static void main(String[] args) {
        ServerRestaurant server = new ServerRestaurant();
        server.run();
    }
}
