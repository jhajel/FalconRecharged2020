/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Limelight;

public class SwitchLimelightMode extends CommandBase {
  /**
   * Creates a new SwitchLimelightMode.
   */
  private boolean finished;
  public SwitchLimelightMode(Limelight limelight) {
    addRequirements(RobotContainer.getContainer().getLimelight());
    finished = false;

  }

  @Override
  public void initialize() {
    RobotContainer.getContainer().getLimelight().setCamMode();
    finished = false;
    
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.getContainer().getLimelight().switchLimeMode();

    finished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
