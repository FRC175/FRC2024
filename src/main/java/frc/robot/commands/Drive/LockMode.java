package frc.robot.commands.Drive;

import frc.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Utils;

public class LockMode extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;

    public LockMode(Drive drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        drive.lock();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
