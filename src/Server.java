import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import static java.lang.Math.floor;

public class Server {
    private ServerSocket conn;
    private Socket comm;

    public Server() {
        try {
            this.conn = new ServerSocket(10080);
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du serveur");
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

//    public void run() {
//        while(true) {
//            try {
//                this.comm = this.conn.accept();
//                System.out.println("connexion acceptée");
//                this.read();
//                this.comm.close();
//            } catch (IOException e) {
//                System.out.println("Erreur lors de l'acceptation");
//            }
//        }
//    }

    public void read() {
        try {
            DataInputStream ins = new DataInputStream(this.comm.getInputStream());
            String[] data = ins.readUTF().split(",");
            int[] args = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
            System.out.println("client à envoyé :" + args[0] + "," + args[1]);
            int result  = this.jeu_ordi(args[0], args[1]);
            this.send(result);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture");
        }
    }

    public void send(int result) {
        try {
            DataOutputStream outs = new DataOutputStream(this.comm.getOutputStream());
            outs.writeInt(result);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
        }
    }

    public int jeu_ordi(int nb_allum, int prise_max) {
        int prise = 0;
        int s = 0;
        float t = 0;
        s = prise_max + 1;
        t = ((float) (nb_allum - s)) / (prise_max + 1);
        while (t != floor(t))
        {
            s--;
            t = ((float) (nb_allum-s)) / (prise_max + 1);
        }
        prise = s - 1;
        if (prise == 0)
            prise = 1;
        return (prise);
    }
}
