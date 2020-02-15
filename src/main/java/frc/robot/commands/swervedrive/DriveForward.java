/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;

import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveForward extends CommandBase {
    private static boolean isEnded;
    private Timer time = new Timer();
    private double driveTime = 1;

    public DriveForward() {
        this(1);
    }

    public DriveForward(double driveTimer) {
        addRequirements(RobotContainer.getContainer().getHolonomicDrivetrain());
        isEnded = false;
        this.driveTime = driveTimer;
    }

    public void driveForward() {
        RobotContainer.getContainer().getHolonomicDrivetrain().holonomicDrive(0.3, 0, 0);

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        time.reset();
        time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        driveForward();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (time.get() > driveTime);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        isEnded = false;
        RobotContainer.getContainer().getHolonomicDrivetrain().stopDriveMotors();
    }
}
