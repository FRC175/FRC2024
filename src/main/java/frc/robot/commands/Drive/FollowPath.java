package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Point;
import frc.robot.utils.Path;

public class FollowPath extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final Path path;
    private Point lastPoint;
    private Point goalPoint;
    private int currentPoint;

    public FollowPath(Drive drive, Path path) {
        this.drive = drive;
        this.path = path;
        currentPoint = 0;
        

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        lastPoint = path.getStart();
        goalPoint = path.getPoints()[0];
    }


    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return currentPoint >= path.getPoints().length;
    }
}
