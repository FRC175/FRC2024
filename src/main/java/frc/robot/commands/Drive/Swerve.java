package frc.robot.commands.Drive;

import frc.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Utils;

public class Swerve extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    private final Gyro gyro;
    private final Joystick joy;
    private final double travSpeed;

    public Swerve(Joystick joy, Drive drive, Gyro gyro) {
        this.joy = joy;
        this.drive = drive;
        this.gyro = gyro;
        this.travSpeed = 0.7;

        addRequirements(drive, gyro);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        drive.swerve(
            Utils.deadband(joy.getX(), 0.1, travSpeed), 
            Utils.deadband(joy.getY(), 0.1, travSpeed), 
            Utils.deadband(joy.getTwist(), 0.25) * -1 * (1-travSpeed), 
            gyro.getYaw());
        drive.postEncoders();
        gyro.postYaw();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
