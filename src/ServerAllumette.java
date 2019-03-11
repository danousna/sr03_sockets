import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.floor;

public class ServerAllumette extends Server {
    private int result;

    public ServerAllumette() {
        super();
    }

    public static void main(String[] args) {
        ServerRestaurant server = new ServerRestaurant();
        server.run();
    }

    public void read() {
        try {
            DataInputStream ins = new DataInputStream(this.comm.getInputStream());
            String[] data = ins.readUTF().split(",");
            int[] args = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
            if (args[0] == 0) {
                this.comm.close();
                this.conn.close();
                return;
            }
            System.out.println("client à envoyé :" + args[0] + "," + args[1]);
            result = this.jeu_ordi(args[0], args[1]);
            this.send();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture");
        }
    }

    public void send() {
        try {
            DataOutputStream outs = new DataOutputStream(this.comm.getOutputStream());
            outs.writeInt(result);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi");
        }
    }

    private int jeu_ordi(int nb_allum, int prise_max) {
        int prise = 0;
        int s = 0;
        float t = 0;

        s = prise_max + 1;
        t = ((float) (nb_allum - s)) / (prise_max + 1);

        while (t != floor(t)) {
            s--;
            t = ((float) (nb_allum-s)) / (prise_max + 1);
        }

        prise = s - 1;
        if (prise == 0) {
            prise = 1;
        }
        return (prise);
    }
}
