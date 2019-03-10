
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
        this.restaurants = new Restaurant[10];
        this.restaurants[0] = new Restaurant("Le Premier", "040508", new Point2D(1, 1));
        this.restaurants[1] = new Restaurant("Le Deuxième", "068709", new Point2D(2, 7));
        this.restaurants[2] = new Restaurant("Le Troisième", "923229", new Point2D(10, 1));
        this.restaurants[3] = new Restaurant("Le Quatrième", "253678", new Point2D(3, 3));
        this.restaurants[4] = new Restaurant("Le Cinquième", "102475", new Point2D(6, 9));
        this.restaurants[5] = new Restaurant("Le Sixième", "035620", new Point2D(5, 1));
        this.restaurants[6] = new Restaurant("Le Septième", "012253", new Point2D(4, 8));
        this.restaurants[7] = new Restaurant("Le Huitième", "098567", new Point2D(2, 0));
        this.restaurants[8] = new Restaurant("Le Neuvième", "025885", new Point2D(7, 3));
        this.restaurants[9] = new Restaurant("Le Dixième", "065426", new Point2D(6, 6));

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
            Restaurant[] closestRestos = closestRestos(pointClient);
            this.send(closestRestos);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send(Restaurant[] closest) {
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

    public Restaurant[] closestRestos(Point2D position) {
        if (restaurants.length > 0) {
            Restaurant[] closestResto = {restaurants[0], null, null};
            double tempDistance;
            Restaurant tempResto = null;
            for (int i = 1; i < restaurants.length; ++i) {
                tempDistance = restaurants[i].getpos().distance(position);
                for (int j = closestResto.length - 1; j >= 0; --j) {
                    if (j == closestResto.length - 1) { // faire rentrer un resto dans le top 3
                        if (closestResto[j] == null || tempDistance < closestResto[j].getpos().distance(position)) {
                            closestResto[j] = restaurants[i];
                        }
                    }
                    else { // trier le top 3
                        if (closestResto[j] == null || closestResto[j].getpos().distance(position) > closestResto[j+1].getpos().distance(position)) {
                            tempResto = closestResto[j];
                            closestResto[j] = closestResto[j+1];
                            closestResto[j+1] = tempResto;
                            tempResto = null;
                        }
                    }
                }
            }
//            double shortestDistance = closestResto.getpos().distance(position), tempDistance;
//            for (int i = 1; i < restaurants.length; ++i) {
//                tempDistance = restaurants[i].getpos().distance(position);
//                if (tempDistance < shortestDistance) {
//                    shortestDistance = tempDistance;
//                    closestResto = restaurants[i];
//                }
//            }
            return closestResto;
        }
        return null;
    }
}

