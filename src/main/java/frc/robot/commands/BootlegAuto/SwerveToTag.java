// package frc.robot.commands.BootlegAuto;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Drive.Drive;
// import frc.robot.subsystems.Limelight;
// import frc.robot.subsystems.Gyro;


// public class SwerveToTag extends CommandBase {
//     @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//     private final Drive drive;
//     private final Limelight limelight;
//     private final Gyro gyro;



//     public SwerveToTag(Drive drive, Gyro gyro, Limelight limelight) {
    
//         this.drive = drive;
//         this.limelight = limelight;
//         this.gyro = gyro;
//         addRequirements(drive);
//     }

//     @Override
//     public void initialize() {

//     }


//     @Override
//     public void execute() {
//         double verticalOffset = limelight.getVerticalAngle();
//     if (verticalOffset > 3 || verticalOffset < 0) {
//         double joyX = .07 * Math.cos((angle+90)%360);
//         double joyY = .07 * Math.sin((angle+90)%360);
//         drive.lockSwerve(joyX, joyY, 0, gyro.getAngleDegrees());
//     } else {
//       cancel();
//     }
//     }

//     @Override
//     public void end(boolean interrupted) {}

//     @Override
//     public boolean isFinished() {
        
//         return 
//     }
// }
