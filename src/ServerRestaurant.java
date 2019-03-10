
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public static void main(String[] args) {
        ServerRestaurant server = new ServerRestaurant();
        server.run();
    }

    public void read() {
        try {
            ObjectInputStream ins = new ObjectInputStream(this.comm.getInputStream());
            Point2D pointClient = (Point2D)ins.readObject();
            Restaurant closestResto = closestResto(pointClient);
            this.send(closestResto);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send(Restaurant closest) {
        try {
            ObjectOutputStream outs = new ObjectOutputStream(this.comm.getOutputStream());
            outs.writeObject(closest);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            this.comm = this.conn.accept();
            System.out.println("connexion acceptée");
            this.read();
            this.comm.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'acceptation");
        }
    }

    public Restaurant closestResto(Point2D position) {
        if (restaurants.length > 0) {
            Restaurant closestResto = restaurants[0];
            double shortestDistance = closestResto.getpos().distance(position), tempDistance;
            for (int i = 1; i < restaurants.length; ++i) {
                tempDistance = restaurants[i].getpos().distance(position);
                if (tempDistance < shortestDistance) {
                    shortestDistance = tempDistance;
                    closestResto = restaurants[i];
                }
            }
            return closestResto;
        }
        return null;
    }
}

