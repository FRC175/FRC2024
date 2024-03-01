package frc.robot.commands.Shooter;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

import java.util.LinkedList;
import java.util.Queue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RevShooter extends CommandBase {
    
    private final Shooter shooter;
    private final Intake intake; 
   
    private double topRPM;
    private double bottomRPM;
    private Queue<Double> topErrors = new LinkedList<>();
    private Queue<Double> bottomErrors = new LinkedList<>();
    private double topAccError = 0.0;
    private double bottomAccError = 0.0;
    private final int ERR_MAX_Q_SIZE = 10;

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

    // TOP
    double topError = topRPM - shooter.getTopRPM();
    topAccError += topError / (double)ERR_MAX_Q_SIZE;
    topErrors.add(topError);

    if (topErrors.size() > ERR_MAX_Q_SIZE) {
      topAccError -= topErrors.remove() / (double)ERR_MAX_Q_SIZE;
    }

    // BOTTOM
    double bottomError = bottomRPM - shooter.getBottomRPM();
    bottomAccError += bottomError / (double)ERR_MAX_Q_SIZE;
    bottomErrors.add(bottomError);

    if (bottomErrors.size() > ERR_MAX_Q_SIZE) {
      bottomAccError -= bottomErrors.remove() / (double)ERR_MAX_Q_SIZE;
    }

    SmartDashboard.putNumber("topErr", topAccError);
    SmartDashboard.putNumber("botErr", bottomAccError);

	  shooter.shooterSetOpenLoop(
      topRPM /*topAccError5*/ / 5000.0 + (topRPM - shooter.getTopRPM()) / 10000.0, 
      bottomRPM /*bottomAccError/5)*/ / 5000.0 + (bottomRPM - shooter.getBottomRPM()) / 10000.0);
  }
  
  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return Math.abs(shooter.getTopRPM() - topRPM) < 100 && Math.abs(shooter.getBottomRPM() - bottomRPM) < 100;
  }
}

