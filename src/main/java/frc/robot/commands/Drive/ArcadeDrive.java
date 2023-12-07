package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class ArcadeDrive extends CommandBase {
    XboxController controller;
    Drive drive;

    public ArcadeDrive(XboxController driverController, Drive drive) {
        this.controller = driverController;
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.arcadeDrive(controller.getRightTriggerAxis(), controller.getLeftTriggerAxis(), controller.getLeftX());
        
    }

    @Override
    public void end(boolean interrupted) {
        drive.setOpenLoop(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
