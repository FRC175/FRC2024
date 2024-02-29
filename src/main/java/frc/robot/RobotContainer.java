// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CyclicBarrier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.Climb;
import frc.robot.commands.DeployArm;
import frc.robot.commands.SetArmPosition;
import frc.robot.commands.pickup;
import frc.robot.commands.AutoModes.ShootFromFrontSubwoofer;
import frc.robot.commands.AutoModes.ShootFrontCollectNoteScore;
import frc.robot.commands.Drive.LockMode;
import frc.robot.commands.Drive.LockSwerve;
import frc.robot.commands.Drive.Swerve;
import frc.robot.commands.Drive.SwerveToDist;
import frc.robot.commands.Shooter.RevShooterThenShoot;
import frc.robot.subsystems.Drive.Drive;
import frc.robot.utils.Controller;
import frc.robot.subsystems.Intake; 
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Arm;

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
  private final Intake intake; 
  private final Shooter shooter; 
  private final Lift lift; 
  private final Arm arm; 
  private final Controller driverController/* , operatorController*/;
  private final XboxController operatorController;
  private final SendableChooser<Command> autoChooser;
  
  PrintWriter writer;
  

  double lockAngle = 0;

  private static RobotContainer instance;



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drive = Drive.getInstance();
    intake = Intake.getInstance(); 
    shooter = Shooter.getInstance();
    lift = Lift.getInstance(); 
    arm = Arm.getInstance();

    driverController = new Controller(new Joystick(ControllerConstants.DRIVER_CONTROLLER_PORT));
    //operatorController = new Joystick(ControllerConstants.OPERATOR_CONTROLLER_PORT);
    operatorController = new XboxController(ControllerConstants.OPERATOR_CONTROLLER_PORT);

    autoChooser = new SendableChooser<>();
    


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
    drive.setDefaultCommand(new Swerve(driverController, drive));
    // arm.setDefaultCommand(new RunCommand(() -> {
    //   double input = 0;
    //   if (operatorController.getRightY() > 0.05) {
    //     input = 0.1;
    //   } else if (operatorController.getRightY() < -0.05) {
    //     input = -0.1;
    //   }
    //   arm.setArmOpenLoop(input);
    // }, arm));
    // arm.setDefaultCommand(new SetArmPosition(arm, 0.2, 0.4, false));


  }


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Driver Joystick Button 12: Reset Gyro 
    new Trigger(() -> driverController.getB12())
      .onTrue(new InstantCommand(() -> drive.resetGyro()));

    new Trigger(() -> driverController.getTrigger())
      .whileTrue(new LockSwerve(driverController, drive))
      .whileFalse(new Swerve(driverController, drive));
    
    //Driver Joystick Button 11: Lock Mode 
    new Trigger(() -> driverController.getA11())
      .whileTrue(new LockMode(drive))
      .onFalse(new Swerve(driverController, drive));

    // Operator Controller A Button: Reverse Intake 
    new Trigger(() -> operatorController.getAButton())
      .onTrue(new InstantCommand(() -> {
        intake.setOpenLoop(-0.5);
        shooter.shooterSetOpenLoop(-0.5, -0.5);
      }, intake))
      .onFalse(new InstantCommand(() -> {
        intake.setOpenLoop(0.0);
        shooter.shooterSetOpenLoop(0, 0);
      }, intake));

    // Operator Controller B Button: Amp 
    new Trigger(() -> operatorController.getBButton())
    .onTrue(new InstantCommand(() -> {
      intake.setOpenLoop(0.25);
      shooter.shooterSetOpenLoop(0.25, 0.25);
    }, intake, shooter))
    .onFalse(new InstantCommand(() -> {
      intake.setOpenLoop(0.0);
      shooter.shooterSetOpenLoop(0, 0);
    }, intake, shooter));
    
    // // Operator Controller B Button: Shoot in Speaker 
    // new Trigger(() -> operatorController.getBButton() )
    // .onTrue(new InstantCommand(() -> {
    // shooter.shooterSetOpenLoop(0.5,0.5);
    // // System.out.println("const");
    // }, shooter))
    // .onFalse(new InstantCommand(() -> {
    //   shooter.shooterSetOpenLoop(0.0, 0.0);
    // }, shooter));

    // Operator Controller Left Trigger: Intake 
    new Trigger(() -> operatorController.getLeftTriggerAxis() > 0)
    .onTrue(new pickup(intake))
    .whileTrue(new InstantCommand(() -> {
      if (intake.isNotePresent()) {
        operatorController.setRumble(RumbleType.kBothRumble, 1.0);
      } else {
        operatorController.setRumble(RumbleType.kBothRumble, 0);
      }
    }))
    .onFalse(new InstantCommand(() -> {
      intake.setOpenLoop(0);
      operatorController.setRumble(RumbleType.kBothRumble, 0);
    }, intake));

    // new Trigger (() -> operatorController.getPOV() == 180)
    // .onTrue(new InstantCommand(() -> {
    //   intake.setOpenLoop(-1);
    // }));
    // selma stinks; rym too
    // Operator Controller Right Trigger: Shoot into Speaker 
    new Trigger(() -> operatorController.getRightTriggerAxis() > 0.5) 
    // .onTrue(new RevShooterThenShoot(shooter, intake))
    // .onFalse(new InstantCommand(() -> {
    //   intake.setOpenLoop(0);
    //   shooter.shooterSetOpenLoop(0, 0);
    // }, shooter, intake));
    .onTrue(new InstantCommand(() -> {
      intake.setOpenLoop(0.25);
      shooter.shooterSetOpenLoop(0.25, 0.25);
    }, intake, shooter))
    .onFalse(new InstantCommand(() -> {
      intake.setOpenLoop(0.0);
      shooter.shooterSetOpenLoop(0, 0);
    }, intake, shooter));

    // // Operator Controller X Button: Climb 
    // new Trigger(() -> operatorController.getXButton())
    // .onTrue(new Climb(lift))
    // .onFalse(new InstantCommand(() -> {
    //   lift.setLiftOpenLoop(0);
    // }));

    // // Operator Controller Right Stick: Test Arm
    // new Trigger(() -> operatorController.getRightY() > 0.05)
    //   .onTrue(new DeployArm(arm, Math.abs(operatorController.getRightY()) > 0.05 ? 0.1 : -0.1))
    //   .onFalse(new InstantCommand( () -> {
    //     arm.setArmOpenLoop(0);
    //     SmartDashboard.putNumber("Arm Position", arm.getPosition());
    //   }, arm));

    // new Trigger(() -> operatorController.getBButton())
    //   .onTrue(new InstantCommand(() -> arm.resetEncoders(), arm));

    // // Operator Controller Y Button: Deploy Arm
    // new Trigger(() -> operatorController.getYButton())
    // .onTrue(new DeployArm(arm))
    // .onFalse(new InstantCommand(() -> {
    //   arm.setArmOpenLoop(0);
    // }));

    // Operator DPad Down: Set Arm Intake Position
    new Trigger(() -> operatorController.getPOV() == 180)
      // .onTrue(new SetArmPosition(arm, 0.2, false, ArmConstants.INTAKE));
      .onTrue(new InstantCommand(() -> {
        arm.setArmGoalPosition(ArmConstants.INTAKE);
      }));

    // Operator Dpad Up: Set Arm Amp Position 
    new Trigger(() -> operatorController.getPOV() == 90)
      .onTrue(new InstantCommand(() -> {arm.setArmGoalPosition(ArmConstants.AMP);}));

    // Operator DPad Right: Set Arm Speaker Position 
    new Trigger(() -> operatorController.getPOV() == 0)
      .onTrue(new InstantCommand(() -> {arm.setArmGoalPosition(ArmConstants.SPEAKER);}));

    // Operator DPad Left: Set Arm Rest Position
    new Trigger(() -> operatorController.getPOV() == 270)
      // .onTrue(new SetArmPosition(arm, 0.2, false, ArmConstants.REST));
      .onTrue(new InstantCommand(() -> {arm.setArmGoalPosition(ArmConstants.REST);}));
  }

  private void configureAutoChooser() {
    autoChooser.setDefaultOption("Nothing", new WaitCommand(0));
    autoChooser.addOption("Drive to Park", new SwerveToDist(drive, 0.3, 45, 2000));
    autoChooser.addOption("Shoot from Subwoofer", new ShootFromFrontSubwoofer(drive, shooter, intake, arm));
    autoChooser.addOption("Shoot from Subwoofer, grab note, shoot again", new ShootFrontCollectNoteScore(drive, shooter, intake, arm));
    SmartDashboard.putData(autoChooser);
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

