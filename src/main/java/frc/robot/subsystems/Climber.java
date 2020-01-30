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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void climberToLimit()
  {
    
  }
}
