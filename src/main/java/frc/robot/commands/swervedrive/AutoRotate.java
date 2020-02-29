/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class AutoRotate extends CommandBase {
  /**
   * Creates a new AutoRotate.
   */
  private double angle;
  private double initAngle;
  private double currAngle;
  private double targetAngle;

  public AutoRotate(double angle) { //in degrees
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
    this.angle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initAngle = RobotContainer.getContainer().getHolonomicDrivetrain().getGyroAngle();
    targetAngle = initAngle + angle;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currAngle = RobotContainer.getContainer().getHolonomicDrivetrain().getGyroAngle();
    RobotContainer.getContainer().getHolonomicDrivetrain().holonomicDrive(0, 0, Math.signum(angle)*-.2);
    SmartDashboard.putNumber("init angle", initAngle);
    SmartDashboard.putNumber("curr angle", (currAngle));
    SmartDashboard.putNumber("target angle", (targetAngle));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(0).setTargetAngle(0);
    // RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(1).setTargetAngle(0);
    // RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(2).setTargetAngle(180);
    // RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(3).setTargetAngle(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(currAngle - (targetAngle)) < 5;
  }
}
