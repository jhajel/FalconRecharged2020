/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotContainer;

public class SpinUnoWheel extends CommandBase {
  /**
   * Creates a new SpinUnoWheel.
   */
  private static int direction;
  private CANEncoder encoder;
  private CANSparkMax moto1;
  public SpinUnoWheel(int direction) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.direction = direction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    addRequirements(RobotContainer.getContainer().getColorPanelSpinner());

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(direction == -1)
    RobotContainer.getContainer().getColorPanelSpinner().spin(-0.5);
    else if(direction == 1)
    RobotContainer.getContainer().getColorPanelSpinner().spin(0.5);

    SmartDashboard.putNumber("SpinUnoWheel" , RobotContainer.getContainer().getColorPanelSpinner().getPosition());
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
