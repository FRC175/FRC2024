package frc.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

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

    public static double convToSideAngle(double angle) {
        if (DriverStation.getAlliance() == Alliance.Red) return angle;
        else return 360 - angle;
    }
}
