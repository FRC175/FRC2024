package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.Constants.DriveConstants;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.utils.SwerveModule;

public final class SwerveDrive extends SubsystemBase {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private final CANSparkMax leftFrontTurn, leftFrontThrottle, rightFrontTurn, rightFrontThrottle, leftBackTurn, leftBackThrottle, rightBackTurn, rightBackThrottle;
    private final SwerveModule swerveModuleLeftFront, swerveModuleRightFront, swerveModuleLeftBack, swerveModuleRightBack;
    /**
     * The single instance of {@link SwerveDrive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link SwerveDrive::getInstance()}.
     */
    private static SwerveDrive instance;
    
    private SwerveDrive() {
        leftFrontTurn = new CANSparkMax(DriveConstants.LEFT_TURN_PORT_FRONT, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftFrontThrottle = new CANSparkMax(DriveConstants.LEFT_THROTTLE_PORT_FRONT, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightFrontTurn = new CANSparkMax(DriveConstants.RIGHT_TURN_PORT_FRONT, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightFrontThrottle = new CANSparkMax(DriveConstants.RIGHT_THROTTLE_PORT_FRONT, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftBackTurn = new CANSparkMax(DriveConstants.LEFT_TURN_PORT_BACK, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftBackThrottle = new CANSparkMax(DriveConstants.LEFT_THROTTLE_PORT_BACK, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightBackTurn = new CANSparkMax(DriveConstants.RIGHT_TURN_PORT_BACK, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightBackThrottle = new CANSparkMax(DriveConstants.RIGHT_THROTTLE_PORT_BACK, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        swerveModuleLeftFront = new SwerveModule(leftFrontThrottle, leftFrontTurn);
        swerveModuleRightFront = new SwerveModule(rightFrontThrottle, rightFrontTurn);
        swerveModuleLeftBack = new SwerveModule(leftBackThrottle, leftBackTurn);
        swerveModuleRightBack = new SwerveModule(rightBackThrottle, rightBackTurn);
        configureSparks();

        resetSensors();
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
     * @return The single instance of {@link SwerveDrive}
     */
    public static SwerveDrive getInstance() {
        if (instance == null) {
            instance = new SwerveDrive();
        }

        return instance;
    }

    /**
     * Helper method that configures the Spark Max motor controllers.
     */
    private void configureSparks() {
        leftFrontTurn.restoreFactoryDefaults();
        leftFrontTurn.setInverted(false);

        leftFrontThrottle.restoreFactoryDefaults();
        leftFrontThrottle.setInverted(false);

        rightFrontTurn.restoreFactoryDefaults();
        rightFrontTurn.setInverted(true);

        rightFrontThrottle.restoreFactoryDefaults();
        rightFrontThrottle.setInverted(true);

        leftBackTurn.restoreFactoryDefaults();
        leftBackTurn.setInverted(false);

        leftBackThrottle.restoreFactoryDefaults();
        leftBackThrottle.setInverted(false);

        rightBackThrottle.restoreFactoryDefaults();
        rightBackThrottle.setInverted(true);

        rightBackTurn.restoreFactoryDefaults();
        rightBackTurn.setInverted(true);
    }

    /**
     * Sets the drive motors to a certain percent output (demand) using open loop control (no sensors in feedback loop).
     *
     * @param leftFrontDemand The percent output for the left drive motors
     * @param leftFrontTurnDemand
     * @param leftBackDemand
     * @param leftBackTurnDemand
     * @param rightFrontDemand The percent output for the right drive motor
     * @param rightFrontTurnDemand
     * @param rightBackDemand
     * @param rightBackTurnDemand
     */
    public void setOpenLoop(double leftFrontDemand, double leftFrontTurnDemand, double rightFrontDemand,  double rightFrontTurnDemand, double leftBackDemand, double leftBackTurnDemand, double rightBackDemand, double rightBackTurnDemand) {
        leftFrontThrottle.set(leftFrontDemand);
        rightFrontThrottle.set(rightFrontDemand);
        leftBackThrottle.set(leftBackDemand);
        rightBackThrottle.set(rightBackDemand);

        leftFrontTurn.set(leftFrontTurnDemand);
        rightFrontTurn.set(rightFrontTurnDemand);
        leftBackTurn.set(leftBackTurnDemand);
        rightBackTurn.set(rightBackTurnDemand);
    }

    /**
     * Controls the drive motor using arcade controls - with a throttle and a turn.
     * 
     * @param throttle The throttle from the controller
     * @param turn The turn from the controller
     */
    public void swerveDrive(double throttle, double turn) {
        swerveModuleLeftFront.swerveDrive(throttle, turn);
        swerveModuleRightFront.swerveDrive(throttle, turn);
        swerveModuleLeftBack.swerveDrive(throttle, turn);
        swerveModuleRightBack.swerveDrive(throttle, turn);
    }

    /**
     * Resets the sensors of a subsystem to their initial values (e.g., set encoders to zero units).
     */
    @Override
    public void resetSensors() {

    }
}
