package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RevShooter extends CommandBase {
    
    private final Shooter shooter;
    private final Intake intake; 
   
    private double rpm; 

    public RevShooter(Shooter shooter, Intake intake, double rpm) {
        this.shooter = shooter;
        this.intake = intake;
        this.rpm = rpm;
  
        addRequirements(shooter, intake);
    }

    @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    intake.setOpenLoop(0);
	shooter.shooterSetOpenLoop(1);
  }
  
  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return Math.abs(shooter.getAverageShooterRPM() - rpm) < 230;
  }
}

