/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.utility.MathUtils;

public class ClimberArmSpeed extends CommandBase {
  /**
   * Creates a new ClimberArmSpeed.
   */
  public ClimberArmSpeed() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getClimber());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed1 = MathUtils.deadband(RobotContainer.getContainer().getOperatorController().getRawAxis(1));
    double speed2 = MathUtils.deadband(RobotContainer.getContainer().getOperatorController().getRawAxis(5));
    // if(RobotContainer.getContainer().getClimbController().get)
    RobotContainer.getContainer().getClimber().moveArm1(speed1);
    RobotContainer.getContainer().getClimber().moveArm2(-speed2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
