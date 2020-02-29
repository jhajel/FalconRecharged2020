/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ZeroNavX extends InstantCommand {

  public ZeroNavX() {
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
  }

  @Override
  public void initialize() {
    RobotContainer.getContainer().getHolonomicDrivetrain().zeroGyro();
  }

}
