package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.ShooterConstants;


import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;


public class Shooter implements Subsystem {
    private final CANSparkMax shooterWheel;
    private final CANSparkMax shooterWheelSlave;
    private final RelativeEncoder shooterWheelEncoder, shooterWheelSlaveEncoder;

    private static Shooter instance;

private Shooter() {
    shooterWheel = new CANSparkMax(ShooterConstants.shooterWheel, CANSparkMaxLowLevel.MotorType.kBrushless);
    shooterWheelSlave = new CANSparkMax(ShooterConstants.shooterWheelSlave, CANSparkMaxLowLevel.MotorType.kBrushless);
    configureSparks();

    shooterWheelEncoder = shooterWheel.getEncoder();
    shooterWheelSlaveEncoder = shooterWheelSlave.getEncoder();
}

public static Shooter getInstance() {
    if (instance == null) {
        instance = new Shooter();
    }

    return instance;
}

private void configureSparks() {


    shooterWheel.restoreFactoryDefaults();
    shooterWheel.setInverted(false);

    shooterWheelSlave.restoreFactoryDefaults();
    shooterWheelSlave.follow(shooterWheel);
    shooterWheelSlave.setInverted(true);
    
}

public void shooterSetOpenLoop(double demand) {
    SmartDashboard.putNumber("Demand", demand);
    shooterWheel.set(demand);
    shooterWheelSlave.set(demand);
}

public double getShooterRPM() {
    return shooterWheelEncoder.getVelocity();
}

public double getShooterSlaveRPM() {
    return shooterWheelSlaveEncoder.getVelocity();
}

public double getAverageShooterRPM() {
    return (getShooterRPM() + getShooterSlaveRPM()) / 2;
}

public void turnOffShooter() {
    shooterSetOpenLoop(0);

}

}