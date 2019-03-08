import java.io.Serializable;

public class Point2D implements Serializable {
    public double x;
    public double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] get() {
        double[] result = new double[2];
        result[0] = x;
        result[1] = y;
        return(result);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point2D p) {
        double[] point = p.get();
        return(Math.sqrt(Math.pow(point[0] - this.x, 2) + Math.pow(point[1] - this.y, 2)));
    }

    public void print() {
        System.out.println(x + ", " + y);
    }
}
