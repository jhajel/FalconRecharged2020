/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MoveBothClimberArms extends CommandBase {
  /**
   * Creates a new MoveBothClimberArms.
   */
  private double distance;
  private CANSparkMax masterArm;
  private CANSparkMax slaveArm;
  private double initPos;
  private double targetPos;
  public MoveBothClimberArms(double inch, CANSparkMax a1, CANSparkMax a2) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getClimber());
    masterArm = a1;
    slaveArm = a2;
    distance = inch;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    slaveArm.follow(masterArm,true);
    initPos = masterArm.getEncoder().getPosition();
    targetPos = initPos + (distance*36)/(1.9*Math.PI);
    masterArm.getPIDController().setReference(targetPos,ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("running");  
    System.out.println(" Difference " + Math.abs(targetPos - masterArm.getEncoder().getPosition()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("done");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(targetPos - masterArm.getEncoder().getPosition()) < 0.1;
  }
}
