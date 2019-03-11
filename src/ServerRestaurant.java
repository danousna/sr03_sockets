import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerRestaurant extends Server {
    private Restaurant[] restaurants;
    private Restaurant[] closest_restaurants;

    public ServerRestaurant(int port) {
        super(port);

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
    }

    public static void main(String[] args) {
        ServerRestaurant server = new ServerRestaurant(10080);
        server.run();
    }

    public void read() {
        try {
            ObjectInputStream ins = new ObjectInputStream(this.comm.getInputStream());
            Point2D position = (Point2D)ins.readObject();

            if (position == null) {
               comm.close();
               conn.close();
               return;
            }

            closest_restaurants = closestRestos(position);
            this.send();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            ObjectOutputStream outs = new ObjectOutputStream(this.comm.getOutputStream());
            outs.writeObject(closest_restaurants);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
            e.printStackTrace();
        }
    }

    private Restaurant[] closestRestos(Point2D position) {
        if (restaurants.length > 0) {
            Restaurant[] closestResto = {restaurants[0], null, null};
            double tempDistance;
            Restaurant tempResto;

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
            return closestResto;
        }
        return null;
    }
}

