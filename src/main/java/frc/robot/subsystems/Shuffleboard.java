package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.DriveConstants;
// import frc.robot.subsystems.Drive.Drive;

public final class Shuffleboard implements Subsystem {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    
    
    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Shuffleboard instance;

    private Limelight limelight;

    private Shuffleboard() {
		
		limelight = Limelight.getInstance();
		
	}

	public static Shuffleboard getInstance() {
		if (instance == null) {
			instance = new Shuffleboard();
		}

		return instance;
	}

    public void logTargeted() {
        SmartDashboard.putBoolean("apriltag", limelight.targetDetected());
        SmartDashboard.putNumber("ID", limelight.getTargetID());
    }
    
  

}
