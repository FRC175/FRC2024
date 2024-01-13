package frc.robot.subsystems.Drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.utils.Vector;

/**
 * A class which represents a drive-turn motor combination, used for "Swerve Drive".
 */
public class SwerveModule {

    private final CANcoder encoder;
    private final CANSparkMax drive, turn;
    private final double turnAngle;
    private double goalAngle;
    private static int id;
    private final int num;
    private final double basePosition;
    private boolean reversed;

    private static double[] vectorMagnitudes = new double[4]; //FR,FL,BR,BL
    private static double[] vectorRotations = new double[4];
    
    /**
     * A Swerve Module is an abstraction of each individual turn-drive motor combination. They must work
     * in conjunction in order to accurately produce swerve motion.
     * @param drive CAN ID of the Drive motor.
     * @param turn CAN ID of the Turn motor.
     * @param encoder CAN ID of the specified CANCoder.
     * @param baseAngle The angle of maximum turning for the specified position.
     * @param basePosition A value to shift the CANCoders frame of reference, sets that position to be "0 degrees".
     */
    public SwerveModule(int drive, int turn, int encoder, double baseAngle, double basePosition) {
        this.drive = new CANSparkMax(drive, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.turn = new CANSparkMax(turn, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.encoder = new CANcoder(encoder);
        this.turnAngle = baseAngle;
        this.num = id++;
        this.goalAngle = 0;
        this.basePosition = basePosition;
        this.reversed = false;
    }

    /**
     * Finds the goal vector with the largest magnitude.
     * @return the magnitude of the vector
     * 
     */
    private static double maxMag() {
        return Math.max(Math.max(vectorMagnitudes[0], vectorMagnitudes[1]), Math.max(vectorMagnitudes[2], vectorMagnitudes[3]));
    }


    /**
     * Runs the drive motors as a specified power.
     * @param power : [0,1]
     */
    public void driveOpenLoop(double power) {
        drive.set(power);
    }

    /**
     * Runs the turn motors at a specified power.
     * @param power : [0,1]
     */
    public void turnOpenLoop(double power) {
        turn.set(power);
    }

    /**
     * Finds the outputs for the turn and drive motors. <p>Passes the data into the {@link #vectorMagnitudes}, and {@link #vectorRotations} static arrays. <p>Used in {@link #setOutputs()}.
     * @param transversal The input transversal vector from the joystick. <strong>[0,1]</strong>, <strong>[0,360)</strong> 
     * @param twist The input twist value from the joystick. <strong>[0,1]</strong>
     * @param gyroAngle The current gyro heading. <strong>[0, 360)</strong>
     * 
     */
    public void calculateRawOutputs(Vector transversal, double twist, double gyroAngle) {
        Vector rotation = new Vector(twist * Math.cos(Math.toRadians(turnAngle)), twist * Math.sin(Math.toRadians(turnAngle))); // both vectors have angles with respect to x
        // rotation.rotate(-90);
        Vector goal = transversal.add(rotation);

        // Vector goal = transversal;

        vectorMagnitudes[num] = goal.getMagnitude();

        // if (reversed) goalAngle = (goal.getAngle()+180) % 360;
        goalAngle = goal.getAngle();

        if (Math.abs(goal.getMagnitude()) > 0.01) goalAngle = goal.getAngle();

        double delta = findDelta(goalAngle);

        if (Math.abs(delta) > 90) {
            goalAngle = (goalAngle + 180) % 360;
            reversed = true;
        } else {
            reversed = false;
        }

        delta = findDelta(goalAngle);

        double maxSpeed = 1.0;
        double turnOut = Math.abs(delta) > 1 ? ((delta / 180) * maxSpeed) : 0;
        turnOut *= -1.0;
        vectorRotations[num] = turnOut;

        SmartDashboard.putNumber("Goal Angle", goalAngle);
    }

    public void calculateRawLockOutputs(Vector transversal, double gyroAngle) {

    }

    /**
     * Sets the outputs for the drive and turn motors. <p>{@link #calculateRawOutputs()} {@link #calculateRawLockOutputs()}
     */
    public void setOutputs() {
        // double maxOutput = maxMag();
        // if (maxOutput > 1.0) {
        //     for (int i = 0; i < vectorMagnitudes.length; ++i) {
        //         vectorMagnitudes[i] /= maxOutput;
        //     }
        // }
        if (reversed) drive.set(-1 * vectorMagnitudes[num]);
        else drive.set(vectorMagnitudes[num]);
        turn.set(vectorRotations[num]);
    }

    public double findDelta(double goalAngle) {
        // double val = controller.calculate(encoderToAngle(encoder.getAbsolutePosition().getValue()), goalAngle-gyroAngle);
        double currentAngle = getAngle();
        double delta = Math.abs(goalAngle-currentAngle) < 180 ? (goalAngle-currentAngle) : (goalAngle-currentAngle) - 360 * Math.signum(goalAngle-currentAngle);
        return delta;
    }

    public double encoderToAngle(double encoderVal) {
        return (((encoderVal * 360)  % 360) + 360) % 360;
    }

    public double getAngle() {
        return encoderToAngle(getShiftedEncoder());
    }

    public double getEncoder() {
        return encoder.getPosition().getValue();
    }

    public double getShiftedEncoder() {
        return getEncoder() - basePosition;
    }
}
