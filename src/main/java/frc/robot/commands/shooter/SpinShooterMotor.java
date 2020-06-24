/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SpinShooterMotor extends CommandBase {
  private XboxController mXboxController;
  /**
   * Creates a new SpinShooterMotor.
   */
  public SpinShooterMotor() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getShooterMotor()); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mXboxController = RobotContainer.getContainer().getDriveController();
    double forward = mXboxController.getY(Hand.kRight); //real: positive
    RobotContainer.getContainer().getShooterMotor().spin(forward);
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
