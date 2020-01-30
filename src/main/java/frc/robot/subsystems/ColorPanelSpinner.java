
package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ColorPanelSpinner extends SubsystemBase {
    private CANEncoder encoder;
    private CANSparkMax moto1;
    public static CANPIDController mPIDController;
    public static double mPIDControllerP = 0.15;
    public static double mPIDControllerI = 0.0000001;
    public static double mPIDControllerD = 0.01;

    public ColorPanelSpinner() {
        moto1 = new CANSparkMax(29, MotorType.kBrushless);
        moto1.setIdleMode(IdleMode.kBrake);
        encoder = moto1.getEncoder();
        mPIDController = moto1.getPIDController();
        // mPIDControllerP = 0.0;
        mPIDController.setP(mPIDControllerP); // 0.00001 working value. we keep it.
        mPIDController.setI(mPIDControllerI); // .0000001
        mPIDController.setD(mPIDControllerD); // 0.0065

    }

    public void setMotorSpeed (double speed){
        moto1.set(speed);
    }

    public void spin(double speed) {
        moto1.set(speed);
    }

    public int inchesToEncoderTicks(double inches) {
        return (int) Math.round(inches * 64);
    }

    public void setPosition(double pos) {
        // mPIDController.setP(mPIDControllerP); // 0.00001 working value. we keep it.
        // mPIDController.setI(mPIDControllerI); // .0000001
        // mPIDController.setD(mPIDControllerD); // 0.0065
        mPIDController.setReference(pos, ControlType.kPosition);
    }

    public void goToPosition(double degrees) // color panel circumference 113.0973 inches
    {
        /*
         * distance /= 2 * Math.PI * 2; // to wheel rotations distance *= 0.25; // to
         * encoder rotations distance *= 4; // to encoder ticks distance =
         * inchesToEncoderTicks(distance);
         */
        double ticks = degrees / 360.0;
        SmartDashboard.putNumber("Module Ticks ", ticks);
    }

    public CANPIDController getPIDController() {
        return mPIDController;
    }

    public double getPosition() {
        return encoder.getPosition();
    }

    public void printPosition() {
        SmartDashboard.putNumber("Spinner Pos", getPosition());
    }

}

// 2pi*18 = circumference of control panel
/// 12.57 = circumference of wheel
// 9 rotations = 1 control panel rotationss