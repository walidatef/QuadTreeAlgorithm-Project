
public class Point implements Comparable {

    private final double xCoordinate;
    private final double yCoordinate;
    private String name = "";

    public Point(double x, double y, String name) {
        xCoordinate = x;
        yCoordinate = y;
        this.name = name;


    }

    public Point(double x, double y) {
        xCoordinate = x;
        yCoordinate = y;
    }


    public double getX() {
        return xCoordinate;
    }


    public double getY() {
        return yCoordinate;
    }


    public String getName() {
        return name;
    }


    public String toString(int intX, int intY) {
        String temp = getName();

        if (!getName().isEmpty()) {
            temp += ", ";
        }


        if (intX == xCoordinate) {
            temp = temp + intX;
        } else {
            temp = temp + xCoordinate;
        }

        if (intY == yCoordinate) {
            temp = temp + ", " + intY;
        } else {
            temp = temp + ", " + yCoordinate;
        }

        return temp;
    }


    public boolean equals(Object other) {
        double checkX = ((Point) other).xCoordinate;
        double checkY = ((Point) other).yCoordinate;
        if (this.xCoordinate == checkX && this.yCoordinate == checkY &&
                this.name.compareTo(((Point) other).name) == 0)
            return true;
        else
            return false;

    }


    public int compareTo(Object o) {
        double checkX = ((Point) o).xCoordinate;
        double checkY = ((Point) o).yCoordinate;

        if (checkX == this.xCoordinate && checkY == this.yCoordinate)
            return 0;
        else
            return -1;
    }


}