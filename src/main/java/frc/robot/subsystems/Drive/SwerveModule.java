package frc.robot.subsystems.Drive;

import org.ejml.dense.block.VectorOps_DDRB;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.utils.Vector;

public class SwerveModule {

    private final CANcoder encoder;
    private final CANSparkMax drive, turn;
    private final double turnAngle;
    private double goalAngle;
    private static int id;
    private final int num;
    private final PIDController controller;

    private static double[] vectorMagnitudes = new double[4]; //FR,FL,BR,BL
    
    public SwerveModule(int drive, int turn, int encoder, double baseAngle) {
        this.drive = new CANSparkMax(drive, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.turn = new CANSparkMax(turn, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.encoder = new CANcoder(encoder);
        this.turnAngle = baseAngle;
        this.num = id++;
        this.goalAngle = 0;
        this.controller = new PIDController(0.5, 0, 0);
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

    public void swerve(Vector t, double twist, double gyroAngle) {
        // Vector r = new Vector(twist * Math.cos(Math.toRadians(turnAngle)), twist * Math.sin(Math.toRadians(turnAngle))); // both vectors have angles with respect to x
        // Vector goal = t.add(r);
        Vector goal = t;
        vectorMagnitudes[num] = goal.getMagnitude();
        goal.normalize(maxMag());
        drive.set(goal.getMagnitude());

        double turnOut = interpolate(goalAngle, gyroAngle);
        turn.set(turnOut);
    }

    public double interpolate(double goalAngle, double gyroAngle) {
        double val = controller.calculate(encoderToAngle(encoder.getAbsolutePosition().getValue()), goalAngle-gyroAngle);
        // NEEDS:
        /*
         * Method to go from encoder out 
         */
        double output = 0;
        return output;
    }

    public double encoderToAngle(double encoderVal) {
        return -1;
    }

    public double getEncoder() {
        return encoder.getAbsolutePosition().getValue();
    }
}
