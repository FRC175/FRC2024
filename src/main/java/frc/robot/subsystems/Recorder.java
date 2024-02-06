package frc.robot.subsystems;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Drive.Drive;

public final class Recorder implements Subsystem {

    // These variables are final because they only need to be instantiated once (after all, you don't need to create a
    // new left master TalonSRX).
    private FileOutputStream fileOut;
    
    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Recorder instance;
    
    private Recorder() {
        File file = new File("/home/lvuser/recorded3.bin");
        // file.delete();
        try {
            fileOut = new FileOutputStream(file);
            byte[] data = new byte[255];
            for (int i = -128; i < 127; ++i) {
                data[i + 128] = (byte)i;
            }
            fileOut.write(data, 0, data.length);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>getInstance()</code> is a crucial part of the "singleton" design pattern. This pattern is used when there
     * should only be one instance of a class, which makes sense for Robot subsystems (after all, there is only one
     * drivetrain). The singleton pattern is implemented by making the constructor private, creating a static variable
     * of the type of the object called <code>instance</code>, and creating this method, <code>getInstance()</code>, to
     * return the instance when the class needs to be used.
     *
     * Usage:
     * <code>Drive drive = Drive.getInstance();</code>
     *
     * @return The single instance of {@link Drive}
     */
    public static Recorder getInstance() {
        if (instance == null) {
            instance = new Recorder();
        }

        return instance;
    }
}
