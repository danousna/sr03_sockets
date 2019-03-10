
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRestaurant {
    public Socket comm;

    public static void main(String[] args) {
        ClientRestaurant client = new ClientRestaurant();
        Restaurant[] restaurantRecu;
        Point2D position = new Point2D(13, 7);
        client.connect();
        client.send(position);
        restaurantRecu = client.read();
        for (int i = 0; i < restaurantRecu.length; ++i)
            System.out.println("Resto " + (i+1) + " : " + restaurantRecu[i].getnom() + " avec une distance de " + Math.floor(restaurantRecu[i].getpos().distance(position)*100)/100 + " carrés. Téléphone : " + restaurantRecu[i].gettel());
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

    public Restaurant[] read() {
        try {
            ObjectInputStream ins = new ObjectInputStream(this.comm.getInputStream());
            return (Restaurant[])ins.readObject();
        } catch(IOException e) {
            System.out.println("Erreur lors de la lecture");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}


