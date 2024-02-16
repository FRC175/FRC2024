package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ArmConstants;

public class Arm implements Subsystem {

    private static Arm instance; 

    private final CANSparkMax armMoverRight, armMoverLeft; 
    private final DutyCycleEncoder armMoverRightEncoder, armMoverLeftEncoder; 

    private Arm() {
        armMoverRight = new CANSparkMax(ArmConstants.ARM_LEFT, CANSparkMax.MotorType.kBrushless);
        armMoverLeft = new CANSparkMax(ArmConstants.ARM_LEFT, CANSparkMax.MotorType.kBrushless);
        armMoverRightEncoder = new DutyCycleEncoder(0);
        armMoverLeftEncoder = new DutyCycleEncoder(1); 

        configureSparks();
    }

    public static Arm getInstance() {
		if (instance == null) {
			instance = new Arm();
		}

		return instance;
	}

    private void configureSparks() {
        armMoverRight.restoreFactoryDefaults();
        armMoverRight.setInverted(false);

        armMoverLeft.restoreFactoryDefaults();
        armMoverLeft.setInverted(true);
    }
    
    public void setArmOpenLoop(double rightDemand, double leftDemand) {
        armMoverRight.set(rightDemand);
        armMoverLeft.set(leftDemand); 
    }

    public double getRightPosition() {
        return -armMoverRightEncoder.getDistance();
        
    }
    public double getLeftPosition() {
        return armMoverLeftEncoder.getDistance();
    }

    public void resetEncoders() {
        armMoverLeftEncoder.reset();
        armMoverRightEncoder.reset();
}
}
