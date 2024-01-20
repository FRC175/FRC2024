package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive.Drive;

public final class Gyro implements Subsystem {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private final Pigeon2 pigeon;
    
    /**
     * The single instance of {link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {link Drive::getInstance()}.
     */
    private static Gyro instance;
    
    private Gyro() {
        pigeon = new Pigeon2(DriveConstants.PIDGEON);
        
    }

    /**
     * <code>getInstance()</code> is a crucial part of the "singleton" design pattern. This pattern is used when there
     * should only be one instance of a class, which makes sense for Robot subsystems (after all, there is only one
     * drivetrain). The singleton pattern is implemented by making the constructor private, creating a static variable
     * of the type of the object called <code>instance</code>, and creating this method, <code>getInstance()</code>, to
     * return the instance when the class needs to be used.
     *
     * Usage:
     * <code>Drive drive = Drive.getInstance();</code>
     *
     * return The single instance of {link Drive}
     */
    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }

        return instance;
    }

    public double getYaw() {
        return ((pigeon.getYaw().getValue() % 360) + 360) % 360;
    }

    public void resetGyro() {
        pigeon.setYaw(0);
    }

    public double getAngleDegrees() {
        return -1;
    }

    public void postYaw() {
        SmartDashboard.putNumber("Yee-Yaw", getYaw());
    }


}
