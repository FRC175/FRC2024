package frc.robot.commands.AutoModes;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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

public class DoubleNote extends SequentialCommandGroup {

   public DoubleNote(Drive drive, Shooter shooter, Intake intake, Arm arm) {
     addCommands(
          new RunToPositionAndShoot(arm, shooter, intake, ArmConstants.REST),
          new SetArmPosition(arm, 0.2, 0.2, false, ArmConstants.INTAKE),
          new ParallelCommandGroup(
               new pickup(intake), 
               new SwerveToDist(drive, 0.2, 0, 0, 1)
          ),
          new SwerveToDist(drive, 0, 180, 0, 1),
          new RunToPositionAndShoot(arm, shooter, intake, ArmConstants.REST)
    );
}
}
