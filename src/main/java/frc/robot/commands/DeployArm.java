package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class DeployArm extends CommandBase {
    private final Arm arm; 
    
    public DeployArm(Arm arm) {
        this.arm = arm; 
    }
    

    @Override
    public void initialize() {
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
       arm.setArmOpenLoop(.65);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.setArmOpenLoop(0);
    }
}