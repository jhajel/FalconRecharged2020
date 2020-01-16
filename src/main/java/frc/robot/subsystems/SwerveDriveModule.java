/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SwerveModuleCommand;

public class SwerveDriveModule extends SubsystemBase {
    /**
     * Creates a new SwerveDriveModule.
     */

    private static final long STALL_TIMEOUT = 2000;

    private long mStallTimeBegin = Long.MAX_VALUE;

    private double mLastError = 0, mLastTargetAngle = 0;

    private final int mModuleNumber;

    private final double mZeroOffset;
    private final TalonSRX mAngleMotor;
    public final TalonFX mDriveMotor;

    public SwerveDriveModule(int moduleNumber, TalonSRX angleMotor, TalonFX driveMotor, double zeroOffset) {
        mModuleNumber = moduleNumber;

        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        mDriveMotor.setSelectedSensorPosition(0);

        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        angleMotor.setSensorPhase(true);
        angleMotor.config_kP(0, 20.2, 0);
        angleMotor.config_kI(0, 0.001, 0);
        angleMotor.config_kD(0, 60, 0);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);
        angleMotor.configNeutralDeadband(0.07);
        // driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
        // 0, 0); ADD TO CANSPARKMAX LATER***

        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.config_kD(0, 0.02, 0); // 0.02
        driveMotor.config_kD(0, 0.000001, 0); // 0.000001
        driveMotor.config_kD(0, 0.0065, 0); //0.0065

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(30, 0);
        angleMotor.configPeakCurrentLimit(30, 0);
        angleMotor.configPeakCurrentDuration(100, 0);
        angleMotor.enableCurrentLimit(true);

        // driveMotor.setSmartCurrentLimit(15); No clue how to set Current Limit... 
        // driveMotor.setSecondaryCurrentLimit(15, 0); //configSupplyCurrentLimit (SupplyCurrentLimitConfiguration currLimitCfg, int timeoutMs)? 
        // driveMotor.enableCurrentLimit(true); try using pheonix tuner

        setDefaultCommand(new SwerveModuleCommand(this));

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // setDefaultCommand(new SwerveModuleCommand(this));
    }

    public TalonSRX getAngleMotor() {
        return mAngleMotor;
    }

    /**
     * Get the current angle of the swerve module
     *
     * @return An angle in the range [0, 360)
     */
    public double getCurrentAngle() {
        double angle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
        angle -= mZeroOffset;
        angle %= 360;
        if (angle < 0)
            angle += 360;

        return angle;
    }

    public TalonFX getDriveMotor() {
        return mDriveMotor;
    }

    public void robotDisabledInit() {
        mStallTimeBegin = Long.MAX_VALUE;
    }

    public void setTargetAngle(double targetAngle) {

        mLastTargetAngle = targetAngle;

        targetAngle %= 360;

        SmartDashboard.putNumber("Module Target Angle " + mModuleNumber, targetAngle % 360);

        targetAngle += mZeroOffset;

        double currentAngle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 1024.0);
        double currentAngleMod = currentAngle % 360;
        if (currentAngleMod < 0)
            currentAngleMod += 360;

        double delta = currentAngleMod - targetAngle;

        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }

        delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if (delta > 90)
                targetAngle += 180;
            else if (delta < -90)
                targetAngle -= 180;
            mDriveMotor.setInverted(false);
        } else {
            mDriveMotor.setInverted(true);
        }

        targetAngle += currentAngle - currentAngleMod;

        double currentError = mAngleMotor.getClosedLoopError(0);
        // if (Math.abs(currentError - mLastError) < 7.5 &&
        // Math.abs(currentAngle - targetAngle) > 5) {
        // if (mStallTimeBegin == Long.MAX_VALUE) {
        // mStallTimeBegin = System.currentTimeMillis();
        // }
        // if (System.currentTimeMillis() - mStallTimeBegin > STALL_TIMEOUT) {
        // angleMotorJam = true;
        // mAngleMotor.set(ControlMode.Disabled, 0);
        // mDriveMotor.set(ControlMode.Disabled, 0);
        // SmartDashboard.putBoolean("Motor Jammed" + moduleNumber, angleMotorJam);
        // return;
        // }
        // } else {
        // mStallTimeBegin = Long.MAX_VALUE;
        // }
        mLastError = currentError;
        targetAngle *= 1024.0 / 360.0;
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetDistance(double distance) { // inches NEED TO REIMPLEMENT
        // if(angleMotorJam) {
        // mDriveMotor.set(ControlMode.Disabled, 0);
        // return;
        // }
        // distance /= 2 * Math.PI * driveWheelRadius; // to wheel rotations
        // distance *= driveGearRatio; // to encoder rotations
        // distance *= 80; // to encoder ticks
        // distance = inchesToEncoderTicks(distance);
        // SmartDashboard.putNumber("Module Ticks " + moduleNumber, distance);
        //mPIDController.setReference(distance, ControlType.kPosition);

    }

    public void setTargetSpeed(double speed) {
        mDriveMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void resetEncoder() {
        mDriveMotor.setSelectedSensorPosition(0);
    }

    public double getTargetAngle() {
        return mLastTargetAngle;
    }

    public double encoderTicksToInches(double ticks) {
        return ticks / 35.6;
    }

    public int inchesToEncoderTicks(double inches) {
        return (int) Math.round(inches * 64);
    }

    public double getInches() {
        return encoderTicksToInches(mDriveMotor.getSelectedSensorPosition());
    }

    // public double getDriveDistance() {
    //     double ticks = mDriveEncoder.getPosition();
    //     return encoderTicksToInches(ticks);
    // }
    public void printTick() {
        SmartDashboard.putNumber("Ticks" + mModuleNumber, mDriveMotor.getSelectedSensorPosition() );
    }

}
