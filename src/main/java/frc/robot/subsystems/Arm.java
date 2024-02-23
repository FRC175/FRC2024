package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ArmConstants;

public class Arm implements Subsystem {

    private static Arm instance; 

    private final CANSparkMax armMaster, armSlave; 
    // private final DutyCycleEncoder armEncoder; 

    private Arm() {
        armMaster = new CANSparkMax(ArmConstants.ARM_RIGHT, CANSparkMax.MotorType.kBrushless);
        armSlave = new CANSparkMax(ArmConstants.ARM_LEFT, CANSparkMax.MotorType.kBrushless);
        // armEncoder = new DutyCycleEncoder(1);
        

        configureSparks();
    }

    public static Arm getInstance() {
		if (instance == null) {
			instance = new Arm();
		}

		return instance;
	}

    private void configureSparks() {
        armMaster.restoreFactoryDefaults();
        armMaster.setInverted(false);

        
        armSlave.restoreFactoryDefaults();
        armSlave.follow(armMaster); 
        armSlave.setInverted(true);
    }
    
    public void setArmOpenLoop(double demand) {
        armMaster.set(demand);
    }

    public double getPosition() {
        // return -1 * armEncoder.getAbsolutePosition();
        return -1;
    }
   

    public void resetEncoders() {
        // armEncoder.reset();
    }
}
