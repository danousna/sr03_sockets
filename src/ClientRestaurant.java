import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientRestaurant {
    private Socket comm;

    public static void main(String[] args) {
        ClientRestaurant client = new ClientRestaurant();
        Scanner scanner = new Scanner(System.in);
        int continue_program = 1;
        int pos_x;
        int pos_y;

        while (continue_program == 1) {
            System.out.println("Quel est votre position ?\n");
            System.out.println("x : ");
            pos_x = scanner.nextInt();
            System.out.println("y : ");
            pos_y = scanner.nextInt();

            Restaurant[] restaurantRecu;
            Point2D position = new Point2D(pos_x, pos_y);

            client.connect();
            client.send(position);

            restaurantRecu = client.read();

            System.out.printf("Voici les 3 restaurants les plus proches de votre position (%d, %d) : \n", pos_x, pos_y);
            if (restaurantRecu != null) {
                for (int i = 0; i < restaurantRecu.length; ++i)
                    System.out.println("Resto " + (i+1) + " : " + restaurantRecu[i].getnom() + " avec une distance de " + Math.floor(restaurantRecu[i].getpos().distance(position)*100)/100 + " carrés. Téléphone : " + restaurantRecu[i].gettel());
            } else {
                System.out.println("Erreur, le message reçu n'est pas valide.\n");
            }

            System.out.println("Réessayer ? (1 : Oui, 0 : Non");
            continue_program = scanner.nextInt();
        }
    }

    private void connect() {
        try {
            this.comm = new Socket("localhost", 10080);
        } catch (IOException e) {
            System.out.println("Erreur, hôte inconnu");
        }
    }

    private void send(Point2D point) {
        try {
            ObjectOutputStream outs = new ObjectOutputStream(this.comm.getOutputStream());
            outs.writeObject(point);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
        }
    }

    private Restaurant[] read() {
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


