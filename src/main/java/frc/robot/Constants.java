// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class ControllerConstants {
        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double CONTROLLER_DEADBAND = 0.1;

        public static final double test = 0;
    }

    

    public static final class DriveConstants {
        public static final int frontRightDrive = 3;
        public static final int frontRightRot = 2;
        public static final int frontLeftDrive = 4;
        public static final int frontLeftRot = 5;
        public static final int backRightDrive = 6;
        public static final int backRightRot = 7;
        public static final int backLeftDrive = 8;
        public static final int backLeftRot = 9;

        public static final double frontRightTurnAngle = 45;
        public static final double frontLeftTurnAngle = 135;
        public static final double backRightTurnAngle = 315;
        public static final double backLeftTurnAngle = 225;

        public static final int frontRightEncoder = 21;
        public static final int frontLeftEncoder = 22;
        public static final int backRightEncoder = 23;
        public static final int backLeftEncoder = 24;

        public static final int PIDGEON = 20;

        public static final double ENCODERTOANGLE = (double) 360/4096;
    }
}
