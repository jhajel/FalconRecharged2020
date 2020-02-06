/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class ShooterSwitchArmMode extends CommandBase {
  /**
   * Creates a new ShooterArmSwitchMode.
   */
  private int counter;
  private Timer timer;
  public ShooterSwitchArmMode() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getShooter());
    counter = 0;
    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //RobotContainer.getContainer().getShooter().switchPistonMode();
    RobotContainer.getContainer().getShooter().switchPistonMode();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer.get() >= .25)
    {
      RobotContainer.getContainer().getShooter().switchPistonMode();
      counter++;
      timer.reset();
      timer.start();
    }
    
    //RobotContainer.getContainer().getShooter().switchPistonMode();
    
    System.out.println(counter);
    
    
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    counter = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return counter == 4;
  }
}
