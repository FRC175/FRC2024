package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Point;
import frc.robot.utils.Path;

public class ResetGyro extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final double startingAngle;

    public ResetGyro(Drive drive, double startingAngle) {
        this.drive = drive;
        this.startingAngle = startingAngle;
        

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetGyro(startingAngle);
    }


    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
