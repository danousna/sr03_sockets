import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientRestaurant extends Client {
    public Point2D position;
    public Restaurant[] restaurants_recu;

    public ClientRestaurant(int port) {
        super(port);
    }

    public static void main(String[] args) {
        ClientRestaurant client = new ClientRestaurant(10080);
        Scanner scanner = new Scanner(System.in);
        int continue_program = 1;
        int pos_x;
        int pos_y;

        while (continue_program == 1 && client.getcomm() != null) {
            System.out.println("Quel est votre position ?\n");
            System.out.println("x : ");
            pos_x = scanner.nextInt();
            System.out.println("y : ");
            pos_y = scanner.nextInt();

            client.position = new Point2D(pos_x, pos_y);
            client.send();
            client.read();

            System.out.printf("Voici les 3 restaurants les plus proches de votre position (%d, %d) : \n", pos_x, pos_y);
            if (client.restaurants_recu != null) {
                for (int i = 0; i < client.restaurants_recu.length; ++i) {
                    double distance = Math.floor(client.restaurants_recu[i].getpos().distance(client.position)*100)/100;
                    double x = client.restaurants_recu[i].getpos().x;
                    double y = client.restaurants_recu[i].getpos().y;
                    String nom = client.restaurants_recu[i].getnom();
                    System.out.printf("Resto "+(i+1)+" : "+nom+" (%1.2f, %1.2f) avec une distance de "+distance+" mètres. \n", x, y);
                }
            } else {
                System.out.println("Erreur, le message reçu n'est pas valide.\n");
            }

            System.out.println("Réessayer ? (1 : Oui, 0 : Non)");
            continue_program = scanner.nextInt();
        }

        // Close by sending null position
        client.position = null;
        if (client.getcomm() != null) {
            client.send();
        }
    }

    public void send() {
        try {
            ObjectOutputStream outs = new ObjectOutputStream(comm.getOutputStream());
            outs.writeObject(position);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
        }
    }

    public void read() {
        try {
            ObjectInputStream ins = new ObjectInputStream(comm.getInputStream());
            restaurants_recu = (Restaurant[])ins.readObject();
        } catch(IOException e) {
            System.out.println("Erreur lors de la lecture");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


