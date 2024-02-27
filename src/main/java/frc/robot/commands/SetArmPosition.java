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
   private double downSpeed;
   private double upSpeed;
   private double armGoalPosition;
   private static final double DEADBAND = 0.001;

    public SetArmPosition(Arm arm, double downSpeed, double upSpeed, boolean isFinite) {
        this(arm, downSpeed, upSpeed, isFinite, arm.getArmGoalPosition());
    }
    

    public SetArmPosition(Arm arm, double downSpeed, double upSpeed, boolean isFinite, double armGoalPosition) {
        this.arm = arm;
        this.isFinite = isFinite;
        this.downSpeed = downSpeed;
        this.upSpeed = upSpeed; 
        // if (this.arm.getArmGoalPosition() != armGoalPosition) {
        //     this.arm.setArmGoalPosition(armGoalPosition);
        //     hasHit = false;
        // }
        this.armGoalPosition = armGoalPosition;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setArmGoalPosition(armGoalPosition);
    }

    @Override
    public void execute() {

    
        double armGoalPosition = arm.getArmGoalPosition();
        SmartDashboard.putNumber("Arm Position", arm.getPosition());
        SmartDashboard.putNumber("Goal Position: ", armGoalPosition);

        if (isFinite) {
            if (arm.getPosition() > armGoalPosition)
                arm.setArmOpenLoop(-upSpeed);
            else if (arm.getPosition() < armGoalPosition)
                arm.setArmOpenLoop(+downSpeed);
        } else {
            if (!hasHit) {
                if (arm.getPosition() > armGoalPosition + DEADBAND)
                    arm.setArmOpenLoop(-upSpeed);
                else if (arm.getPosition() < armGoalPosition - DEADBAND)
                    arm.setArmOpenLoop(+downSpeed);
                else hasHit = true;
            } else {
                if (arm.getPosition() - armGoalPosition > DEADBAND) {
                    arm.setArmOpenLoop(-0.1);
                } else if (Math.abs(arm.getPosition() - armGoalPosition) > 0.01) {
                    hasHit = false;
                } else {
                    arm.setArmOpenLoop(0);
                }
            }
        }
    }
  
    @Override
    public void end(boolean interrupted) {
        arm.setArmOpenLoop(0);
    }

    @Override
    public boolean isFinished() {
    return isFinite && Math.abs(arm.getPosition() - arm.getArmGoalPosition()) <= DEADBAND; // experiment with deadband
    }
}

