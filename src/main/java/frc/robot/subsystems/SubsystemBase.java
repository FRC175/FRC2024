package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class SubsystemBase implements Subsystem {
    /**
     * Resets the sensors of a subsystem to their initial values (e.g., set encoders to zero units).
     */
    public abstract void resetSensors();
}
