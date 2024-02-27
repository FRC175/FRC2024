package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class DeployArm extends CommandBase {
    private final Arm arm; 
    private double power;
    
    public DeployArm(Arm arm, double power) {
        this.arm = arm; 
        this.power = power;
    }
    

    @Override
    public void initialize() {
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
       arm.setArmOpenLoop(power);
       SmartDashboard.getNumber("Arm Position", arm.getPosition());
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.setArmOpenLoop(0);
    }
}