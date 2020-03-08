/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import java.sql.Driver;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoPaths.AutoRightToScore;
import frc.robot.commands.AutoPaths.AutoDiagonal;
import frc.robot.commands.AutoPaths.AutoDumpAndPickup;
import frc.robot.commands.AutoPaths.AutoPath1;
import frc.robot.commands.climber.*;
import frc.robot.commands.controlpanel.*;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.shooter.*;
import frc.robot.commands.swervedrive.*;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Color.ColorPanelSpinner;
import frc.robot.subsystems.Color.ColorSensor;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterMotor;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;
//import sun.java2d.cmm.ColorTransform;
import frc.robot.utility.TrajectoryMaker;

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
    JoystickButton buttonLB = new JoystickButton(mXboxController, XboxController.Button.kBumperLeft.value);
    JoystickButton buttonRB = new JoystickButton(mXboxController, XboxController.Button.kBumperRight.value);
    JoystickButton buttonBack = new JoystickButton(mXboxController, XboxController.Button.kBack.value);
    JoystickButton buttonStart = new JoystickButton(mXboxController, XboxController.Button.kStart.value);

    JoystickButton buttonY_2 = new JoystickButton(mXboxController2, XboxController.Button.kY.value);
    JoystickButton buttonX_2 = new JoystickButton(mXboxController2, XboxController.Button.kX.value);
    JoystickButton buttonB_2 = new JoystickButton(mXboxController2, XboxController.Button.kB.value);
    JoystickButton buttonA_2 = new JoystickButton(mXboxController2,XboxController.Button.kA.value);
    JoystickButton buttonLB_2 = new JoystickButton(mXboxController, XboxController.Button.kBumperLeft.value);
    JoystickButton buttonRB_2 = new JoystickButton(mXboxController2, XboxController.Button.kBumperRight.value);
    JoystickButton buttonBack_2 = new JoystickButton(mXboxController2, XboxController.Button.kBack.value);
    JoystickButton buttonStart_2 = new JoystickButton(mXboxController2, XboxController.Button.kStart.value);
    
    buttonA.whenPressed(new ToggleIntake());
    buttonB.whileHeld(new IntakeSpeed(-1));
    buttonX.whileHeld(new IntakeSpeed(.5));
    buttonY.whileHeld(new ConveyorSpeed(-.8));
    buttonLB.whileHeld(new ConveyorSpeed(.5));
    buttonRB.whileHeld(new SpinShooterMotor());
    buttonBack.whenPressed(new ZeroNavX());
    //buttonStart.whenPressed(new);
    

    //buttonA_2.whenPressed(new ToggleIgnore());
    buttonB_2.whenPressed(new SemiAutoClimb());
    buttonX_2.whenPressed(new SemiAutoPullUp());
    buttonY_2.whenPressed(new ToggleClimberGearLock(climber));
    buttonRB_2.whenPressed(new SpinRotations());
    buttonLB_2.whenPressed(new SpinToColor());
    buttonBack_2.whenPressed(new SpinToMidColor()); //may b
    // buttonStart_2.



    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new AutoPath1();

  }

  public TrajectoryMaker createSidePath() //Test Path
  {
    //return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2.77, 0.2, new Rotation2d(0)), true);
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0, -2, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createForwardPath() //Test Path
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-4.1, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createDiagonalPath() //Test Path
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(1, 1, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createForwardPath2() //Test Path
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-2.77, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createBackwardPath() //Test Path
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2.77, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createForwardPath3() //Test Path
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createForwardPath4() //Test Path
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-4.75, 0, new Rotation2d(0)), true);
  }


  public TrajectoryMaker createPortToFrontofTrench()
    {
      ArrayList<Translation2d> points = new ArrayList<Translation2d>();
      points.add(new Translation2d(-1.5, 2.3));
      points.add(new Translation2d(-3, 2.3));
      return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-5.3, 2.3, new Rotation2d(180)), points );
    }
  public TrajectoryMaker createMoveDownTrench()
  {
    return new TrajectoryMaker(new Pose2d(0,0,new Rotation2d(0)), new Pose2d(3, 0, new Rotation2d(0)), true);
  }

  public TrajectoryMaker createMoveToPort()
  {
    ArrayList<Translation2d> points = new ArrayList<Translation2d>();
      points.add(new Translation2d(1.524, 2.286));
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(3.048, 4.572, new Rotation2d(0)), points );
  }
  

  public TrajectoryMaker createAutonomousPath1() // Init Line (Start on Left) to Port Test
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
  }

  public TrajectoryMaker createAutonomousPath2() //Test 2 Electric Bugaloo
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0, -1, new Rotation2d(0)), true);
  }
  
}