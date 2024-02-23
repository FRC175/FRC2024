package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class SetArmPosition extends CommandBase {
    
   private final Arm arm; 
   private boolean isFinite; 
   private boolean hasHit = false; 
   private double speed; 
   private double armPosition; 

    public SetArmPosition(Arm arm, double speed, boolean isFinite, double armPosition) {
       this.arm = arm;
       this.isFinite = isFinite;
       this.speed = speed; 
       this.armPosition = armPosition;
       addRequirements(arm);
    }

    @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    
    if (isFinite) {
        if (arm.getPosition() < armPosition)
            arm.setArmOpenLoop(-1 * speed);
        else if (arm.getPosition() > armPosition)
            arm.setArmOpenLoop(speed);
    } else {
        if (!hasHit) {
            if (arm.getPosition() < armPosition - 0.1)
                arm.setArmOpenLoop(-1 * speed);
            else if (arm.getPosition() > armPosition + 0.1)
                arm.setArmOpenLoop(speed);
            else hasHit = true;
        } else {
            if (armPosition - arm.getPosition() > 0.1) {
                arm.setArmOpenLoop(-0.1);
            } else {
                arm.setArmOpenLoop(0);
            }
        }
    }
  }
  
  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    if (isFinite) return Math.abs(arm.getPosition() - armPosition) <= 0.1; // experiment with deadband
        else return false;
  }
}

