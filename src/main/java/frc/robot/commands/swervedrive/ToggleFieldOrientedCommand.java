/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class ToggleFieldOrientedCommand extends InstantCommand {
  /**
   * Creates a new ToggleFieldOriented.
   */
  public ToggleFieldOrientedCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.getContainer().getHolonomicDrivetrain().setFieldOriented(!RobotContainer.getContainer().getHolonomicDrivetrain().isFieldOriented());
  }

}
