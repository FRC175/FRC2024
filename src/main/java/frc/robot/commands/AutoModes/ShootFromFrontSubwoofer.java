package frc.robot.commands.AutoModes;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.SetArmPosition;
import frc.robot.commands.Drive.SwerveToDist;
import frc.robot.commands.Shooter.RunToPositionAndShoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class ShootFromFrontSubwoofer extends SequentialCommandGroup {

   public ShootFromFrontSubwoofer(Drive drive, Shooter shooter, Intake intake, Arm arm) {
    addCommands(
         new RunToPositionAndShoot(arm, shooter, intake, ArmConstants.SPEAKER),
         new InstantCommand(() -> {
             intake.setOpenLoop(0);
             shooter.shooterSetOpenLoop(0,0);
         }),
         new SwerveToDist(drive, 0.1, 0, 1)
    ); 
}
}
