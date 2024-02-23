package frc.robot.subsystems.Drive;

import frc.robot.Constants.DriveConstants;
import frc.robot.utils.Vector;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public final class Drive implements Subsystem {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    SwerveModule frontRight, frontLeft, backRight, backLeft;
    double lastValidAngle;
    
    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Drive instance;
    
    private Drive() {
        // leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        frontRight = new SwerveModule(DriveConstants.frontRightDrive, DriveConstants.frontRightRot, DriveConstants.frontRightEncoder, DriveConstants.frontRightTurnAngle, DriveConstants.frontRightBaseAngle);
        frontLeft = new SwerveModule(DriveConstants.frontLeftDrive, DriveConstants.frontLeftRot, DriveConstants.frontLeftEncoder, DriveConstants.frontLeftTurnAngle, DriveConstants.frontLeftBaseAngle);
        backRight = new SwerveModule(DriveConstants.backRightDrive, DriveConstants.backRightRot, DriveConstants.backRightEncoder, DriveConstants.backRightTurnAngle, DriveConstants.backRightBaseAngle);
        backLeft = new SwerveModule(DriveConstants.backLeftDrive, DriveConstants.backLeftRot, DriveConstants.backLeftEncoder, DriveConstants.backLeftTurnAngle, DriveConstants.backLeftBaseAngle);
        configureSparks();

        lastValidAngle = 0;
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
        frontRight.driveOpenLoop(drive);
        frontLeft.driveOpenLoop(drive);
        backRight.driveOpenLoop(drive);
        backLeft.driveOpenLoop(drive);

        frontRight.turnOpenLoop(turn);
        frontLeft.turnOpenLoop(turn);
        backRight.turnOpenLoop(turn);
        backLeft.turnOpenLoop(turn);
    }

    public void postEncoders() {
        SmartDashboard.putNumber("FrontRightD", frontRight.getAngle());
        SmartDashboard.putNumber("FrontLeftD", frontLeft.getAngle());
        SmartDashboard.putNumber("BackRightD", backRight.getAngle());
        SmartDashboard.putNumber("BackLeftD", backLeft.getAngle());

        SmartDashboard.putNumber("FrontRightP", frontRight.getEncoder());
        SmartDashboard.putNumber("FrontLeftP", frontLeft.getEncoder());
        SmartDashboard.putNumber("BackRightP", backRight.getEncoder());
        SmartDashboard.putNumber("BackLeftP", backLeft.getEncoder());
    }

    public void swerve(double joyX, double joyY, double twist, double gyroAngle) {
        Vector transversal = new Vector(joyX, joyY * -1);
        SmartDashboard.putNumber("Joystick Angle", transversal.getAngle());

        if (transversal.getMagnitude() < 0.001) {
            transversal.setAngle(lastValidAngle);
        } else {
            lastValidAngle = transversal.getAngle();
        }

        transversal.rotate(- 90 - gyroAngle);

        frontRight.calculateRawOutputs(transversal, frontRight.findControlledRotationVector(twist));
        frontLeft.calculateRawOutputs(transversal, frontLeft.findControlledRotationVector(twist));
        backRight.calculateRawOutputs(transversal, backRight.findControlledRotationVector(twist));
        backLeft.calculateRawOutputs(transversal, backLeft.findControlledRotationVector(twist));

        frontRight.setOutputs();
        frontLeft.setOutputs();
        backRight.setOutputs();
        backLeft.setOutputs();
    }

    public void lockSwerve(double joyX, double joyY, double lockAngle, double gyroAngle) {
        Vector transversal = new Vector(joyX, joyY * -1);
        SmartDashboard.putNumber("Joystick Angle", transversal.getAngle());

        if (transversal.getMagnitude() < 0.001) {
            transversal.setAngle(lastValidAngle);
        } else {
            lastValidAngle = transversal.getAngle();
        }

        transversal.rotate(- 90 - gyroAngle);

        frontRight.calculateRawOutputs(transversal, frontRight.findLockedRotationVector(lockAngle, gyroAngle));
        frontLeft.calculateRawOutputs(transversal, frontLeft.findLockedRotationVector(lockAngle, gyroAngle));
        backRight.calculateRawOutputs(transversal, backRight.findLockedRotationVector(lockAngle, gyroAngle));
        backLeft.calculateRawOutputs(transversal, backLeft.findLockedRotationVector(lockAngle, gyroAngle));

        frontRight.setOutputs();
        frontLeft.setOutputs();
        backRight.setOutputs();
        backLeft.setOutputs();
    }

    public void lock() {
        frontRight.lock();
        frontLeft.lock();
        backRight.lock();
        backLeft.lock();

        frontRight.setOutputs();
        frontLeft.setOutputs();
        backRight.setOutputs();
        backLeft.setOutputs();
    }

    public void turning(double fr, double fl, double br, double bl) {
        frontRight.turnOpenLoop(fr);
        frontLeft.turnOpenLoop(fl);
        backRight.turnOpenLoop(br);
        backLeft.turnOpenLoop(bl);
    }

    public double getFRPosition() {
        return frontRight.getDriveEncoder();
    }
    public double getFLPosition() {
        return frontLeft.getDriveEncoder();
    }
    public double getBRPosition() {
        return backRight.getDriveEncoder();
    }
    public double getBLPosition() {
        return backLeft.getDriveEncoder();
    }

   
}
