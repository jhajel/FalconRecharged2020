/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SenseCell extends CommandBase {
  /**
   * Creates a new SenseCell.
   */
  private boolean seen;
  public SenseCell() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getConveyor());
    seen = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    seen = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if(!RobotContainer.getContainer().getConveyor().getStatus())
    // {
    //   // Command a = new MoveConveyorDistance(-1);
    //   // a.schedule();
    //   seen = true;
    // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    seen = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return seen;
  }
}
