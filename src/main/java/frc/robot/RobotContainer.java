/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.sql.Driver;
import java.util.ArrayList;

import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoPath1;
import frc.robot.commands.climber.*;
import frc.robot.commands.controlpanel.*;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.limelight.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.swervedrive.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Color.ColorPanelSpinner;
import frc.robot.subsystems.Color.ColorSensor;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drive.HolonomicDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterMotor;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;
//import sun.java2d.cmm.ColorTransform;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Command m_autoCommand = null;
  private XboxController mXboxController;
  private XboxController mXboxController2;
  private static RobotContainer theContainer;
  private SwerveDriveSubsystem swerveDriveSubsystem;
  private ColorPanelSpinner colorPanelSpinner;
  private ColorSensor colorSensor;
  private Limelight limelight;
  private Conveyor conveyor;
  private Intake intake;
  private ShooterMotor shooterMotor;
  private Compressor compressor;
  private Climber climber;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    theContainer = this;
    // Configure the button bindings
    swerveDriveSubsystem = new SwerveDriveSubsystem();
    swerveDriveSubsystem.zeroGyro();
    colorPanelSpinner = new ColorPanelSpinner();
    colorSensor = new ColorSensor();

    mXboxController = new XboxController(0);
    mXboxController2 = new XboxController(1);
    limelight = new Limelight();
    conveyor = new Conveyor();
    intake = new Intake();
    shooterMotor = new ShooterMotor();
    compressor = new Compressor();
    climber = new Climber();

    shooterMotor.setDefaultCommand(new SpinShooterMotor());
    configureButtonBindings();
  }

  public ShooterMotor getShooterMotor() {
    return shooterMotor;
  }

  public ColorPanelSpinner getColorPanelSpinner() {
    return colorPanelSpinner;
  }

  public ColorSensor getColorSensor() {
    return colorSensor;
  }

  public SwerveDriveSubsystem getHolonomicDrivetrain() {
    return swerveDriveSubsystem;
  }

  public XboxController getDriveController() {
    return mXboxController;
  }

  public XboxController getClimbController() {
    return mXboxController2;
  }

  public static RobotContainer getContainer() {
    return theContainer;

  }

  public Limelight getLimelight() {
    return limelight;
  }

  public Conveyor getConveyor() {
    return conveyor;
  }

  public Intake getIntake() {
    return intake;
  }

  public Compressor getCompressor() {
    return compressor;
  }

  public Climber getClimber() {
    return climber;
  }


  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton buttonA = new JoystickButton(mXboxController, XboxController.Button.kA.value);
    JoystickButton buttonX = new JoystickButton(mXboxController, XboxController.Button.kX.value);
    JoystickButton buttonB = new JoystickButton(mXboxController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(mXboxController, XboxController.Button.kY.value);
    JoystickButton buttonY_2 = new JoystickButton(mXboxController2, XboxController.Button.kY.value);
    JoystickButton buttonX_2 = new JoystickButton(mXboxController2, XboxController.Button.kX.value);
    JoystickButton buttonB_2 = new JoystickButton(mXboxController2, XboxController.Button.kB.value);
    JoystickButton buttonA_2 = new JoystickButton(mXboxController2,XboxController.Button.kA.value);
    // buttonX.whenHeld(new IntakeSpeed(.5));
    buttonA.whenHeld(new IntakeSpeed(-1));
    //buttonB.whenPressed(new ToggleIntake());
    buttonY.whenPressed(new ZeroNavX());
    // buttonY.whileHeld(new IntakeSpeed(.5));
    //buttonY.whenPressed(new SwitchPipeline());
    //buttonX.whileHeld(new ConveyorSpeed(-1));
    // buttonA.whenPressed(new DriveForward(.2));
    //buttonY.whenPressed(new SwitchPipeline());
    //buttonX.whenPressed(new SwitchLimelightMode());
    buttonX.whenPressed(new Autonomous(createAutonomousPath2()));
    buttonB.whenPressed(new AutoPath1());

    // buttonY_2.whenPressed(new ToggleClimberGearLock(climber));
     buttonB_2.whenPressed(new SemiAutoClimb());
    // // buttonB_2.whenPressed(new MoveClimberArm(7, getClimber().getUpperArm()));
    // buttonX_2.whenPressed(new SemiAutoPullUp());
    // buttonX_2.whenPressed(new MoveClimberArm(-7, getClimber().getLowerArm()));

    // buttonB.whenPressed(new DriveForwardDistance(3, .3));

    // buttonX.whenPressed(new WheelsDriveForwardTest(-100, 0).withTimeout(5));

    // buttonY.whenPressed(new ZeroNavX());
    // buttonX.whileHeld(new ConveyorSpeed(1));
    // buttonY.whileHeld(new ConveyorSpeed(-1));
    // buttonA.whenPressed(new MoveConveyorDistance(-5));
    // buttonB.whenPressed(new ShooterSwitchArmMode());

    buttonX_2.whenPressed(new SpinToPosition());
    buttonY_2.whenPressed(new SpinToMidColor(DriverStation.getInstance().getGameSpecificMessage()));
    buttonA_2.whenPressed(new ToggleSpinner(colorPanelSpinner));

    // buttonX.whenPressed(new SwitchLimelightMode(limelight));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;

  }

  public Trajectory createAutonomousPath() //Test Path
  {
    Trajectory trajectory;
    TrajectoryConfig config = new TrajectoryConfig(2, 1);
    config.setStartVelocity(0);
    config.setEndVelocity(0);
    config.setReversed(false);
    config.addConstraint(new CentripetalAccelerationConstraint(0.5));
    ArrayList<Translation2d> listOfPoints = new ArrayList<Translation2d>();
    //listOfPoints.add(new Translation2d(1, 1));
    trajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(Math.toRadians(0))),
      listOfPoints, 
      new Pose2d(-1, -1, new Rotation2d(Math.toRadians(180))), config);
    return trajectory;
  }

  public Trajectory createAutonomousPath1() // Init Line (Start on Left) to Port Test
  {
    Trajectory trajectory;
    TrajectoryConfig config = new TrajectoryConfig(2, 1.0);
    config.setStartVelocity(0);
    config.setEndVelocity(0);
    config.setReversed(false);
    config.addConstraint(new CentripetalAccelerationConstraint(0.5));
    ArrayList<Translation2d> listOfPoints = new ArrayList<Translation2d>();
    listOfPoints.add(new Translation2d(1.0, 2.4384));
    trajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)),
      listOfPoints, 
      new Pose2d(3.048, 2.4384, new Rotation2d(Math.toRadians(0))), config);
    return trajectory;
  }

  public Trajectory createAutonomousPath2() //Test 2 Electric Bugaloo
  {
    Trajectory trajectory;
    TrajectoryConfig config = new TrajectoryConfig(2, 1.0);
    config.setStartVelocity(0);
    config.setEndVelocity(0);
    config.setReversed(false);
    ArrayList<Translation2d> listOfPoints = new ArrayList<Translation2d>();

    trajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)),
      listOfPoints, 
      new Pose2d(1, 0, new Rotation2d(Math.toRadians(0))), config);
    return trajectory;
  }
  
}