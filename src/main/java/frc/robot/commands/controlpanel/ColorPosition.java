/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

/**
 * Add your docs here.
 */
public class ColorPosition {
    private String color;
    private double position;
    public ColorPosition(String initColor, double initPosition) {
        color = initColor;
        position = initPosition;
    }

    public String toString() {
        return "Color :" + color + "  Position: " + position;
    }
}
