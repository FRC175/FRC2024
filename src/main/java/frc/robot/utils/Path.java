package frc.robot.utils;

public class Path {
    private Point[] points;
    private Point start;

    public Path(Point start, Point[] points) {
        this.start = start;
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    public Point getStart() {
        return start;
    }
}
