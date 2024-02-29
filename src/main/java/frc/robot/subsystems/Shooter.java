package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShooterConstants;


import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;


public class Shooter implements Subsystem {
    private final CANSparkMax top;
    private final CANSparkMax bottom;
    private final RelativeEncoder topEncoder, bottomEncoder;

    private static Shooter instance;

private Shooter() {
    top = new CANSparkMax(ShooterConstants.TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
    bottom = new CANSparkMax(ShooterConstants.BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
    configureSparks();

    topEncoder = top.getEncoder();
    bottomEncoder = bottom.getEncoder();
}

public static Shooter getInstance() {
    if (instance == null) {
        instance = new Shooter();
    }

    return instance;
}

private void configureSparks() {


    

    bottom.restoreFactoryDefaults();
    
    bottom.setInverted(false);

    top.restoreFactoryDefaults();
    // shooterWheel.follow(shooterWheelSlave);
    top.setInverted(false);
    
}

public void shooterSetOpenLoop(double demandTop, double demandBot) {
    // SmartDashboard.putNumber("Demand", demand);
    // SmartDashboard.putNumber("Average RPM", getAverageShooterRPM());
    // SmartDashboard.putNumber("Bottom RPM", getBottomRPM());
    // SmartDashboard.putNumber("Top RPM", getTopRPM());
    // System.out.println("Average RPM: " + getAverageShooterRPM());
    // System.out.println("Bottom RPM: " + getBottomRPM());
    // System.out.println("Top RPM: " + getTopRPM());
    top.set(demandTop);
    bottom.set(demandBot);
}

@Override
public void periodic() {
    SmartDashboard.putNumber("Top RPM", getTopRPM());
    SmartDashboard.putNumber("Bottom RPM", getBottomRPM());
}

public double getTopRPM() {
    return topEncoder.getVelocity();
}

public double getBottomRPM() {
    return bottomEncoder.getVelocity();
}

public double getAverageShooterRPM() {
    return (getTopRPM() + getBottomRPM()) / 2;
}

public void turnOffShooter() {
    shooterSetOpenLoop(0, 0);

}

}