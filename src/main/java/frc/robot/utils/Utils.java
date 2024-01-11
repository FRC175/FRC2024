package frc.robot.utils;

public class Utils {
    public static double deadband(double in, double radius) {
        return Math.abs(in) < radius ? 0 : in;
    }
}
