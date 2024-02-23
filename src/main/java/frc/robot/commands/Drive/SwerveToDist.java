package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.subsystems.Gyro;

public class SwerveToDist extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final Gyro gyro;

    private final double speed;
    private final double angle;
    private final double encoderCounts;


    public SwerveToDist(Drive drive, Gyro gyro, double targetSpeed, double targetAngle, double targetEncoderCounts) {
    
        this.drive = drive;
        this.gyro = gyro;
        this.speed = targetSpeed;
        this.angle = targetAngle;
        this.encoderCounts = targetEncoderCounts;
        
        addRequirements(drive, gyro);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        double joyX = speed * Math.cos((angle+90)%360);
        double joyY = speed * Math.sin((angle+90)%360);
        drive.lockSwerve(joyX, joyY, 0, gyro.getAngleDegrees());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        
        return drive.getFLPosition() >= encoderCounts;
    } 
}
