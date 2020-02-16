/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.conveyor.SenseCell;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Conveyor extends SubsystemBase {
  
  private DigitalInput sensor;
  private CANSparkMax indexer;
  private CANEncoder encoder;
  private CANPIDController pidController;
  
  public Conveyor() {
    sensor = new DigitalInput(0);
    indexer = new CANSparkMax(Constants.CONVEYOR_SPARK, MotorType.kBrushless);// not correct
    encoder = indexer.getEncoder();
    pidController = indexer.getPIDController();
    pidController.setP(.2);
    pidController.setI(.0001);
  }

  @Override
  public void periodic() {
    printStatus();
    setDefaultCommand(new SenseCell());
  }

  public boolean getStatus(){
    return sensor.get();
  }

  public void printStatus() {
    SmartDashboard.putBoolean("Sensor State", getStatus());
  }

  public void setConveyerPosition(double ticks) 
  {
    pidController.setReference(ticks, ControlType.kPosition); 
  }

  public void setConveyerSpeed(double speed)
  {
    indexer.set(speed);
  }

  public double getPositon()
  {
    return encoder.getPosition();
  }

}
