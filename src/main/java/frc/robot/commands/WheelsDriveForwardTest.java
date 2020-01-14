/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WheelsDriveForwardTest extends CommandBase {
  private double driveTime = 1;
  private double d1;
  private double startAngle;
  public static final double WHEEL_CIRCUMFERENCE = 4 * Math.PI;
  public static final double TOTAL_SENSOR_POS = 64;
  public static final double DISTANCE = WHEEL_CIRCUMFERENCE / TOTAL_SENSOR_POS;
  private double initAngle0;
  private double initAngle1;
  private double initAngle2;
  private double initAngle3;
  private double curPos;
  private double pos1;
  private double pos2;
  private double pos3;
  private double pos4;
  private double angle;
  private double targetPos;
  private SwerveDriveSubsystem swerveDriveSystem;

  public WheelsDriveForwardTest(double d, double targetAngle) {
      swerveDriveSystem = RobotContainer.getContainer().getHolonomicDrivetrain();
      addRequirements(swerveDriveSystem);
       // inches to ticks
       d1 = d;
       targetPos = (d1* 8.5714)/(4*Math.PI);
      System.out.println("Starting Drive Forward");
    angle = targetAngle;
    
      // initAngle0 = Robot.swerveDriveSubsystem.m1.getTargetAngle();
      // initAngle1 = Robot.swerveDriveSubsystem.m2.getTargetAngle();
      // initAngle2 = Robot.swerveDriveSubsystem.m3.getTargetAngle();
      // initAngle3 = Robot.swerveDriveSubsystem.m4.getTargetAngle();
  }

  public void driveForward() 
  {
    //System.out.println("Field orientation is " + (Robot.swerveDriveSubsystem.isFieldOriented() ? "true" : "false"));
    // Robot.swerveDriveSubsystem.driveForwardDistance(d1, startAngle);
    //isEnded = false;

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    swerveDriveSystem.m0.getPIDController().setReference(pos1 + d1, ControlType.kPosition);
    swerveDriveSystem.m1.getPIDController().setReference(pos2 + d1, ControlType.kPosition);
    swerveDriveSystem.m2.getPIDController().setReference(pos3 + d1, ControlType.kPosition);
    swerveDriveSystem.m3.getPIDController().setReference(pos4 + d1, ControlType.kPosition);
    System.out.println("Driving Wheels Forward");
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    pos1 = swerveDriveSystem.m0.getDriveMotor().getEncoder().getPosition();
    curPos = swerveDriveSystem.m0.getDriveMotor().getEncoder().getPosition();
    pos2 = swerveDriveSystem.m1.getDriveMotor().getEncoder().getPosition();
    pos3 = swerveDriveSystem.m2.getDriveMotor().getEncoder().getPosition();
    pos4 = swerveDriveSystem.m3.getDriveMotor().getEncoder().getPosition();
    startAngle = swerveDriveSystem.getNavX().getYaw();
    swerveDriveSystem.resetAllEncoders();
    //Robot.swerveDriveSubsystem.setFieldOriented(false);
    swerveDriveSystem.driveForwardDistance(d1,angle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    curPos = swerveDriveSystem.m0.getDriveMotor().getEncoder().getPosition();
    boolean ya = isFinished();
    System.out.println( "Target Pos: " + (targetPos+pos1) +  "Actual Pos: " + curPos + " Drive Ended: " + ya);
    // Robot.swerveDriveSubsystem.m1.setTargetAngle(startAngle);
    // Robot.swerveDriveSubsystem.m2.setTargetAngle(startAngle);
    // Robot.swerveDriveSubsystem.m3.setTargetAngle(startAngle);
    // Robot.swerveDriveSubsystem.m4.setTargetAngle(startAngle);
    //driveForward();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return (Math.abs(curPos - (targetPos+pos1)) < 0.5);
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ending Drive Forward");
    swerveDriveSystem.setFieldOriented(true);
  }
}