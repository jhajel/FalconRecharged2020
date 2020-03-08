/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.conveyor.ConveyorSpeed;
import frc.robot.commands.conveyor.ToggleIgnore;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.intake.ToggleIntake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoShoot extends SequentialCommandGroup {
  /**
   * Creates a new AutoShoot.
   */
  public AutoShoot() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super( new ToggleIgnore(true), 
    new ConveyorSpeed(.5).withTimeout(.5), 
    new SetShooterSpeed().withTimeout(.2) , 
    new ParallelCommandGroup(new ConveyorSpeed(-0.8), new SetShooterSpeed()).withTimeout(3));
  }
}
