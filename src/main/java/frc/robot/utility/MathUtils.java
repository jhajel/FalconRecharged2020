package frc.robot.utility;

public class MathUtils
{
    public static double deadband(double input, boolean isFieldOriented) {
        if (!isFieldOriented) {
            if(Math.abs(input) < 0.1) {
                return 0;
            }
            else {
                return input;
            }
        }
        if (Math.abs(input) < 0.25) 
            return 0;
        return input;
    }
}