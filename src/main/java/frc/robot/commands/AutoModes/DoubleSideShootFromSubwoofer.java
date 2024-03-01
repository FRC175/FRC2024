package frc.robot.commands.AutoModes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Utils;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.SetArmPosition;
import frc.robot.commands.Drive.ResetGyro;
import frc.robot.commands.Drive.SwerveToDist;
import frc.robot.commands.Shooter.RunToPositionAndShoot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class DoubleSideShootFromSubwoofer extends SequentialCommandGroup {

   public DoubleSideShootFromSubwoofer(Drive drive, Shooter shooter, Intake intake, Arm arm) {
    double angle = Utils.convToSideAngle(54);
    addCommands(
        new ResetGyro(drive, angle),
         new RunToPositionAndShoot(arm, shooter, intake, ArmConstants.REST),
         new InstantCommand(() -> {
             intake.setOpenLoop(0);
             shooter.shooterSetOpenLoop(0,0);
         }),
         new SwerveToDist(drive, 0.2, angle, angle, 2)
    ); 
}
}