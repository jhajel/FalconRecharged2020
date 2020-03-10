/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.utility.TrajectoryMaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoLineToAutoScore extends SequentialCommandGroup {
  /**
   * Creates a new AutoDelayToScore.
   */
  public AutoLineToAutoScore() {
    TrajectoryMaker trajMaker = RobotContainer.getContainer().createToPortPath();
    addCommands(
      new Autonomous(trajMaker.getTrajectory(), trajMaker.getAngle()),
      new AutoShoot() // Assuming Auto Shoot Ends
    );
  }
}
