public class Circle extends GeometricObject implements Comparable<Circle> {
    private double radius;

    public Circle() {
    }

    public Circle(double radius) {
        this.radius = radius;
    }


    public double getRadius() {
        return radius;
    }


    public void setRadius(double radius) {
        this.radius = radius;
    }


    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }


    public double getDiameter() {
        return 2 * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * radius * Math.PI;
    }


    public void printCircle() {
        System.out.println("The circle is created " + getDateCreated()
            + " and the radius is " + radius);
    }

    @Override
    public int compareTo(Circle other) {
        if (this.radius > other.radius)
            return 1;
        else if (this.radius < other.radius)
            return -1;
        else
            return 0;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;           
        if (!(obj instanceof Circle)) return false;    
        Circle other = (Circle) obj;
        return this.radius == other.radius;
    }
}
