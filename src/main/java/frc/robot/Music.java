/**
 * Phoenix Software License Agreement
 *
 * Copyright (C) Cross The Road Electronics.  All rights
 * reserved.
 * 
 * Cross The Road Electronics (CTRE) licenses to you the right to 
 * use, publish, and distribute copies of CRF (Cross The Road) firmware files (*.crf) and 
 * Phoenix Software API Libraries ONLY when in use with CTR Electronics hardware products.
 * 
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS" WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT
 * LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO EVENT SHALL
 * CROSS THE ROAD ELECTRONICS BE LIABLE FOR ANY INCIDENTAL, SPECIAL, 
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF
 * PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR SERVICES, ANY CLAIMS
 * BY THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE
 * THEREOF), ANY CLAIMS FOR INDEMNITY OR CONTRIBUTION, OR OTHER
 * SIMILAR COSTS, WHETHER ASSERTED ON THE BASIS OF CONTRACT, TORT
 * (INCLUDING NEGLIGENCE), BREACH OF WARRANTY, OR OTHERWISE
 */

/**
 * Description:
 * Example plays music sourced from a MIDI file through several Talon FXs.
 * Use Tuner to convert a MIDI file into a CHRP (chirp) file. 
 * These are then placed into the deploy folder of this project so that they are copied into the robot controller (on deploy).
 * 
 * Supported Version:
 * 	- Talon FX: 20.2.3.0 or newer
 *
 * Feature Video: https://youtu.be/MTGScSS_iaQ
 */
package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Top level Robot class 
 */
public class Music extends TimedRobot {

    /* The orchestra object that holds all the instruments */
    Orchestra _orchestra;

    /* Talon FXs to play music through.  
    More complex music MIDIs will contain several tracks, requiring multiple instruments.  */
    /**
     * Climber - upperArm
     * Climber - lowerArm
     * Conveyor - indexer
     * Shooter - motor1
     * Shooter - motor2
     * SwerveDriveSubsystem - getSwerveModule[i] - getDriveMotor[0,1,2,3]
     */
    // TalonFX [] _fxes = {RobotContainer.getContainer().getClimberT().getLowerArm(), 
    //                     RobotContainer.getContainer().getClimberT().getUpperArm(), 
    //                     RobotContainer.getContainer().getConveyorT().getIndexer(), 
    //                     RobotContainer.getContainer().getShooterMotor().getmotor1(),
    //                     RobotContainer.getContainer().getShooterMotor().getmotor2(),
    //                     RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(0).getDriveMotor(),
    //                     RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(1).getDriveMotor(),
    //                     RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(2).getDriveMotor(),
    //                     RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(3).getDriveMotor()

    // };

    TalonFX [] _fxes = { RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(0).getDriveMotor(),
        RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(1).getDriveMotor(),
        RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(2).getDriveMotor(),
        RobotContainer.getContainer().getHolonomicDrivetrain().getSwerveModule(3).getDriveMotor()

    }

    /* An array of songs that are available to be played, can you guess the song/artists? */
    String[] _songs = new String[] {
        "mulan.chrp",
        "TheBox.chrp",
        "thanks.chrp",
        "Pinwheel.chrp",
        "smileFlower.chrp",
        "finalcountdown.chrp"
      };

    /* track which song is selected for play */
    int _songSelection = 0;

    /* overlapped actions */
    int _timeToPlayLoops = 0;

    /* joystick vars */
    Joystick _joy;
    int _lastButton = 0;
    int _lastPOV = 0;

    XboxController musicController;

    //------------- joystick routines --------------- //
    /** @return 0 if no button pressed, index of button otherwise. */
    int getButton() {
        if(musicController.getBackButton())
            return 1;
        else if(musicController.getStartButton())
            return 2;
        return 0;
    }

    void LoadMusicSelection(int offset)
    {
        /* increment song selection */
        _songSelection += offset;
        /* wrap song index in case it exceeds boundary */
        if (_songSelection >= _songs.length) {
            _songSelection = 0;
        }
        if (_songSelection < 0) {
            _songSelection = _songs.length - 1;
        }
        /* load the chirp file */
        _orchestra.loadMusic(_songs[_songSelection]); 

        /* print to console */
        System.out.println("Song selected is: " + _songs[_songSelection] + ".  Press left/right on d-pad to change.");
        
        /* schedule a play request, after a delay.  
            This gives the Orchestra service time to parse chirp file.
            If play() is called immedietely after, you may get an invalid action error code. */
        _timeToPlayLoops = 10;
    }

    public Music() {
        /* A list of TalonFX's that are to be used as instruments */
        ArrayList<TalonFX> _instruments = new ArrayList<TalonFX>();
      
        /* Initialize the TalonFX's to be used */
        for (int i = 0; i < _fxes.length; ++i) {
            _instruments.add(_fxes[i]);
        }
        /* Create the orchestra with the TalonFX instruments */
        _orchestra = new Orchestra(_instruments);
        musicController = RobotContainer.getContainer().getOperatorController();
    }
    
    // @Override
    // public void teleopInit() {
        
    //     /* load whatever file is selected */
    //     LoadMusicSelection(0);
    // }


    public void songControls() {
        /* poll gamepad */
        int btn = getButton();
        int currentPOV = musicController.getPOV();

        /* if song selection changed, auto-play it */
        if (_timeToPlayLoops > 0) {
            --_timeToPlayLoops;
            if (_timeToPlayLoops == 0) {
                /* scheduled play request */
                System.out.println("Auto-playing song.");
                _orchestra.play();
            }
        }


        /* has a button been pressed? */
        if (_lastButton != btn) {
            _lastButton = btn;

            switch (btn) {
                case 1: /* toggle play and paused */
                    if (_orchestra.isPlaying()) {
                        _orchestra.pause();
                        System.out.println("Song paused");
                    }  else {
                        _orchestra.play();
                        System.out.println("Playing song...");
                    }
                    break;
                    
                case 2: /* toggle play and stop */
                    if (_orchestra.isPlaying()) {
                        _orchestra.stop();
                        System.out.println("Song stopped.");
                    }  else {
                        _orchestra.play();
                        System.out.println("Playing song...");
                    }
                    break;
            }
        }

        /* has POV/D-pad changed? */
        if (_lastPOV != currentPOV) {
            _lastPOV = currentPOV;

            switch (currentPOV) {
                case 90:
                    /* increment song selection */
                    LoadMusicSelection(+1);
                    break;
                case 270:
                    /* decrement song selection */
                    LoadMusicSelection(-1);
                    break;
            }
        }
    }
}