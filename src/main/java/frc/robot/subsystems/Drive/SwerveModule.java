package frc.robot.subsystems.Drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class SwerveModule {

    private final CANcoder encoder;
    private final CANSparkMax drive, turn;
    private final double turningAngle;
    
    public SwerveModule(int drive, int turn, int encoder, double turningAngle) {
        this.drive = new CANSparkMax(drive, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.turn = new CANSparkMax(turn, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.encoder = new CANcoder(encoder);
        this.turningAngle = turningAngle;
    }

    public void drive(double power) {
        drive.set(power);
    }

    public void turn(double power) {
        turn.set(power);
    }

    public double getEncoder() {
        return encoder.getAbsolutePosition().getValue();
    }



    
}
