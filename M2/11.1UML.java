------------------------------------------
|           GeometricObject              |
------------------------------------------
| - color: String                        |
| - filled: boolean                      |
------------------------------------------
| + GeometricObject()                    |
| + GeometricObject(color: String,       |
|   filled: boolean)                     |
| + getColor(): String                   |
| + setColor(color: String): void        |
| + isFilled(): boolean                  |
| + setFilled(filled: boolean): void     |
| + getArea(): double {abstract}         |
| + getPerimeter(): double {abstract}    |
------------------------------------------

                   ▲
                   │
------------------------------------------
|                Triangle                |
------------------------------------------
| - side1: double                        |
| - side2: double                        |
| - side3: double                        |
------------------------------------------
| + Triangle()                           |
| + Triangle(side1: double,              |
|   side2: double, side3: double)        |
| + Triangle(side1: double,              |
|   side2: double, side3: double,        |
|   color: String, filled: boolean)      |
| + getSide1(): double                   |
| + getSide2(): double                   |
| + getSide3(): double                   |
| + getArea(): double                    |
| + getPerimeter(): double               |
| + toString(): String                   |
------------------------------------------
