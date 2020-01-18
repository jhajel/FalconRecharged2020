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

public class Conveyer extends SubsystemBase {
  
  private DigitalInput sensor;
  
  public Conveyer() {
    sensor = new DigitalInput(0);
    
  }

  @Override
  public void periodic() {
    printStatus();
  }

  public boolean getStatus(){
    return sensor.get();
  }
  public void printStatus() {
    SmartDashboard.putBoolean("Sensor State", getStatus());
  }
}
