/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.climber.ClimberArmSpeed;
//import jdk.vm.ci.meta.Constant;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  private CANSparkMax lowerArm;
  private CANSparkMax upperArm;
  private DoubleSolenoid climberGearLock;
  private DigitalInput leftLimit;
  private DigitalInput rightLimit;

  public Climber() {
    upperArm = new CANSparkMax(Constants.CLIMBER1_SPARK,MotorType.kBrushless); //left
    lowerArm = new CANSparkMax(Constants.CLIMBER2_SPARK,MotorType.kBrushless); //right
    climberGearLock = new DoubleSolenoid(Constants.CLIMBERFORWARD_SOLENOID,Constants.CLIMBERREVERSE_SOLENOID);
    leftLimit = new DigitalInput(1);
    rightLimit = new DigitalInput(2);
    climberGearLock.set(Value.kReverse);  
    lowerArm.getPIDController().setP(.2); // make faster .3?
    lowerArm.getPIDController().setI(.0000);
    lowerArm.getPIDController().setD(.0002);
    upperArm.getPIDController().setP(.2); // make faster .3?
    upperArm.getPIDController().setI(.0000);
    upperArm.getPIDController().setD(.0002);
    //lowerArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 0);
    upperArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, -2);
    upperArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -119);

    lowerArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, -2);
    lowerArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -119);

    upperArm.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    upperArm.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    lowerArm.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    lowerArm.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    upperArm.getEncoder().setPosition(0);
    lowerArm.getEncoder().setPosition(0);

  }

  public void toggleClimberGearLock() {
    if(climberGearLock.get() == Value.kReverse)
    {
      climberGearLock.set(Value.kForward);
    }
    else {
      climberGearLock.set(Value.kReverse);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Left Limit", leftLimit.get());
    SmartDashboard.putBoolean("Right Limit", rightLimit.get());
    SmartDashboard.putNumber("Left Encoder Pos", upperArm.getEncoder().getPosition());
    SmartDashboard.putNumber("Right Encoder Pos", lowerArm.getEncoder().getPosition());
   }

  public void moveLowerArm(double speed)
  {
    lowerArm.set(speed);
    
  }

  public void moveUpperArm(double speed)
  {
    upperArm.set(speed);
  }

  public CANSparkMax getUpperArm(){
    return upperArm;
  }

  public CANSparkMax getLowerArm(){
    return lowerArm;
  }
}
