
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRestaurant {
    public Socket comm;

    public static void main(String[] args) {
        ClientRestaurant client = new ClientRestaurant();
        Restaurant restaurantRecu;
        Point2D position = new Point2D(5, 3);
        client.connect();
        client.send(position);
        restaurantRecu = client.read();
        System.out.println("Le resto le plus proche est : " + restaurantRecu.getnom() + " avec une distance de " + restaurantRecu.getpos().distance(position) + " carrés");
    }

    public void connect() {
        try {
            this.comm = new Socket("localhost", 10080);
        } catch (IOException e) {
            System.out.println("Erreur, hôte inconnu");
        }
    }

    public void send(Point2D point) {
        try {
            ObjectOutputStream outs = new ObjectOutputStream(this.comm.getOutputStream());
            outs.writeObject(point);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
        }
    }

    public Restaurant read() {
        try {
            ObjectInputStream ins = new ObjectInputStream(this.comm.getInputStream());
            return (Restaurant)ins.readObject();
        } catch(IOException e) {
            System.out.println("Erreur lors de la lecture");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}


