import java.io.Serializable;

public class Restaurant implements Serializable {
    private String nom;
    private String tel;
    private Point2D pos;

    public Restaurant(String nom, String tel, Point2D pos) {
        this.nom = nom;
        this.tel = tel;
        this.pos = pos;
    }

    public void print() {
        System.out.println(this.nom);
        this.pos.print();
    }
}
