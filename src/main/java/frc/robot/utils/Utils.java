package frc.robot.utils;

public class Utils {
    public static double deadband(double in, double minMagnitude, double maxMagnitude) {
        if (Math.abs(in) > maxMagnitude) {
            return Math.signum(in) * maxMagnitude;
        } else if (Math.abs(in) < minMagnitude) {
            return 0.0;
        } else {
            return in;
        }
    }

    public static double deadband(double in, double minMagnitude) {
        return deadband(in, minMagnitude, 1.0);
    }
}
