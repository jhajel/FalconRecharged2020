/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//Spins the wheel that spins the color panel
package frc.robot.commands.controlpanel;

import frc.robot.RobotContainer;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Map;
import java.util.HashMap;

public class SpinToColor extends CommandBase {
    
    private String startColor;
    private int colorCount;
    private String previousColor;
    private String targetColor;
    private String currentColor;
    private String[] targetColorArray;
    private int arraySize;
    private int prevIndex;
    private Map<String,Integer> colorDictionary;
    private String gameData;
    private String color;

    private Map<String, String> impossible;
    
    public SpinToColor() {
        addRequirements(RobotContainer.getContainer().getColorSensor());   
        addRequirements(RobotContainer.getContainer().getColorPanelSpinner());
        //gameData = data;
        impossible = new HashMap<String, String>();
        impossible.put("Yellow", "Green");
        impossible.put("Green", "Yellow");
        impossible.put("Blue", "Red");
        impossible.put("Red", "Blue");
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        color = RobotContainer.getContainer().getColorSensor().getColor();
        gameData =  DriverStation.getInstance().getGameSpecificMessage();
        targetColorArray = new String[]{"Yellow", "Red", "Green", "Blue"};
        arraySize = targetColorArray.length;
     
        //Mapping colors to index numbers
        colorDictionary = new HashMap<String, Integer>();
        colorDictionary.put("Yellow", Integer.valueOf(0));
        colorDictionary.put("Red", Integer.valueOf(1));
        colorDictionary.put("Green", Integer.valueOf(2));
        colorDictionary.put("Blue", Integer.valueOf(3));



        startColor = color;
        currentColor = color;

        if(Constants.forward) {
            prevIndex = (colorDictionary.get(startColor) - 1) >= 0 ? colorDictionary.get(startColor) - 1 : arraySize-1;
        }
        else {
            prevIndex = (colorDictionary.get(startColor) + 1) % arraySize;
        }
        previousColor = targetColorArray[prevIndex]; 

        if(gameData.length()>0){//sets target color based on game data(stage 3 control panel color)

            if(gameData.toUpperCase().charAt(0) == 'G'){
                targetColor = "Yellow";
            }
            else if(gameData.toUpperCase().charAt(0) == 'B'){
                targetColor = "Red";
            }
            else if(gameData.toUpperCase().charAt(0) == 'Y'){
                targetColor = "Green";
            }
            else if(gameData.toUpperCase().charAt(0) == 'R'){
                targetColor = "Blue";
            }
            else{
                targetColor = "Unknown";
            }
        }   
        
  
        
    }

    //If we see an impossible color, we don't change currentColor
    public void updateColor() {  
    
        String wrongColor = impossible.get(currentColor);

        String detected = RobotContainer.getContainer().getColorSensor().getColor();
        if (!detected.equals(wrongColor)) {
        currentColor = detected;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
  
        RobotContainer.getContainer().getColorPanelSpinner().spin(0.2); //change the speed
        //currentColor = ((RobotContainer.getContainer().getColorSensor().getColor().equals("Green") && previousColor.equals("Blue")) ? "Blue" : RobotContainer.getContainer().getColorSensor().getColor());
        updateColor();

        SmartDashboard.putString("currentColor", currentColor);
        SmartDashboard.putString("previousColor", previousColor);
        SmartDashboard.putNumber("colorCount", colorCount);
        SmartDashboard.putString("targetColor", targetColor);


        previousColor = currentColor;
    
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return currentColor.equals(targetColor);

        
    }

    // Called once after isFinished returns true
    @Override
    public void end(final boolean interrupted) {

        RobotContainer.getContainer().getColorPanelSpinner().spin(0);
    }
}
