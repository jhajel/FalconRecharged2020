/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.conveyor.SenseCell;

import com.playingwithfusion.TimeOfFlight;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Conveyor extends SubsystemBase {
  
  private TimeOfFlight sensor;
  private CANSparkMax indexer;
  private CANEncoder encoder;
  private CANPIDController pidController;
  private boolean ignore;
  
  public Conveyor() {
    sensor = new TimeOfFlight(2);// to change ID for TOF sensor go to...      ipOfRoboRio:5812
    sensor.setRangingMode(TimeOfFlight.RangingMode.Short, 0.24);
    indexer = new CANSparkMax(Constants.CONVEYOR_SPARK, MotorType.kBrushless);
    encoder = indexer.getEncoder();
    pidController = indexer.getPIDController();
    pidController.setP(.2);
    pidController.setI(.0001);
    ignore = false;
    //indexer.setSmartCurrentLimit(20);
  }

  @Override
  public void periodic() {
    printStuff();
  }

  private void printStuff() {
    SmartDashboard.putBoolean("Sensor State", getStatus());
    SmartDashboard.putNumber("Ticks", encoder.getPosition());
    SmartDashboard.putNumber("Distance", getDistance());
    SmartDashboard.putBoolean("Ignored", isIgnored());
    SmartDashboard.putNumber("Conveyor Current", indexer.getOutputCurrent());
  }

  public boolean getStatus() {
    return getDistance() < 5.5;
  }

  public boolean isIgnored() {
    return ignore;
  }

  public void toggleIgnore()
  {
    ignore = !ignore;
  }

  public double getDistance()
  {
    return sensor.getRange()*0.0393701;
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
