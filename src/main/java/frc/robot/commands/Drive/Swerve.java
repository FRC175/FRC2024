package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Controller;
import frc.robot.utils.Utils;

public class Swerve extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final Controller joy;
    private final double travSpeed;

    public Swerve(Controller joy, Drive drive) {
        this.joy = joy;
        this.drive = drive;
        this.travSpeed = 0.7;

        addRequirements(drive);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        drive.swerve(
            Utils.deadband(joy.getLeftX(), 0.1, travSpeed), 
            Utils.deadband(joy.getLeftY(), 0.1, travSpeed), 
            Utils.deadband(joy.getTwist(), 0.25) * -1 * (1-travSpeed), 
            drive.getYaw());
        drive.postEncoders();
        drive.postYaw();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
