package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class that takes input from joysticks/xbox controllers.
 * This class can be reused every year - nothing specific to 2016 to change.
 * @author Usaid Malik
 *
 */
public class OI {
	Joystick xbox, joystick;
	public double xboxRightJoystickVal = 0;
	public double xboxLeftJoystickVal = 0;
	public boolean xboxAVal = false;
	public boolean xboxBVal = false;
	public boolean xboxXVal = false;
	public boolean xboxYVal = false;
	
	private int xboxRightJoy = 1;
	private int xboxLeftJoy = 5;
	private int xboxButtonA = 1;
	private int xboxButtonB = 3;
	private int xboxButtonX = 2;
	private int xboxButtonY = 4;
	
	public OI(int xboxPort, int joystickPort){
		xbox = new Joystick(xboxPort);
		joystick = new Joystick(joystickPort);
	}
	
	public void updateVals(){
		xboxRightJoystickVal = xbox.getRawAxis(xboxRightJoy);
		xboxLeftJoystickVal = xbox.getRawAxis(xboxLeftJoy);
		xboxAVal = xbox.getRawButton(xboxButtonA);
		xboxBVal = xbox.getRawButton(xboxButtonB);
		xboxXVal = xbox.getRawButton(xboxButtonX);
		xboxYVal = xbox.getRawButton(xboxButtonY);
		
		//Add joystick stuff if we ever need a joystick
	}
}
