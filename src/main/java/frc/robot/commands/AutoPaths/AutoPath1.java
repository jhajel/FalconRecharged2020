/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.conveyor.ConveyorSpeed;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.swervedrive.AutoRotate;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.utility.TrajectoryMaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoPath1 extends SequentialCommandGroup {
  /**
   * Creates a new AutoPath1.
   */
  public AutoPath1() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    // super( new Autonomous(RobotContainer.getContainer().createAutonomousPath().getTrajectory(), RobotContainer.getContainer().createAutonomousPath().getAngle()),
    //        new Autonomous(RobotContainer.getContainer().createAutonomousPath1().getTrajectory(), RobotContainer.getContainer().createAutonomousPath1().getAngle()),
    //        new Autonomous(RobotContainer.getContainer().createAutonomousPath2().getTrajectory(), RobotContainer.getContainer().createAutonomousPath2().getAngle())); 
    super();
  }
  
}
