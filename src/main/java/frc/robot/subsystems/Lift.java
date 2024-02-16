package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.Constants.LiftConstants;;

public class Lift implements Subsystem  {
    
    private final CANSparkMax leftLift, rightLift; 

    private final DutyCycleEncoder leftEncoder, rightEncoder; 

    private static Lift instance; 

    private Lift() {
        leftLift = new CANSparkMax(LiftConstants.LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightLift = new CANSparkMax(LiftConstants.RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftEncoder = new DutyCycleEncoder(1);
        rightEncoder = new DutyCycleEncoder(0);

    }

    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }

        return instance; 
    } 

    private double maxPos = 6;

    double demandMutable = 0;

    public void setLiftOpenLoop(double demand) {
        

        if (getLeftPosition() > 0 && getLeftPosition() < maxPos) {
            System.out.println("Left " + demand);
            leftLift.set(demand);
        } else if (getLeftPosition() < 0) {
            if (demand >= 0) {
                System.out.println("Left 0");
                leftLift.set(0);
            } else {
                System.out.println("Left " + demand);
                leftLift.set(demand);
            }
        } else if (getLeftPosition() > maxPos) {
            if (demand <= 0) {
                System.out.println("Left 0");
                leftLift.set(0);
        } else {
                System.out.println("Left " + demand);
                leftLift.set(demand);
            }
        }

        if (getRightPosition() > 0 && getRightPosition() < maxPos) {
            System.out.println("Right " + demand);
            rightLift.set(demand);

        } else if (getRightPosition() < 0) {
            if (demand >= 0) {
                System.out.println("Right 0");
                rightLift.set(0);
            } else {
                System.out.println("Right " + demand);
                rightLift.set(demand);
            }
        } else if (getRightPosition() > maxPos) {
            if (demand <= 0) {
                System.out.println("Right 0");
                rightLift.set(0);
        } else {
                System.out.println("Right " + demand);
                rightLift.set(demand);
            }
        }


       
    }

    public void resetLeftLift(double demand) {
        leftLift.set(demand);
    }

    public void resetRightLift(double demand) {
        rightLift.set(demand);
    }

    public double getRightPosition() {
        return -rightEncoder.getDistance();
        
    }
    public double getLeftPosition() {
        return leftEncoder.getDistance();
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
}

}
