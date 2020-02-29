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

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private CANSparkMax intakeController;
  private DoubleSolenoid rightSolenoid;
  private DoubleSolenoid leftSolenoid;

  public Intake() {
    intakeController = new CANSparkMax(Constants.INTAKE_SPARK, MotorType.kBrushless);
    rightSolenoid = new DoubleSolenoid(Constants.INTAKEFORWARD_SOLENOID, Constants.INTAKEREVERSE_SOLENOID);
    leftSolenoid = new DoubleSolenoid(Constants.INTAKEFORWARD_SOLENOID2, Constants.INTAKEREVERSE_SOLENOID2);
    leftSolenoid.set(DoubleSolenoid.Value.kReverse);
    rightSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void setSpeed(double speed){
    intakeController.set(speed);
  }

  public void toggleIntakeSolenoidMode(){
    if(rightSolenoid.get() == (DoubleSolenoid.Value.kReverse)){
      rightSolenoid.set(DoubleSolenoid.Value.kForward);
      leftSolenoid.set(DoubleSolenoid.Value.kForward);

    }
    else{
      rightSolenoid.set(DoubleSolenoid.Value.kReverse);
      leftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //setDefaultCommand(new IntakeSpeed(-.5));
  }
}
