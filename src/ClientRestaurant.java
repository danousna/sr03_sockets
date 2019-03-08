import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRestaurant {
    public class Client {
    public Socket comm;

    public static void main(String[] args) {
        ClientRestaurant client = new ClientRestaurant();
        client.connect();
        client.send();
    }

    public void connect() {
        try {
            this.comm = new Socket("localhost", 10080);
        } catch (IOException e) {
            System.out.println("Erreur, hôte inconnu");
        }
    }

    public void send() {
        try {
            Point2D pos = new Point2D(3, 4);
            ObjectOutputStream outs = new ObjectOutputStream(this.comm.getOutputStream());
            outs.writeObject(pos);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
        }
    }
}
