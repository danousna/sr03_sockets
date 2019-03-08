public class Point3D extends Point2D {
    public double z;

    public Point3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public double[] get() {
        double[] result = new double[3];
        result[0] = x;
        result[1] = y;
        result[2] = z;
        return(result);
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distance(Point3D p) {
        double[] point = p.get();
        return(Math.sqrt(Math.pow(point[0] - this.x, 2) + Math.pow(point[1] - this.y, 2) + Math.pow(point[2] - this.z, 2)));
    }

    @Override
    public void print() {
        System.out.println(x + ", " + y + ", " + z);
    }
}
