---------------------------
|     GeometricObject     |
---------------------------
| - color : String        |
| - filled : boolean      |
| - dateCreated : Date    |
---------------------------
| + getArea() : double    |
| + getPerimeter() : double|
| + getColor() : String   |
| + isFilled() : boolean  |
| + setColor(String)      |
| + setFilled(boolean)    |
---------------------------

              ▲
              |
              |
-----------------------------------------
|                 Circle                 |
-----------------------------------------
| - radius : double                      |
-----------------------------------------
| + Circle()                             |
| + Circle(radius: double)               |
| + getRadius() : double                 |
| + setRadius(double)                    |
| + getArea() : double                   |
| + getPerimeter() : double              |
| + getDiameter() : double               |
| + printCircle()                        |
| + compareTo(Circle) : int              |
| + equals(Object) : boolean             |
-----------------------------------------
              ▲
              |
              |
-----------------------------------------
|           Comparable<Circle>          |
-----------------------------------------
| + compareTo(Circle) : int             |
-----------------------------------------
