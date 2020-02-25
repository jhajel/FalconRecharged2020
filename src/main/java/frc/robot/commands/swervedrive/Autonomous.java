/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

public class Autonomous extends CommandBase {
  /**
   * Creates a new Autonomous.
   */
  private RamseteController controller;
  private Trajectory trajectory;
  private SwerveDriveOdometry odometry;
  private SwerveDriveKinematics kinematics;
  private SwerveDriveSubsystem drivetrain;
  private Timer time;
  private double initGyro;

  //Speed constant calulated using 19251 as ticks/rev, 0.3048 ft to m conversion, 2pi*(1/6) is rev tp ft conversion
  public static final double SPEEDCONSTANT = (2*Math.PI*(1.0/6)*0.3048)/19251; //used to swtich from ticks to meters
  public double initPos[];

  public Autonomous(Trajectory trajectory) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = RobotContainer.getContainer().getHolonomicDrivetrain();
    this.trajectory = trajectory;
    addRequirements(drivetrain);
    controller = new RamseteController(2.5, 1);
    kinematics = new SwerveDriveKinematics(
      new Translation2d(0.28575, 0.28575), //(+,+)
      new Translation2d(0.28575, -0.28575), //(+,-)
      new Translation2d(-0.28575, -0.28575), //(-,-)
      new Translation2d(-0.28575, 0.28575)); //(-,+)
    odometry = new SwerveDriveOdometry(kinematics,new Rotation2d(Math.toRadians(0)));
    time = new Timer();
    initPos = new double[4];
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    odometry.resetPosition(new Pose2d(0, 0, new Rotation2d(0)), new Rotation2d(0));
    time.start();
    drivetrain.setFieldOriented(false);
    drivetrain.setIsAuto(true);
    drivetrain.swapPIDSlot(1);
    drivetrain.getSwerveModule(0).setTargetAngle(0);
    drivetrain.getSwerveModule(1).setTargetAngle(0);
    drivetrain.getSwerveModule(2).setTargetAngle(180);
    drivetrain.getSwerveModule(3).setTargetAngle(0);
    initPos[0] = drivetrain.getSwerveModule(0).getCurrentAngle();
    initPos[1] = drivetrain.getSwerveModule(1).getCurrentAngle();
    initPos[2] = drivetrain.getSwerveModule(2).getCurrentAngle();
    initPos[3] = drivetrain.getSwerveModule(3).getCurrentAngle();
    initGyro = drivetrain.getGyroAngle();
    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("time: " + time.get());

    //10* is to give odometry speed in m/s instead of m/100ms
    odometry.update(new Rotation2d(Math.toRadians(drivetrain.getGyroAngle() - initGyro)), 
      new SwerveModuleState(10*drivetrain.getSwerveModule(0).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(0).getCurrentAngle()-initPos[0]))),
      new SwerveModuleState(10*drivetrain.getSwerveModule(1).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(1).getCurrentAngle()-initPos[1]))),
      new SwerveModuleState(10*drivetrain.getSwerveModule(2).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(2).getCurrentAngle()-initPos[2]))),
      new SwerveModuleState(10*drivetrain.getSwerveModule(3).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT, new Rotation2d(Math.toRadians(drivetrain.getSwerveModule(3).getCurrentAngle()-initPos[3]))));
    Trajectory.State goal = trajectory.sample(time.get()); // sample the trajectory at 3.4 seconds from the beginning
    ChassisSpeeds adjustedSpeeds = controller.calculate(odometry.getPoseMeters(), goal);
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(adjustedSpeeds);
    drivetrain.getSwerveModule(0).setMeterSpeed(moduleStates[0].speedMetersPerSecond);
    drivetrain.getSwerveModule(0).setTargetAngle(moduleStates[0].angle.getDegrees()+initPos[0]);
    drivetrain.getSwerveModule(1).setMeterSpeed(moduleStates[1].speedMetersPerSecond);
    drivetrain.getSwerveModule(1).setTargetAngle(moduleStates[1].angle.getDegrees()+initPos[1]);
    drivetrain.getSwerveModule(2).setMeterSpeed(moduleStates[2].speedMetersPerSecond);
    drivetrain.getSwerveModule(2).setTargetAngle(moduleStates[2].angle.getDegrees()+initPos[2]);
    drivetrain.getSwerveModule(3).setMeterSpeed(moduleStates[3].speedMetersPerSecond);
    drivetrain.getSwerveModule(3).setTargetAngle(moduleStates[3].angle.getDegrees()+initPos[3]);

    SmartDashboard.putNumber("Speed 0", 10*drivetrain.getSwerveModule(0).getDriveMotor().getSelectedSensorVelocity()*SPEEDCONSTANT);
    SmartDashboard.putNumber("Angle 0 Expected", moduleStates[0].angle.getDegrees()+initPos[0]);
    SmartDashboard.putNumber("Speed 1", moduleStates[1].speedMetersPerSecond);
    SmartDashboard.putNumber("Angle 1 Expected", moduleStates[1].angle.getDegrees()+initPos[1]);
    SmartDashboard.putNumber("Speed 2", moduleStates[2].speedMetersPerSecond);
    SmartDashboard.putNumber("Angle 2 Expected", moduleStates[2].angle.getDegrees()+initPos[2]);
    SmartDashboard.putNumber("Speed 3", moduleStates[3].speedMetersPerSecond);
    SmartDashboard.putNumber("Angle 3 Expected", moduleStates[3].angle.getDegrees()+initPos[3]);
    SmartDashboard.putNumber("Angle 0 Actual", drivetrain.getSwerveModule(0).getCurrentAngle());
    SmartDashboard.putNumber("Angle 1 Actual", drivetrain.getSwerveModule(1).getCurrentAngle());
    SmartDashboard.putNumber("Angle 2 Actual", drivetrain.getSwerveModule(2).getCurrentAngle());
    SmartDashboard.putNumber("Angle 3 Actual", drivetrain.getSwerveModule(3).getCurrentAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println(trajectory.getTotalTimeSeconds());
    drivetrain.swapPIDSlot(0);
    drivetrain.setFieldOriented(true);
    drivetrain.setIsAuto(false);
    time.reset();
    System.out.println("AutoEnded");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Pose2d currPos = odometry.getPoseMeters();
    Pose2d tarPos = trajectory.getStates().get(trajectory.getStates().size()-1).poseMeters;
    double posDif = currPos.getTranslation().getDistance(tarPos.getTranslation());
    double rotDif = Math.abs((currPos.getRotation().minus(tarPos.getRotation())).getDegrees());
    return posDif <= .5 && rotDif <= 10; // review -Riley "Change once we account for slip.""
  }
}
