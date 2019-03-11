import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.floor;

public class ServerAllumette extends Server {
    private int result;

    public ServerAllumette(int port) {
        super(port);
    }

    public static void main(String[] args) {
        ServerAllumette server = new ServerAllumette(10080);
        server.run();
    }

    public void read() {
        try {
            DataInputStream ins = new DataInputStream(this.comm.getInputStream());
            // Parse data format ([String], [String])
            String[] data = ins.readUTF().split(",");
            // Convert array items to int.
            int[] args = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();

            // If nb_allu_rest == 0, then the game is finished.
            if (args[0] == 0) {
                comm.close();
                conn.close();
                return;
            }

            System.out.println("Le client à envoyé :" + args[0] + "," + args[1]);
            result = jeu_ordi(args[0], args[1]);

            send();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void send() {
        try {
            DataOutputStream outs = new DataOutputStream(comm.getOutputStream());
            outs.writeInt(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
