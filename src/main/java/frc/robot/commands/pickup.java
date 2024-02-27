package frc.robot.commands;

import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class pickup extends CommandBase {
    
    private final Intake intake; 
   
    

    public pickup(Intake intake) {
        
        this.intake = intake;
       
  
        addRequirements(intake);
    }

    @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    intake.setOpenLoop(0.5);
  }
  
  @Override
  public void end(boolean interrupted) {
    intake.setOpenLoop(0);
  }

  @Override
  public boolean isFinished() {
    return intake.isNotePresent();
  }
}

