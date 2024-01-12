package frc.robot.subsystems.Drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.utils.Vector;

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

    private static double maxMag() {
        return Math.max(Math.max(vectorMagnitudes[0], vectorMagnitudes[1]), Math.max(vectorMagnitudes[2], vectorMagnitudes[3]));
    }

    public void driveOpenLoop(double power) {
        drive.set(power);
    }

    public void turnOpenLoop(double power) {
        turn.set(power);
    }

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
