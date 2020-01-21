/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WheelsDriveForwardTest extends CommandBase {
  private double targetAngle;
  private double targetPos;
  private double curPos; // current position of module 0
  public WheelsDriveForwardTest(double d, double targetAngle) { //inches and degrees
      
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
    targetPos = d;
    System.out.println("Starting Drive Forward");
    this.targetAngle = targetAngle;
    curPos = RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(0).getPosition();
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    RobotContainer.getContainer().getHolonomicDrivetrain().resetAllEncoders();
    RobotContainer.getContainer().getHolonomicDrivetrain().driveForwardDistance(targetPos,targetAngle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    curPos = RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(0).getPosition();
    System.out.println( "Target Pos: " + (targetPos) +  "Actual Pos: " + curPos + " Drive Ended: " + isFinished());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return (Math.abs(curPos - targetPos) < 0.5);
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ending Drive Forward");
  }
}