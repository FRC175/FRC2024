package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SetArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RunToPositionAndShoot extends SequentialCommandGroup {


    public RunToPositionAndShoot(Arm arm, Shooter shooter, Intake intake, double position) {
       addCommands(
            new SetArmPosition(arm, 0.3, 0.4, true, position),
            new RevShooterThenShoot(shooter, intake)
       ); 
   }
    
}
