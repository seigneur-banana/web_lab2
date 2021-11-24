package other;

import java.util.Date;

public class Point {
    double x;
    double y;
    double r;
    boolean isInArea;
    Date date;

    public Point(double x, double y, double r, boolean isInArea) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isInArea = isInArea;
        this.date = new Date();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isInArea() {
        return isInArea;
    }

    public Date getDate() {
        return date;
    }
}