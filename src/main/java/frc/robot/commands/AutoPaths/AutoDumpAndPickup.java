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
import frc.robot.commands.conveyor.SenseCell;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.utility.TrajectoryMaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoDumpAndPickup extends SequentialCommandGroup {
  /**
   * Creates a new AutoDumpAndPickup.
   */
  public AutoDumpAndPickup() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    TrajectoryMaker forwardPath = RobotContainer.getContainer().createForwardPath3();
    TrajectoryMaker forwardScorePath = RobotContainer.getContainer().createForwardPath2();
    TrajectoryMaker backPath = RobotContainer.getContainer().createBackwardPath();
    TrajectoryMaker forwardPath2 = RobotContainer.getContainer().createForwardPath4();
    addCommands(
      new Autonomous(forwardScorePath.getTrajectory(), forwardScorePath.getAngle()),
      new ConveyorSpeed(-1).withTimeout(1),
      new Autonomous(backPath.getTrajectory(), backPath.getAngle()),
      new Autonomous(forwardPath.getTrajectory(), 
        forwardPath.getAngle()).alongWith(new IntakeSpeed(-1), new SenseCell().andThen(new SenseCell(),new SenseCell())).withTimeout(3),
      new Autonomous(forwardPath2.getTrajectory(), forwardPath2.getAngle()),
      new ConveyorSpeed(-1).withTimeout(1)
      
      );
      
  }
}
