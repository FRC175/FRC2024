package frc.robot.utils;

import com.revrobotics.CANSparkMax;

public final class SwerveModule {

    // Spark Maxes
    private final CANSparkMax throttling, turning;

    /**
     * Constructs a new DriveHelper.
     *
     * @param throttling  The master Spark Max for the left drive motors
     * @param turning The master Spark Max for the right drive motors
     */
    public SwerveModule(CANSparkMax throttling, CANSparkMax turning) {
        this.throttling = throttling;
        this.turning = turning;
    }

    /**
     * Swerve drive
     */
    public void swerveDrive(double turn, double throttle) {
        double throttleOut = throttle;
        double turningOut = turn;
        turning.set(turningOut);
        throttling.set(throttleOut);
    }
    public void lockMode(double turn, double throttle) {
        double throttleOut = throttle;
        double turningOut = turn;
        turning.set(turningOut);
        throttling.set(throttleOut);
    }
    public void rotModeOnly(double turn, double throttle) {
        double throttleOut = throttle;
        double turningOut = turn;
        turning.set(turningOut);
        throttling.set(throttleOut);
    }

   
}
