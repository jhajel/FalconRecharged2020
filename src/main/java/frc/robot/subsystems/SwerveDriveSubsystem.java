package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import frc.robot.commands.HolonomicDriveCommand;

public class SwerveDriveSubsystem extends HolonomicDrivetrain {
	private static final double WHEELBASE = 21.5;
	private static final double TRACKWIDTH = 23;
	private static final double RATIO = Math.sqrt(Math.pow(WHEELBASE, 2) + Math.pow(TRACKWIDTH, 2));
	public SwerveDriveModule m0 = new SwerveDriveModule(0, new TalonSRX(12), new CANSparkMax(4, MotorType.kBrushless), 395); //real:390 practice: 212
	public SwerveDriveModule m1 = new SwerveDriveModule(1, new TalonSRX(13), new CANSparkMax(5, MotorType.kBrushless), 296); //real:293 practice: 59
	public SwerveDriveModule m2 = new SwerveDriveModule(2, new TalonSRX(10), new CANSparkMax(3, MotorType.kBrushless), 303); //real:298 practice: 56
	public SwerveDriveModule m3 = new SwerveDriveModule(3, new TalonSRX(11), new CANSparkMax(2, MotorType.kBrushless), 355); //real: 355 practice: 190

	/*
	public SwerveDriveModule m0 = new SwerveDriveModule(0, new TalonSRX(12), new CANSparkMax(4, MotorType.kBrushless), 370); //real:390 practice: 212
	public SwerveDriveModule m1 = new SwerveDriveModule(1, new TalonSRX(13), new CANSparkMax(5, MotorType.kBrushless), 293); //real:293 practice: 59
	public SwerveDriveModule m2 = new SwerveDriveModule(2, new TalonSRX(10), new CANSparkMax(3, MotorType.kBrushless), 248); //real:298 practice: 56
	public SwerveDriveModule m3 = new SwerveDriveModule(3, new TalonSRX(11), new CANSparkMax(2, MotorType.kBrushless), 355); //real: 355 practice: 190
	*/

	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
	private SwerveDriveModule[] mSwerveModules = new SwerveDriveModule[] {                            
		//new SwerveDriveModule(0, new TalonSRX(12), new CANSparkMax(4, MotorType.kBrushless), 390), //real:390 practice: 212
		//new SwerveDriveModule(1, new TalonSRX(13), new CANSparkMax(5, MotorType.kBrushless), 293), //real:293 practice: 59
		//new SwerveDriveModule(2, new TalonSRX(10), new CANSparkMax(3, MotorType.kBrushless), 298), //real:298 practice: 56
		//new SwerveDriveModule(3, new TalonSRX(11), new CANSparkMax(2, MotorType.kBrushless), 355) //real: 355 practice: 190
		m0,m1,m2,m3
	};

	public AHRS mNavX = new AHRS(SPI.Port.kMXP, (byte) 200);

	public SwerveDriveSubsystem() { // add PID controll stuff for Drive Motors
		zeroGyro(); 

		mSwerveModules[0].getDriveMotor().setInverted(false); //real: false
		mSwerveModules[1].getDriveMotor().setInverted(true); //real: true
		mSwerveModules[2].getDriveMotor().setInverted(false); //real: false
		mSwerveModules[3].getDriveMotor().setInverted(false); //real: false

		mSwerveModules[0].getAngleMotor().setInverted(true); //real: true
		mSwerveModules[2].getAngleMotor().setInverted(true); //real: true
		mSwerveModules[1].getAngleMotor().setInverted(true); //real: true
		mSwerveModules[3].getAngleMotor().setInverted(true); //real: true

		this.resetAllEncoders();

		//mSwerveModules[0].resetEncoder();
		for(int i = 0; i < 4; i++) {
			mSwerveModules[i].getDriveMotor().setIdleMode(IdleMode.kBrake);
		}

		setDefaultCommand(new HolonomicDriveCommand(this));
	}

	public AHRS getNavX() {
		return mNavX;
	}

	public double getGyroAngle() {
		return (mNavX.getAngle() - getAdjustmentAngle());
	}

	public double getGyroRate() {
		return mNavX.getRate();
	}

	public double getRawGyroAngle() {
		return mNavX.getAngle();
	}

	public double getYaw()
	{
		return mNavX.getAngle();
	}

	public SwerveDriveModule getSwerveModule(int i) {
		return mSwerveModules[i];
	}

	@Override
	public void holonomicDrive(double forward, double strafe, double rotation) {
		forward *= getSpeedMultiplier();
		strafe *= getSpeedMultiplier();
		if (isFieldOriented()) {
			
			double angleRad = Math.toRadians(getGyroAngle());
			double temp = forward * Math.cos(angleRad) +
					strafe * Math.sin(angleRad);
			strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
			forward = temp;
		}
		
		double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
		double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
		double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
		double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

		double[] angles = new double[]{
				Math.atan2(b, c) * 180 / Math.PI,
				Math.atan2(b, d) * 180 / Math.PI,
				Math.atan2(a, d) * 180 / Math.PI,
				Math.atan2(a, c) * 180 / Math.PI
		};

		double[] speeds = new double[]{
				Math.sqrt(b * b + c * c),
				Math.sqrt(b * b + d * d),
				Math.sqrt(a * a + d * d),
				Math.sqrt(a * a + c * c)
		};

		double max = speeds[0];

		for (double speed : speeds) { 
			if (speed > max) {
				max = speed;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (Math.abs(forward) > 0.05 ||
			    Math.abs(strafe) > 0.05 ||
			    Math.abs(rotation) > 0.05) {
				mSwerveModules[i].setTargetAngle(angles[i] + 180);
			} else {
				mSwerveModules[i].setTargetAngle(mSwerveModules[i].getTargetAngle());
			}
			mSwerveModules[i].setTargetSpeed(speeds[i]);
		}
	}

	@Override
	public void stopDriveMotors() {
		for (SwerveDriveModule module : mSwerveModules) {
			module.setTargetSpeed(0);
		}
	}

	public void resetAllEncoders() {
		for(int i = 0; i < 4; i++) {
			mSwerveModules[i].resetEncoder();
		}
	}

	public void driveForwardDistance(double targetPos, double angle){ // inches
		double angleError = ((angle - mNavX.getYaw()) / 180)*10;

		angleError = Math.min(angleError, 1);
		angleError = Math.max(angleError, -1);
		targetPos = (targetPos * 8.5714)/(4*Math.PI); //inches to ticks
		for (int i = 0; i < 4; i++) {
				mSwerveModules[i].setTargetAngle(angle); //mSwerveModules[i].getTargetAngle());
				mSwerveModules[i].setTargetDistance(targetPos+mSwerveModules[i].getDriveMotor().getEncoder().getPosition());
			}
			
	} // 2/12/19 3:37 PM i want boba and a burrito so bad right now !!!!!!!!!

	public void driveSidewaysDistance(double targetPos, double angle, double speed) {
		double angleError = ((angle - mNavX.getYaw()) / 180)*10;
		angleError = Math.min(angleError, 1);
		angleError = Math.max(angleError, -1);
		//holonomicDrive(0, speed, angleError);
	}

	public double calculateErrPos(double d1) {
		return d1 - mSwerveModules[0].getDriveDistance();
	}

}

