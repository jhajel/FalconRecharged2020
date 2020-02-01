/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SpinShooterMotor;

public class ShooterMotor extends SubsystemBase {
  /**
   * Creates a new ShooterMotor.
   */
  private TalonFX motor1;
  private TalonFX motor2; 

  public ShooterMotor() {
    motor1  = new TalonFX(35); // ID NUMBER to do
    motor2 = new TalonFX(36); // ID NUMBER to do
    motor2.set(ControlMode.Follower, 35);
    motor2.setInverted(true);
  }

  public void spin(double speed)
  {
    motor1.set(ControlMode.PercentOutput, -speed);
    //motor2.set(ControlMode.PercentOutput, speed);
    
    //motor1.set(ControlMode.MotionMagic, targetPos, DemandType.ArbitraryFeedForward, feedforward);

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setDefaultCommand(new SpinShooterMotor());
  }
}
