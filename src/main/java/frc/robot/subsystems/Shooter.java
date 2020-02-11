/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */

  private boolean isRetracted;

  public Shooter() {
   
    isRetracted = true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void switchPistonMode(){
    if(!isRetracted) {

      isRetracted = !isRetracted;
    }
    else {

      isRetracted = !isRetracted;
    }
  }

  public boolean getRetracted()
  {
    return isRetracted;
  }
}
