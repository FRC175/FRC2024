package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

public class RevShooterThenShoot extends SequentialCommandGroup {
    
    public RevShooterThenShoot(Shooter shooter, Intake intake) {
       this(shooter, intake, 4500, 3500);
   }

   public RevShooterThenShoot(Shooter shooter, Intake intake, double top, double bottom) {
    addCommands(
         new RevShooter(shooter, intake, top, bottom),
         new InstantCommand(() -> intake.setOpenLoop(0.7)),
         new WaitCommand(0.2), 
         new InstantCommand(() -> {
             intake.setOpenLoop(0);
             shooter.shooterSetOpenLoop(0,0);
         })
    ); 
}
}
