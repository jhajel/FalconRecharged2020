/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.climber.ClimberArmSpeed;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  private CANSparkMax motorController1;
  private CANSparkMax motorController2;
  private DoubleSolenoid climberGearLock;

  public Climber() {
    motorController1 = new CANSparkMax(Constants.CLIMBER1_SPARK,MotorType.kBrushless);
    motorController2 = new CANSparkMax(Constants.CLIMBER2_SPARK,MotorType.kBrushless);
    climberGearLock = new DoubleSolenoid(Constants.CLIMBERFORWARD_SOLENOID,Constants.CLIMBERREVERSE_SOLENOID);
    climberGearLock.set(Value.kReverse);
    motorController1.getPIDController().setP(.2);
    motorController1.getPIDController().setI(.0000);
    motorController1.getPIDController().setD(.0002);
    motorController2.getPIDController().setP(.2);
    motorController2.getPIDController().setI(.0000);
    motorController1.getPIDController().setD(.0002);

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
  }

  public void moveArm1(double speed)
  {
    motorController1.set(speed);
    
  }

  public void moveArm2(double speed)
  {
    motorController2.set(speed);
  }

  public CANSparkMax getUpperArm(){
    return motorController2;
  }

  public CANSparkMax getLowerArm(){
    return motorController1;
  }
}
