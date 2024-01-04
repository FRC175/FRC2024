package frc.robot.subsystems.Drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public final class Drive implements Subsystem {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    SwerveModule frontRight, frontLeft, backRight, backLeft;
    
    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Drive instance;
    
    private Drive() {
        // leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontRight = new SwerveModule(DriveConstants.frontRightDrive, DriveConstants.frontRightRot, DriveConstants.frontRightEncoder);
        frontLeft = new SwerveModule(DriveConstants.frontLeftDrive, DriveConstants.frontLeftRot, DriveConstants.frontLeftEncoder);
        backRight = new SwerveModule(DriveConstants.backRightDrive, DriveConstants.backRightRot, DriveConstants.backRightEncoder);
        backLeft = new SwerveModule(DriveConstants.backLeftDrive, DriveConstants.backLeftRot, DriveConstants.backLeftEncoder);
        configureSparks();
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
     * @return The single instance of {@link Drive}
     */
    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    /**
     * Helper method that configures the Spark Max motor controllers.
     */
    private void configureSparks() {

        
    }

    /**
     * Sets the drive motors to a certain percent output (demand) using open loop control (no sensors in feedback loop).
     *
     * @param leftDemand The percent output for the left drive motors
     * @param rightDemand The percent output for the right drive motors
     */
    public void setOpenLoop(double drive, double turn) {
        frontRight.drive(drive);
        frontLeft.drive(drive);
        backRight.drive(drive);
        backLeft.drive(drive);

        frontRight.turn(turn);
        frontLeft.turn(turn);
        backRight.turn(turn);
        backLeft.turn(turn);
    }

}
