/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.ColorPanelSubsystem;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.HolonomicDrivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Command m_autoCommand = null;
  private XboxController mXboxController;
  private static RobotContainer theContainer;
  private SwerveDriveSubsystem swerveDriveSubsystem;
  private ColorPanelSubsystem colorPanelSubsystem;
  private Limelight limelight; 
  private Conveyor conveyor;
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    theContainer = this;
    // Configure the button bindings
    swerveDriveSubsystem = new SwerveDriveSubsystem();
    swerveDriveSubsystem.zeroGyro();
    colorPanelSubsystem = new ColorPanelSubsystem();
    mXboxController = new XboxController(0);
    limelight = new Limelight();
    conveyor = new Conveyor();
    configureButtonBindings();
  }

  public ColorPanelSubsystem getColorPanelSubsystem()
  {
    return colorPanelSubsystem;
  }

  public SwerveDriveSubsystem getHolonomicDrivetrain()
  {
    return swerveDriveSubsystem;
  }

  public XboxController getDriveController() {
    return mXboxController;
  }

  
  public static RobotContainer getContainer(){
    return theContainer;

  }

  public Limelight getLimelight(){
    return limelight;
  }

  public Conveyor getConveyor(){
    return conveyor;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton buttonA = new JoystickButton(mXboxController, XboxController.Button.kA.value);
    JoystickButton buttonX = new JoystickButton(mXboxController, XboxController.Button.kX.value);
    JoystickButton buttonB = new JoystickButton(mXboxController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(mXboxController, XboxController.Button.kY.value);
    //buttonA.whenPressed(new DriveForward(.2));
     
    //buttonB.whenPressed(new DriveForwardDistance(3, .3));
    
    //  buttonX.whenPressed(new WheelsDriveForwardTest(-100, 0).withTimeout(5));
     
    //buttonY.whenPressed(new ZeroNavX());
    buttonX.whileHeld(new ConveyorSpeed(1));
    buttonY.whileHeld(new ConveyorSpeed(-1));
    buttonA.whenPressed(new MoveConveyorDistance(-5));

    
    //buttonX.whenPressed(new SwitchLimelightMode(limelight));
    //buttonX.whenPressed(new PrintSensor());
    


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
}
