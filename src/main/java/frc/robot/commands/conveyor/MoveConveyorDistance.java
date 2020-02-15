/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class MoveConveyorDistance extends CommandBase {
  /**
   * Creates a new MoveConveyorDistance.
   */
  private double initPos;
  private double targetPos;
  private double currPos;
  public MoveConveyorDistance(double targetPos) { //inches
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getConveyor());
    //inches to ticks
    this.targetPos = (targetPos*16)/(2.5*Math.PI);
    currPos = RobotContainer.getContainer().getConveyor().getPositon();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initPos = RobotContainer.getContainer().getConveyor().getPositon();
    //System.out.println(initPos);
    RobotContainer.getContainer().getConveyor().setConveyerPosition(targetPos+initPos);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currPos = RobotContainer.getContainer().getConveyor().getPositon();
    SmartDashboard.putNumber("target", targetPos+initPos);
    SmartDashboard.putNumber("initPos", initPos);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (currPos) > ((targetPos)+ (initPos));
  }
}
