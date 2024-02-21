package frc.robot.commands;

import frc.robot.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Climb extends CommandBase {
    
    private final Lift lift; 
    private double maxCounts; 

    public Climb(Lift lift) {
        this.lift = lift;

        maxCounts = 5.7; 

        addRequirements(lift);
    }

    @Override
    public void initialize() {
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        lift.setLiftOpenLoop(0.2);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        lift.setLiftOpenLoop(0);
    }
}
