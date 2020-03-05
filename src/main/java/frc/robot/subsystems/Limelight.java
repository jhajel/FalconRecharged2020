/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight extends SubsystemBase  {
  /**
   * Creates a new Limelight.
   */

  public static double intakeX;
  public static double intakeY;
  public static double intakeA;
  public static double shooterX;
  public static double shooterY;
  public static double shooterA;
  
  private SendableChooser<Integer> intakeMode;

  public NetworkTable intakeLL = NetworkTableInstance.getDefault().getTable("limelight-intake"); // 10.55.7.12
  public NetworkTable shooterLL = NetworkTableInstance.getDefault().getTable("limelight-shooter"); // 10.55.7.11
  public NetworkTableEntry inX = intakeLL.getEntry("tx");
  public NetworkTableEntry inY = intakeLL.getEntry("ty");
  public NetworkTableEntry inA = intakeLL.getEntry("ta");
  public NetworkTableEntry shX = shooterLL.getEntry("tx");
  public NetworkTableEntry shY = shooterLL.getEntry("ty");
  public NetworkTableEntry shA = shooterLL.getEntry("ta");

  public Limelight() {
    intakeX = inX.getDouble(0.0);
    intakeY = inY.getDouble(0.0);
    intakeA = inA.getDouble(0.0);
    shooterA = shA.getDouble(0.0);
    shooterX = shX.getDouble(0.0);
    shooterY = shY.getDouble(0.0);
    intakeMode = new SendableChooser<Integer>();
    intakeMode.setDefaultOption("Vison Mode", 0);
    intakeMode.addOption("Cam Mode", 1);
    SmartDashboard.putData(intakeMode);
  }

  public void printInfo() {
    // SmartDashboard.putNumber("Limelight X", limelightx);
    // SmartDashboard.putNumber("Limelight Y", limelighty);
    // SmartDashboard.putNumber("Limelight A", limelighta);
    // SmartDashboard.putNumber("Current Pipeline", table.getEntry("pipeline").getDouble(-1));
  }

  public void setIntakeCam(int x)
  {
    if(x == 1)
    {
      intakeLL.getEntry("ledMode").setDouble(1);
      intakeLL.getEntry("camMode").setDouble(x);
    }
    else
    {
      intakeLL.getEntry("ledMode").setDouble(3);
      intakeLL.getEntry("camMode").setDouble(x);
    }
  }

  @Override
  public void periodic() {
    
    setIntakeCam(intakeMode.getSelected());
  }
}
