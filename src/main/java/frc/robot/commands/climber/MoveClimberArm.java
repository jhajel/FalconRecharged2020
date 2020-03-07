/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MoveClimberArm extends CommandBase {
  /**
   * Creates a new MoveClimberArm.
   */
  private double initPos;
  private double targetPosition;
  private TalonFX arm;
  //private CANSparkMax arm;
  private double inch;
  public MoveClimberArm(double inches, TalonFX arm) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = arm;
    this.inch = inches;
    addRequirements(RobotContainer.getContainer().getClimberT());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initPos = arm.getSelectedSensorPosition();
    targetPosition = initPos + (inch*36)/(1.9*Math.PI); // 1 inch = 6.03 ticks
    arm.set(TalonFXControlMode.Position, targetPosition);
    //arm.getPIDController().setReference(targetPosition, ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return Math.abs(targetPosition - arm.getSelectedSensorPosition()) < 0.1;

  }
}
