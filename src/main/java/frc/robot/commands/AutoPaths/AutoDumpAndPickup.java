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
import frc.robot.commands.swervedrive.Autonomous;

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
    super(
      new Autonomous(RobotContainer.getContainer().createForwardPath2().getTrajectory(), RobotContainer.getContainer().createForwardPath2().getAngle()),
      new ConveyorSpeed(-1).withTimeout(2),
      new Autonomous(RobotContainer.getContainer().createBackwardPath().getTrajectory(), RobotContainer.getContainer().createBackwardPath().getAngle()),
      new Autonomous(RobotContainer.getContainer().createForwardPath3().getTrajectory(), RobotContainer.getContainer().createForwardPath3().getAngle()).raceWith(new IntakeSpeed(-1))

    );
  }
}
