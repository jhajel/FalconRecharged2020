/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.commands.ShowLimelight;

public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
  public static double limelightx;
  public static double limelighty;
  public static double limelighta;
  public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-color");
  public NetworkTableEntry tx = table.getEntry("tx");
  public NetworkTableEntry ty = table.getEntry("ty");
  public NetworkTableEntry ta = table.getEntry("ta");
  public NetworkTableEntry tv = table.getEntry("tv");

  public Limelight() {
    limelightx = tx.getDouble(0.0);
    limelighty = ty.getDouble(0.0);
    limelighta = ta.getDouble(0.0);
    
    setDefaultCommand(new ShowLimelight(this));
  }

  public void printInfo(){
    SmartDashboard.putNumber("Limelight X", limelightx);
    SmartDashboard.putNumber("Limelight Y", limelighty);
    SmartDashboard.putNumber("Limelight A", limelighta);
  }

  public double getLEDMode()  {
    return table.getEntry("ledMode").getDouble(3);
  }

  public double getCamMode()  {
    return table.getEntry("camMode").getDouble(1);
  }

  public void setLEDMode(int modeNum) {
    table.getEntry("ledMode").setDouble(modeNum);
    table.getEntry("camMode").setDouble(0);
      SmartDashboard.putNumber("CAMMODE", modeNum);
    } 
  
  public void setCamMode(int modeNum) {
      table.getEntry("camMode").setDouble(modeNum);
      if(modeNum==1)
        table.getEntry("ledMode").setDouble(1);
      SmartDashboard.putNumber("CAMMODE", modeNum);
    }
  


  

  @Override
  public void periodic() {
    printInfo();
  }
}
