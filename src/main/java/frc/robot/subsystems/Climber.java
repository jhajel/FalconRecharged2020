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
    lowerArm = new CANSparkMax(Constants.CLIMBER1_SPARK,MotorType.kBrushless);
    upperArm = new CANSparkMax(Constants.CLIMBER2_SPARK,MotorType.kBrushless);
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
    upperArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 110);
    upperArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 4);

    lowerArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, -4);
    lowerArm.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -110);

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
    setDefaultCommand(new ClimberArmSpeed());
    SmartDashboard.putBoolean("Left Limit", leftLimit.get());
    SmartDashboard.putBoolean("Right Limit", rightLimit.get());
  }

  public void moveArm1(double speed)
  {
    lowerArm.set(speed);
    
  }

  public void moveArm2(double speed)
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
