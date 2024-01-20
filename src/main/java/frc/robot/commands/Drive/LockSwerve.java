// package frc.robot.commands.Drive;

// import frc.robot.subsystems.Gyro;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Drive.Drive;
// import frc.robot.utils.Utils;

// public class LockSwerve extends CommandBase {
//     @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//     private final Drive drive;
//     private final Gyro gyro;
//     private final Joystick joy;
//     private final double travSpeed;

//     public LockSwerve(Joystick joy, Drive drive, Gyro gyro) {
//         this.joy = joy;
//         this.drive = drive;
//         this.gyro = gyro;
//         this.travSpeed = 0.7;

//         addRequirements(drive, gyro);
//     }

//     @Override
//     public void initialize() {

//     }


//     @Override
//     public void execute() {
//         drive.lockSwerve(
//             Utils.deadband(joy.getX(), 0.05, travSpeed), 
//             Utils.deadband(joy.getY(), 0.05, travSpeed), 
//             (-1*joy.getPOV() + 360) % 360, 
//             gyro.getYaw());
//         drive.postEncoders();
//         gyro.postYaw();
//         SmartDashboard.putBoolean("Trigger", joy.getTrigger());
//     }

//     @Override
//     public void end(boolean interrupted) {}

//     @Override
//     public boolean isFinished() {
//         return false;
//     }
// }
