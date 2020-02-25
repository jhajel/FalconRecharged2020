/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SetWheelAngle extends CommandBase {
  /**
   * Creates a new SetWheelAngle.
   */
  Timer time;
  double angle;
  public SetWheelAngle(double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
    time = new Timer();
    this.angle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time.reset();
    time.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(0).setTargetAngle(angle);
    RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(1).setTargetAngle(angle);
    RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(2).setTargetAngle(180+angle);
    RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(3).setTargetAngle(angle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    time.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return time.get() > .25;
  }
}
