/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

import frc.robot.Constants;
import frc.robot.Robot;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SpinToMidConfidence extends CommandBase {
  /**
   * Creates a new SenseColorTest.
   */
  private String color;
  private String previousColor;
  private String expectedColor;
  private String currentColor;
  private String prevColor;
  private String[] expectedColorArray;
  private int arraySize;
  private int prevIndex;
  private Map<String, Integer> colorDictionary;
  private String startColor;
  private double segmentLength;
  private double targetPos;
  private String gameData;
  private boolean runningForward;

  public SpinToMidConfidence(String data) { //viv bday = 3/6
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getColorSensor());
    if (data == null) {
      gameData = "Unknown";
    } else if (data.length() == 0) {
      gameData = "Unknown";
    } else if (data.charAt(0) == 'G') {
      gameData = "Yellow";
    } else if (data.charAt(0) == 'B') {
      gameData = "Red";
    } else if (data.charAt(0) == 'Y') {
      gameData = "Green";
    } else if (data.charAt(0) == 'R') {
      gameData = "Blue";
    } else {
      gameData = "Unknown";
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // RobotContainer.getContainer().getColorPanelSpinner().resetEncoder();
    expectedColorArray = new String[] { "Yellow", "Red", "Green", "Blue" };
    arraySize = expectedColorArray.length;

    // Mapping colors to index numbers
    colorDictionary = new HashMap<String, Integer>();
    colorDictionary.put("Yellow", Integer.valueOf(0));
    colorDictionary.put("Red", Integer.valueOf(1));
    colorDictionary.put("Green", Integer.valueOf(2));
    colorDictionary.put("Blue", Integer.valueOf(3));

    color = gameData;
    startColor = color;
    currentColor = RobotContainer.getContainer().getColorSensor().getColor();

    if (Constants.forward) {
      prevIndex = (colorDictionary.get(startColor) - 1) >= 0 ? colorDictionary.get(startColor) - 1 : arraySize - 1;
      previousColor = expectedColorArray[prevIndex];
      expectedColor = expectedColorArray[(colorDictionary.get(startColor) + 1) % arraySize];
      prevColor = previousColor;
    } else {
      prevIndex = (colorDictionary.get(startColor) + 1) % arraySize;
      previousColor = expectedColorArray[prevIndex];
      expectedColor = expectedColorArray[(colorDictionary.get(startColor) - 1) >= 0
          ? colorDictionary.get(startColor) - 1
          : arraySize - 1];
      prevColor = previousColor;
    }

    SmartDashboard.putString("previousColor", previousColor);
    SmartDashboard.putString("expColor", expectedColor);
    runningForward = true;
    findMid();
  }

  public void updateColor() {
    prevColor = currentColor;
    // Color change between green --> red sees yellow
    if (gameData.equals("Red")) { //viv bday = 3/6  
      if(runningForward && RobotContainer.getContainer().getColorSensor().getColor().equals("Yellow"))
      {
        currentColor = "Green";
      }
      else if(!runningForward && RobotContainer.getContainer().getColorSensor().getColor().equals("Yellow"))
      {
        currentColor = "Red";
      }
      if (prevColor.equals("Red") && currentColor.equals("Green") && RobotContainer.getContainer().getColorSensor().getColor().equals("Yellow")) {
        currentColor = "Green";
      }
    }
    // Color change between yellow -> blue sees green
    if (prevColor.equals("Blue") && currentColor.equals("Yellow")
        && RobotContainer.getContainer().getColorSensor().getColor().equals("Green")) {
      currentColor = "Blue";
    } else {
      currentColor = RobotContainer.getContainer().getColorSensor().getColor();
    }
  }

  public void findMid() {
    SmartDashboard.putNumber("init pos", RobotContainer.getContainer().getColorPanelSpinner().getPosition());

    updateColor();
    SmartDashboard.putString("currentColor", currentColor);
    while (currentColor != expectedColor) {
      SmartDashboard.putString("currentColor", currentColor);
      RobotContainer.getContainer().getColorPanelSpinner().spin(.15);
      runningForward = true;
      updateColor();
      RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    }
    RobotContainer.getContainer().getColorPanelSpinner().spin(0);
    double forwardPos = RobotContainer.getContainer().getColorPanelSpinner().getPosition();
    RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    SmartDashboard.putNumber("forward pos", forwardPos);
    runningForward = false;
    while (currentColor != previousColor) {
      SmartDashboard.putString("currentColor", currentColor);
      RobotContainer.getContainer().getColorPanelSpinner().spin(-.15);
      updateColor();
      RobotContainer.getContainer().getColorPanelSpinner().spin(0);
    }
    runningForward = true;
    double finalPos = RobotContainer.getContainer().getColorPanelSpinner().getPosition();
    RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    segmentLength = Math.abs(forwardPos - finalPos);
    SmartDashboard.putNumber("backward pos", finalPos);

    double midPos = segmentLength / 2;
    targetPos = finalPos + midPos;

    SmartDashboard.putNumber("Target pos", targetPos);
    RobotContainer.getContainer().getColorPanelSpinner().setPosition(targetPos);
    RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    SmartDashboard.putString("currentColor", currentColor);
    SmartDashboard.putNumber("Segment length", segmentLength);
    SmartDashboard.putString("prevColor", prevColor);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(RobotContainer.getContainer().getColorPanelSpinner().getPosition() - targetPos) < 0.01;
  }

  @Override
  public void end(final boolean interrupted) {
    RobotContainer.getContainer().getColorPanelSpinner().spin(0);
  }
}
