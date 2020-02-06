/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterMotor;

public class SetShooterSpeed extends CommandBase {
  /**
   * Creates a new SetShooterSpeed.
   */
  private double speed; 
  private ShooterMotor shooterMotor;

  public SetShooterSpeed(double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.speed = speed;
    shooterMotor = RobotContainer.getContainer().getShooterMotor();
    addRequirements(shooterMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterMotor.setSpeed(speed);  //ticks per 100ms??

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Current Speed: "+ shooterMotor.getSpeed() + " Desired Speed: " + speed + " Difference: "+(speed - shooterMotor.getSpeed()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterMotor.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
