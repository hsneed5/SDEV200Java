public class RegularPolygon {
    // Private data fields
    private int n;
    private double side;
    private double x;
    private double y;

    // No-arg constructor
    public RegularPolygon() {
        this(3, 1, 0, 0); // default values
    }

    // Constructor with n and side
    public RegularPolygon(int n, double side) {
        this(n, side, 0, 0);
    }

    // Constructor with n, side, x, y
    public RegularPolygon(int n, double side, double x, double y) {
        this.n = n;
        this.side = side;
        this.x = x;
        this.y = y;
    }

    // Accessors and mutators
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Method to get perimeter
    public double getPerimeter() {
        return n * side;
    }

    // Method to get area
    public double getArea() {
        return (n * side * side) / (4 * Math.tan(Math.PI / n));
    }
}
