package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;

public class SwerveToDist extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;

    private final double speed;
    private final double targetAngle;
    private final double headingAngle;
    private final double targetDistance;


    public SwerveToDist(Drive drive, double targetSpeed, double targetAngle, double headingAngle, double targetDistance) {
    
        this.drive = drive;
        this.speed = targetSpeed;
        this.targetAngle = targetAngle;
        this.headingAngle = headingAngle;
        this.targetDistance = targetDistance;

        
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetDistance();
    }


    @Override
    public void execute() {
        double joyX = speed * Math.cos(Math.toRadians((targetAngle+90) % 360));
        double joyY = -speed * Math.sin(Math.toRadians((targetAngle+90) % 360));
        drive.lockSwerve(joyX, joyY, headingAngle, drive.getYaw());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setOpenLoop(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drive.getDriveDistance()) >= targetDistance;
    } 
}
