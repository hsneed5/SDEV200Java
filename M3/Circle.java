public class Circle extends GeometricObject implements Comparable<Circle> {
    private double radius;

    public Circle() {
    }

    public Circle(double radius) {
        this.radius = radius;
    }

    /** Return radius */
    public double getRadius() {
        return radius;
    }

    /** Set a new radius */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /** Return area */
    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }

    /** Return diameter */
    public double getDiameter() {
        return 2 * radius;
    }

    /** Return perimeter */
    @Override
    public double getPerimeter() {
        return 2 * radius * Math.PI;
    }

    /** Print the circle info */
    public void printCircle() {
        System.out.println("The circle is created " + getDateCreated()
            + " and the radius is " + radius);
    }

    /** Comparable interface — compare by radius */
    @Override
    public int compareTo(Circle other) {
        if (this.radius > other.radius)
            return 1;
        else if (this.radius < other.radius)
            return -1;
        else
            return 0;
    }

    /** Two circles are equal if their radii are equal */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                   // same object
        if (!(obj instanceof Circle)) return false;    // must be Circle
        Circle other = (Circle) obj;
        return this.radius == other.radius;
    }
}
