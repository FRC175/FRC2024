package frc.robot.commands.AutoModes;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.SetArmPosition;
import frc.robot.commands.pickup;
import frc.robot.commands.Drive.SwerveToDist;
import frc.robot.commands.Shooter.RunToPositionAndShoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class ShootFrontCollectNoteScore extends SequentialCommandGroup {

   public ShootFrontCollectNoteScore(Drive drive, Shooter shooter, Intake intake, Arm arm) {
    addCommands(
         new ShootFromFrontSubwoofer(drive, shooter, intake, arm),
         new SwerveToDist(drive, .1, 180, 3),
         new SetArmPosition(arm, 0.3, 0.4, true,ArmConstants.INTAKE),
         new pickup(intake),
         new RunToPositionAndShoot(arm, shooter, intake, ArmConstants.SPEAKER)
         
    );
}
}
