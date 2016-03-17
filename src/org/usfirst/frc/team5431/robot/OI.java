package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class that takes input from joysticks/xbox controllers.
 * This class can be reused every year - nothing specific to 2016 to change.
 * @author Usaid Malik
 *
 */
public class OI {
	Joystick xbox;
	Joystick joystick;
	public double 
			xboxRightJoystickVal = 0,
			xboxLeftJoystickVal = 0,
			joystickPotentiometerVal = 0.0;
	
	public boolean
			//Xbox joystick
			xboxAVal = false,
			xboxBVal = false,
			xboxXVal = false,
			xboxYVal = false,
			//Gun joystick
			joystickTriggerVal = false,
			joystickButton2 = false,
			joystickButton3 = false,
			joystickButton4 = false,
			joystickButton5 = false;
	
	private static int 
			xboxRightJoy = 1,
			xboxLeftJoy = 5,
			xboxButtonA = 1,
			xboxButtonB = 2,
			xboxButtonX = 3,
			xboxButtonY = 4,
			//Gun joystick
			joystickPotentiometer = 2,
			joystickTrigger = 1,
			joystickButtonLabeled2 = 2,
			joystickButtonLabeled3 = 3,
			joystickButtonLabeled4 = 4,
			joystickButtonLabeled5 = 5;
	
	public OI(int xboxPort, int joystickPort){
		xbox = new Joystick(xboxPort);
		joystick = new Joystick(joystickPort);
	}
	
	public void updateVals(){
		//Xbox joystick
		xboxRightJoystickVal = xbox.getRawAxis(xboxRightJoy);
		xboxLeftJoystickVal = xbox.getRawAxis(xboxLeftJoy);
		joystickPotentiometerVal = joystick.getRawAxis(joystickPotentiometer);
		xboxAVal = xbox.getRawButton(xboxButtonA);
		xboxBVal = xbox.getRawButton(xboxButtonB);
		xboxXVal = xbox.getRawButton(xboxButtonX);
		xboxYVal = xbox.getRawButton(xboxButtonY);
		
		//Shooting joystick
		joystickTriggerVal = joystick.getRawButton(joystickTrigger);
		joystickButton2 = joystick.getRawButton(joystickButtonLabeled2);
		joystickButton3 = joystick.getRawButton(joystickButtonLabeled3);
		joystickButton4 = joystick.getRawButton(joystickButtonLabeled4);
		joystickButton5 = joystick.getRawButton(joystickButtonLabeled5);
		//Add joystick stuff if we ever need a joystick
	}
}
