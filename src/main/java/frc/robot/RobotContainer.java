// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import frc.robot.models.AdvancedXboxController;
import frc.robot.models.XboxButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.RevShooterThenShoot;
import frc.robot.commands.pickup;
import frc.robot.commands.Drive.LockMode;
import frc.robot.commands.Drive.LockSwerve;
import frc.robot.commands.Drive.Swerve;
import frc.robot.subsystems.Gyro;
<<<<<<< HEAD
import frc.robot.subsystems.Recorder;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.subsystems.Intake; 
import frc.robot.subsystems.Shooter;
=======
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.subsystems.Shuffleboard;
>>>>>>> 7bd801c2de0ab97a40000dc3c6ade2fd8a2c1086

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Drive drive;
  private final Gyro gyro;
  private final Recorder recorder;
  private final Intake intake; 
  private final Shooter shooter; 

  private final Joystick driverController/* , operatorController*/;
  private final AdvancedXboxController operatorController;
  private final SendableChooser<Command> autoChooser;
  
  PrintWriter writer;
  

  private final Shuffleboard shuffleboard;

  double lockAngle = 0;

  private static RobotContainer instance;



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
<<<<<<< HEAD
    gyro = Gyro.getInstance();
    recorder = Recorder.getInstance();
    intake = Intake.getInstance(); 
    shooter = Shooter.getInstance();
=======
   gyro = Gyro.getInstance();
>>>>>>> 7bd801c2de0ab97a40000dc3c6ade2fd8a2c1086

    driverController = new Joystick(ControllerConstants.DRIVER_CONTROLLER_PORT);
    //operatorController = new Joystick(ControllerConstants.OPERATOR_CONTROLLER_PORT);
    operatorController = new AdvancedXboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT, ControllerConstants.CONTROLLER_DEADBAND);

    autoChooser = new SendableChooser<>();
    

    shuffleboard = Shuffleboard.getInstance();

    // Configure the default commands
    configureDefaultCommands();

    // Configure the button bindings
    configureButtonBindings();

    // Configure auto mode
    configureAutoChooser();
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
        instance = new RobotContainer();
    }

    return instance;
  }

  private void configureDefaultCommands() {
    // Arcade Drive
    drive.setDefaultCommand(new LockSwerve(driverController, drive, gyro));

    
  shuffleboard.setDefaultCommand(new RunCommand(() -> {
    shuffleboard.logTargeted();
  }, shuffleboard));


  }


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new Trigger(() -> new JoystickButton(driverController, 12).getAsBoolean())
      .onTrue(new InstantCommand(() -> gyro.resetGyro(), gyro));

    new Trigger(() -> driverController.getTrigger())
      .whileTrue(new Swerve(driverController, drive, gyro))
      .whileFalse(new LockSwerve(driverController, drive, gyro));

    new Trigger(() -> driverController.getRawButton(11))
      .whileTrue(new LockMode(drive))
      .onFalse(new LockSwerve(driverController, drive, gyro));

    new Trigger(() -> operatorController.getAButton())
      .onTrue(new InstantCommand(() -> {
        intake.setOpenLoop(-0.5);
      }, intake))
      .onFalse(new InstantCommand(() -> {
        intake.setOpenLoop(0.0);
      }, intake));
      
    new Trigger(() -> operatorController.getRightTriggerAxis() > 0)
    .onTrue(new InstantCommand(() -> {
    shooter.shooterSetOpenLoop(0.5);
    }, shooter))
    .onFalse(new InstantCommand(() -> {
      shooter.shooterSetOpenLoop(0.0);
    }, shooter));

    new Trigger(() -> operatorController.getLeftTriggerAxis() > 0)
    .onTrue(new pickup(intake))
    .onFalse(new InstantCommand(() -> {
      intake.setOpenLoop(0);
    }, intake));

    new Trigger(() -> operatorController.getBButton()) 
    .onTrue(new RevShooterThenShoot(shooter, intake))
    .onFalse(new InstantCommand(() -> {
      intake.setOpenLoop(0);
      shooter.shooterSetOpenLoop(0);
    }));
  }

  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Nothing", new WaitCommand(0));;
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }
}
