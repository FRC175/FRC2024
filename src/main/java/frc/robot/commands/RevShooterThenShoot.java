package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

public class RevShooterThenShoot extends SequentialCommandGroup {
    
    public RevShooterThenShoot(Shooter shooter, Intake intake) {
       addCommands(
            new RevShooter(shooter, intake, 4500, 3500),
            new InstantCommand(() -> intake.setOpenLoop(0.25)),
            new WaitCommand(0.25), 
            new InstantCommand(() -> {
                intake.setOpenLoop(0);
                shooter.shooterSetOpenLoop(0,0);
            })
       ); 
   }
}