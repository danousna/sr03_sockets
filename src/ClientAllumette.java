import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientAllumette extends Client {
    public int nb_allu_rest;
    public int nb_allu_max;
    public int prise;

    public ClientAllumette(int port) {
        super(port);
    }

    public static void main(String[] args) {
        ClientAllumette client = new ClientAllumette(10080);

        int nb_max_d=0; /*nbre d'allumettes maxi au départ*/
        int qui=0; /*qui joue? 0=Nous --- 1=PC*/

        client.nb_allu_max = 0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
        client.nb_allu_rest = 0; /*nbre d'allumettes restantes*/
        client.prise = 0; /*nbre d'allumettes prises par le joueur*/

        Scanner sc=new Scanner(System.in);
        do{
            System.out.println ("Nombre d'allumettes disposées entre les deux joueurs (entre 10 et 60) :");
            nb_max_d=sc.nextInt();
        }
        while((nb_max_d<10) || (nb_max_d>60));
        do
        {
            System.out.println ("\nNombre maximal d'allumettes que l'on peut retirer : ");
            client.nb_allu_max=sc.nextInt();
            if (client.nb_allu_max >= nb_max_d)
                System.out.println ("Erreur !");
        }

        while ((client.nb_allu_max >= nb_max_d)||(client.nb_allu_max == 0));
        /* On répète la demande de prise tant que le nombre donné n'est pas correct */
        do
        {
            System.out.println ("\nQuel joueur commence? 0--> Joueur ; 1--> Ordinateur : ");
            qui=sc.nextInt();

            if ((qui != 0) && (qui != 1))
                System.out.println ("\nErreur");
        }
        while ((qui != 0) && (qui != 1));

        client.nb_allu_rest = nb_max_d;

        do
        {
            System.out.println ("\nNombre d'allumettes restantes :"+client.nb_allu_rest);
            afficher_allu(client.nb_allu_rest);
            if (qui==0)
            {
                do
                {
                    System.out.println ("\nCombien d'allumettes souhaitez-vous piocher ? ");
                    client.prise=sc.nextInt();
                    if ((client.prise > client.nb_allu_rest) || (client.prise > client.nb_allu_max))
                    {
                        System.out.println ("Erreur !\n");
                    }
                }
                while ((client.prise > client.nb_allu_rest) || (client.prise > client.nb_allu_max));
                /* On répète la demande de prise tant que le nombre donné n'est pas correct */
            }
            else
            {
                client.send();
                client.read();
                System.out.println ("\nPrise de l'ordi :"+client.prise);
            }
            qui=(qui+1)%2;

            client.nb_allu_rest= client.nb_allu_rest - client.prise;
        }
        while (client.nb_allu_rest >0);

        if (qui == 0) /* Cest à nous de jouer */
            System.out.println("\nVous avez gagné!\n");
        else
            System.out.println("\nVous avez perdu!\n");

        client.send();

        try {
            comm.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de la fermeture du socket de communication : " + e.getMessage());
        }
    }

    public void read() {
        try {
            DataInputStream ins = new DataInputStream(comm.getInputStream());
            prise = ins.readInt();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture : " + e.getMessage());
        }
    }

    public void send() {
        try {
            DataOutputStream outs = new DataOutputStream(comm.getOutputStream());
            outs.writeUTF(nb_allu_rest + "," + nb_allu_max);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi : " + e.getMessage());
        }
    }

    private static void afficher_allu(int n) {
        int i;
        System.out.print("\n");
        for (i=0; i<n ;i++)
            System.out.print("  o");
        System.out.print("\n");
        for (i=0; i<n; i++)
            System.out.print("  |");
        System.out.print("\n");
        for (i=0; i<n; i++)
            System.out.print("  |");
        System.out.print("\n");

    }
}
