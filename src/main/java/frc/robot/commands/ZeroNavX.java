/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ZeroNavX extends CommandBase {

  public ZeroNavX() {
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    RobotContainer.getContainer().getHolonomicDrivetrain().zeroGyro();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void end(boolean interrupted) {
  }
}
