/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LEDMode extends CommandBase {
  
  private Limelight limelight;
  private boolean finished = false;
  public LEDMode(Limelight limelight) {
    this.limelight = limelight;
    addRequirements(limelight);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    limelight.setLEDMode(3);
    finished = true;
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}