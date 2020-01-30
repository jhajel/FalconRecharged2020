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
import frc.robot.commands.IntakeSpeed;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private CANSparkMax intakeController;
  private DoubleSolenoid intakeSolenoid;

  public Intake() {
    intakeController = new CANSparkMax(Constants.INTAKE_SPARK, MotorType.kBrushless);
    intakeSolenoid = new DoubleSolenoid(Constants.INTAKEFORWARD_SOLENOID, Constants.INTAKEREVERSE_SOLENOID);
  }

  public void setSpeed(double speed){
    intakeController.set(speed);
  }

  public void switchintakeSolenoidMode(){
    if(intakeSolenoid.get() == (DoubleSolenoid.Value.kForward)){
      intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    else if(intakeSolenoid.get() == (DoubleSolenoid.Value.kReverse)){
      intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else{
      intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setDefaultCommand(new IntakeSpeed(-.5));
  }
}
