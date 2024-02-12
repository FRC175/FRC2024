package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
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
        SmartDashboard.putNumber("numberApriltags", limelight.numTargetsDetected(limelight.getJson()));
        for (int i = 0; i < limelight.getTargetIds(limelight.getJson()).length; i++) {
            try {
                SmartDashboard.putNumber("apriltag" + (i + 1), limelight.getTargetIds(limelight.getJson())[i]);
            } catch(ArrayIndexOutOfBoundsException e) {

            }
            }
        for (int i = 0; i < limelight.getVerticalAngle(limelight.getJson()).length; i++) {
            try {
                SmartDashboard.putNumber("("+limelight.getTargetIds(limelight.getJson())[i]+")verticalOff", limelight.getVerticalAngle(limelight.getJson())[i]);
            } catch(ArrayIndexOutOfBoundsException e) {

            }
        }
        for (int i = 0; i < limelight.getHorizontalAngle(limelight.getJson()).length; i++) {
           try {

            SmartDashboard.putNumber("("+limelight.getTargetIds(limelight.getJson())[i]+")horizontalOff", limelight.getHorizontalAngle(limelight.getJson())[i]);
           }catch(ArrayIndexOutOfBoundsException e) {

           }
        }
        for (int i = 0; i < limelight.getDistance(limelight.getTargetIds(limelight.getJson())).length; i++) {
            try {
               SmartDashboard.putNumber("("+limelight.getTargetIds(limelight.getJson())[i]+")dist", limelight.getDistance(limelight.getTargetIds(limelight.getJson()))[i]);
            } catch(ArrayIndexOutOfBoundsException e) {
                
            }
        }
        try {
            SmartDashboard.putNumber("distBetween tags", limelight.getDistanceOfPointBetween(limelight.getTargetIds(limelight.getJson())));
        } 
        catch(ArrayIndexOutOfBoundsException e) {

        }
        //SmartDashboard.putNumber("Prim ID", limelight.getTargetID());
        // PLACEHOLDER CODE - smartdashboard is being weird about adding arrays
        SmartDashboard.putNumberArray("json", limelight.getTargetIds(limelight.getJson()));
       
       
    }
    
  

}
