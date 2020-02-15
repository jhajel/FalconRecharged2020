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
  //private DoubleSolenoid piston;
  //private DoubleSolenoid piston2;
  private boolean isRetracted;

  public Shooter() {
    // piston = new DoubleSolenoid(Constants.SHOOTER_FORWARD_SOLENOID, Constants.SHOOTER_REVERSE_SOLENOID);
    // piston2 = new DoubleSolenoid(Constants.SHOOTER2_FORWARD_SOLENOID, Constants.SHOOTER2_REVERSE_SOLENOID);
    // isRetracted = true;
    // piston.set(DoubleSolenoid.Value.kReverse);
    // piston2.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void switchPistonMode(){
    // if(!isRetracted) {
    //   piston.set(DoubleSolenoid.Value.kReverse);
    //   piston2.set(DoubleSolenoid.Value.kReverse);
    //   isRetracted = !isRetracted;
    // }
    // else {
    //   piston.set(DoubleSolenoid.Value.kForward);
    //   piston2.set(DoubleSolenoid.Value.kForward);
    //   isRetracted = !isRetracted;
    // }
  }

  public boolean getRetracted()
  {
    return isRetracted;
  }
}
