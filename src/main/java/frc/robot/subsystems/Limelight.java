package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Subsystem;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;



public final class Limelight implements Subsystem {
    /**
     * The single instance of {@link Drive} used to implement the "singleton" design pattern. A description of the
     * singleton design pattern can be found in the JavaDoc for {@link Drive::getInstance()}.
     */
    private static Limelight instance;

    public final NetworkTable table;

    public int pipe;
    
    private Limelight() {
        
        pipe = 0;
        table = NetworkTableInstance.getDefault().getTable("limelight");
        table.getEntry("getpipe").setNumber(pipe);
        
    }

    public void switchPipe() {

        if (pipe == 0) pipe = 1;
        else pipe = 0;
        
    }

    public double getVerticalAngle() {
        return table.getEntry("ty").getDouble(0);
    }

    public double getHorizontalAngle() {
        return table.getEntry("tx").getDouble(0);
    }


    public boolean targetDetected() {
        return table.getEntry("tv").getDouble(0) == 1; 
    }

    public double getTargetID() {
        return table.getEntry("tid").getDouble(0);
    }
    
 
    public String[] test() {
    //     double[] detectedTargets = table.getEntry("t").getDoubleArray(new double [1]);
    //     double[] explode = {1.0,2.0,3.0};
    // //    double[] detectedTargets = {69.420, 2.0, 38.3};
    //     if (detectedTargets[0] == 0) return explode;
    //     else return detectedTargets;

    String data = table.getEntry("json").getString("0");
    return new String[0];
    
        
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
    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }

        return instance;
    }


}
