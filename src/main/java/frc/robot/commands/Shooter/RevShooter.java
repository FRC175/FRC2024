package frc.robot.commands.Shooter;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RevShooter extends CommandBase {
    
    private final Shooter shooter;
    private final Intake intake; 
   
    private double topRPM;
    private double bottomRPM;

    public RevShooter(Shooter shooter, Intake intake, double topRPM, double bottomRPM) {
        this.shooter = shooter;
        this.intake = intake;
        this.topRPM = topRPM;
        this.bottomRPM = bottomRPM;
  
        addRequirements(shooter, intake);
    }

    @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    intake.setOpenLoop(0);
	  shooter.shooterSetOpenLoop(topRPM / 5500.0 + (topRPM - shooter.getTopRPM()) / 15000.0, bottomRPM / 5000.0 + (bottomRPM - shooter.getBottomRPM()) / 15000.0);
  }
  
  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return Math.abs(shooter.getTopRPM() - topRPM) < 100 && Math.abs(shooter.getBottomRPM() - bottomRPM) < 100;
  }
}

