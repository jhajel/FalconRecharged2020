/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveForwardDistance extends CommandBase {

  private double d1; 
  private double startAngle;
  private double speed;
  public static final double WHEEL_CIRCUMFERENCE = 4 * Math.PI;
  public static final double TOTAL_SENSOR_POS = 1024;
  public static final double DISTANCE = WHEEL_CIRCUMFERENCE / TOTAL_SENSOR_POS;

  public DriveForwardDistance(double d, double speed) {
    addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
    d1 = DISTANCE * d * 12;
    this.speed = speed;
  }

  @Override
  public void initialize() {
    startAngle = RobotContainer.getContainer().getHolonomicDrivetrain().getNavX().getYaw();
    RobotContainer.getContainer().getHolonomicDrivetrain().resetAllEncoders();
  }

  @Override
  public void execute() {
    RobotContainer.getContainer().getHolonomicDrivetrain().driveForwardDistance(d1, startAngle);
    System.out.println("Drive Position: " + RobotContainer.getContainer().getHolonomicDrivetrain().calculateErrPos(d1));
  }

  @Override
  public boolean isFinished() {
    return Math.abs(RobotContainer.getContainer().getHolonomicDrivetrain().calculateErrPos(d1)) < DISTANCE;
   
  }

  @Override
  public void end(boolean interrupted) {
  }
}