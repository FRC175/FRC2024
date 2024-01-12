package frc.robot.utils;

public class Vector {
    private double x;
    private double y;
    private double m;
    private double theta;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.m = Math.sqrt(x * x + y * y);
        this.theta = (Math.toDegrees(Math.atan2(y, x))+360) % 360;
    }

    public void rotate(double angle) {
        theta = ((theta + angle) + 360) % 360;
        x = getMagnitude() * Math.cos(getAngleRadians());
        y = getMagnitude() * Math.sin(getAngleRadians());
    }

    public void normalize(double val) {
        m = m / val;
        x = m * Math.cos(getAngleRadians());
        y = m * Math.sin(getAngleRadians());
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public void setAngle(double angle) {
        theta = angle;
        x = m * Math.cos(getAngleRadians());
        y = m * Math.sin(getAngleRadians());
    }

    public double getAngle() {
        return theta;
    }

    public double getXComponent() {
        return x;
    }

    public double getYComponent() {
        return y;
    }

    public double getMagnitude() {
        return m;
    }

    public double getAngleRadians() {
        return Math.toRadians(theta);
    }

}
