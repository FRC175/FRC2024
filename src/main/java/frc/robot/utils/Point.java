package frc.robot.utils;

public class Point {
    private Vector position;
    private double angle;
    private double speed;

    public Point(Vector position, double angle, double speed) {
        this.position = position;
        this.angle = angle;
        this.speed = speed;
    }

    public Vector getPosition() {
        return position;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    
}
