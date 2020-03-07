/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class MoveBothClimberArms extends CommandBase {
  /**
   * Creates a new MoveBothClimberArms.
   */
  private double distance;
  private TalonFX masterArm;
  private TalonFX slaveArm;
  // private CANSparkMax masterArm;
  // private CANSparkMax slaveArm;
  private double initPos;
  private double targetPos;
  
  public MoveBothClimberArms(double inch, TalonFX a1, TalonFX a2) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getClimberT());
    masterArm = a1;
    slaveArm = a2;
    distance = inch;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    slaveArm.set(ControlMode.Follower, Constants.CLIMBER1_TALON);
    // slaveArm.follow(masterArm,true);
    initPos = masterArm.getSelectedSensorPosition();
    targetPos = initPos + (distance*36)/(1.9*Math.PI);
    masterArm.set(TalonFXControlMode.Position, targetPos);
    //masterArm.getPIDController().setReference(targetPos,ControlType.kPosition);
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
    return Math.abs(targetPos - masterArm.getSelectedSensorPosition()) < 0.1;
  }
}
