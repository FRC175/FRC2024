package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Controller;
import frc.robot.utils.Utils;

public class LockSwerve extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final Controller joy;
    private final double travSpeed;
    private final double lockAngle;

    public LockSwerve(Controller joy, Drive drive) {
        this.joy = joy;
        this.drive = drive;
        this.travSpeed = 0.7;
        this.lockAngle = -1;

        addRequirements(drive);
    }
    public LockSwerve(Controller joy, Drive drive, double lockAngle) {
        this.joy = joy;
        this.drive = drive;
        this.travSpeed = 0.7;
        this.lockAngle = lockAngle;

        addRequirements(drive);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        if (lockAngle == -1) {
            drive.lockSwerve(
                Utils.deadband(joy.getLeftX(), 0.05, travSpeed), 
                Utils.deadband(joy.getLeftY(), 0.05, travSpeed), 
                (-1*joy.getPOV() + 360) % 360, 
                drive.getYaw());
        } else {
            drive.lockSwerve(
                Utils.deadband(joy.getLeftX(), 0.05, travSpeed), 
                Utils.deadband(joy.getLeftY(), 0.05, travSpeed), 
                lockAngle, 
                drive.getYaw());
        }
        // drive.postEncoders();
        // drive.postYaw();
        // SmartDashboard.putBoolean("Trigger", joy.getTrigger());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
