/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class MoveConveyorDistance extends CommandBase {
  /**
   * Creates a new MoveConveyorDistance.
   */
  //private double initPos;
  //private double targetPos;
  private double speed;
  //private double currPos;
  public MoveConveyorDistance(double speed) { //inches
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getConveyor());
    //inches to ticks
    this.speed = speed;
    // currPos = RobotContainer.getContainer().getConveyor().getPositon();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // initPos = RobotContainer.getContainer().getConveyor().getPositon();
    // RobotContainer.getContainer().getConveyor().setConveyerPosition(targetPos+initPos);
    RobotContainer.getContainer().getConveyor().setConveyerSpeed(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //currPos = RobotContainer.getContainer().getConveyor().getPositon();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.getContainer().getConveyor().setConveyerSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.getContainer().getConveyor().getDistance() > 3;
  }
}
