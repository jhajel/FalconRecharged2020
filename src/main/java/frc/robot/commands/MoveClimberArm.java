/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MoveClimberArm extends CommandBase {
  /**
   * Creates a new MoveClimberArm.
   */
  private double initPos;
  private double targetPosition;
  private CANSparkMax arm;
  private double inch;
  public MoveClimberArm(double inches, CANSparkMax arm) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = arm;
    this.inch = inches;
    addRequirements(RobotContainer.getContainer().getClimber());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initPos = arm.getEncoder().getPosition();
    targetPosition = initPos + (inch*36)/(1.9*Math.PI); // 1 inch = 6.03 ticks
    arm.getPIDController().setReference(targetPosition, ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("running");  
    System.out.println(" Difference " + Math.abs(targetPosition - arm.getEncoder().getPosition()));
    SmartDashboard.putNumber("Diff", targetPosition);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("done");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return Math.abs(targetPosition - arm.getEncoder().getPosition()) < 0.1;

  }
}
