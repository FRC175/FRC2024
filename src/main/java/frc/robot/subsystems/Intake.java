package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.IntakeConstants;

public class Intake implements Subsystem {
    
    private final CANSparkMax intakeMotor; 
    private final RelativeEncoder intakeMotorEncoder; 
    private final DigitalInput reflectometer; 
    private static Intake instance; 

    private Intake() {
        intakeMotor = new CANSparkMax(IntakeConstants.INTAKE, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeMotor.setInverted(true);
        intakeMotor.restoreFactoryDefaults();
        intakeMotorEncoder = intakeMotor.getEncoder(); 
        reflectometer = new DigitalInput(0);
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance; 
    }

    public void setOpenLoop(double demand) {
        intakeMotor.set(demand);
    }

    public double getMotorRPM() {
        return intakeMotorEncoder.getVelocity(); 
    }

    public boolean isNotePresent() {
        return reflectometer.get();
    }
}
