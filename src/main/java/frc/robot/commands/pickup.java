package frc.robot.commands;

import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class pickup extends CommandBase {
    
    private final Intake intake; 
    private boolean initial;
   
    

    public pickup(Intake intake) {
        
        this.intake = intake;
        initial = true;
       
  
        addRequirements(intake);
    }

    @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    if (initial) intake.setOpenLoop(0.5);
    if (initial) if (intake.isNotePresent()) initial = false;
    if (!initial) intake.setOpenLoop(-0.15);
  }
  
  @Override
  public void end(boolean interrupted) {
    intake.setOpenLoop(0);
    initial = true;
  }

  @Override
  public boolean isFinished() {
    return !initial && !intake.isNotePresent();
  }
}

