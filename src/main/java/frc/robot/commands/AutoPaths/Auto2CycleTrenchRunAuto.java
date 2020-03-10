/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.commands.swervedrive.AutoRotate;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.utility.TrajectoryMaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Auto2CycleTrenchRunAuto extends SequentialCommandGroup {
  /**
   * Creates a new Auto2CycleTrenchRunAuto.
   */
  public Auto2CycleTrenchRunAuto() {
    // Start at Initiation Line
    // This is all theoretical code with no actual field measurements. 
    // MAKE SURE TO MEASURE AND SWAP VALUES BEFORE TESTING - Kyle

    TrajectoryMaker traj = RobotContainer.getContainer().createToPortPath();
    TrajectoryMaker trajTrench = RobotContainer.getContainer().createTargetToFrontOfTrench();
    TrajectoryMaker trajTrenchForward = RobotContainer.getContainer().createTrenchForward();
    TrajectoryMaker trajTrenchTarget = RobotContainer.getContainer().createTrenchToTargetDiagonal();
    addCommands(
      new Autonomous(traj.getTrajectory(), traj.getAngle()),
      new AutoShoot(),
      new Autonomous(trajTrench.getTrajectory(), trajTrench.getAngle()),
      //need a method to rotate 180 here, either by using a Differential Drive trajectory or AutoRotate Method
      //new AutoRotate(180),
      new Autonomous(trajTrenchForward.getTrajectory(), trajTrenchForward.getAngle()).raceWith(new IntakeSpeed(-1)),
      //need to rotate 180 again
      //new AutoRotate(180),
      new Autonomous(trajTrenchTarget.getTrajectory(), trajTrenchTarget.getAngle()),
      new AutoShoot()
    );
  }
}
