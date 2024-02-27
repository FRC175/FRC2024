package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.Constants.LEDConstants;

/**
 * FIXME: Not sure if this works...
 */
public final class LED implements Subsystem {

    private final Spark blinkin;

    private static LED instance;

    private LED() {
        blinkin = new Spark(LEDConstants.BLINKIN_PORT);
    }

    public static LED getInstance() {
        if (instance == null) {
            instance = new LED();
        }

        return instance;
    }

    public void setValue(double value) {
        blinkin.set(value);
    }



}