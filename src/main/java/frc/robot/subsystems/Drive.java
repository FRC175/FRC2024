package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants.DriveConstants;

import edu.wpi.first.wpilibj2.command.Subsystem;

public final class Drive implements Subsystem {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private final CANSparkMax leftMaster, rightMaster;
    
    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Drive instance;
    
    private Drive() {
        leftMaster = new CANSparkMax(DriveConstants.LEFT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMaster = new CANSparkMax(DriveConstants.RIGHT_MASTER_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
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
        leftMaster.restoreFactoryDefaults();
        leftMaster.setInverted(false);

        rightMaster.restoreFactoryDefaults();
        rightMaster.setInverted(true);
    }

    /**
     * Sets the drive motors to a certain percent output (demand) using open loop control (no sensors in feedback loop).
     *
     * @param leftDemand The percent output for the left drive motors
     * @param rightDemand The percent output for the right drive motors
     */
    public void setOpenLoop(double leftDemand, double rightDemand) {
        leftMaster.set(leftDemand);
        rightMaster.set(rightDemand);
    }

    /**
     * Controls the drive motor using arcade controls - with a throttle and a turn.
     * 
     * @param throttle The throttle from the controller
     * @param turn The turn from the controller
     */
    public void arcadeDrive(double rightAxis, double leftAxis, double stickX) {
        double throttle = rightAxis - leftAxis;
        double turn = -1 * stickX;
        double leftOut = throttle - turn;
        double rightOut = throttle + turn;
        rightMaster.set(rightOut);
        leftMaster.set(leftOut);
    }

    public double getShortestTurnDelta(double currentAngle, double finalAngle) {
        return Math.abs(finalAngle-currentAngle) < Math.PI ? (finalAngle-currentAngle) : (finalAngle-currentAngle) - 2 * Math.PI * (finalAngle-currentAngle)/Math.abs(finalAngle-currentAngle);
    }

}
