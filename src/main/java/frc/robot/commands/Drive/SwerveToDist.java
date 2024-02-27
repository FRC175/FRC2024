package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;

public class SwerveToDist extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;

    private final double speed;
    private final double angle;
    private final double encoderCounts;


    public SwerveToDist(Drive drive, double targetSpeed, double targetAngle, double targetEncoderCounts) {
    
        this.drive = drive;
        this.speed = targetSpeed;
        this.angle = targetAngle;
        this.encoderCounts = targetEncoderCounts;
        
        addRequirements(drive);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        // double joyX = speed * Math.cos((angle+90)%360);
        // double joyY = speed * Math.sin((angle+90)%360);
        // drive.lockSwerve(joyX, -joyY, 0, drive.getYaw());
        drive.lockSwerve(0, -speed, 0, drive.getYaw());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setOpenLoop(0, 0);
    }

    @Override
    public boolean isFinished() {
        
        return drive.getFLPosition() >= encoderCounts;
    } 
}
