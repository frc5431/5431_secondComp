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
	public double joystickPotentiometerVal = 0.0;
	public boolean xboxAVal = false;
	public boolean xboxBVal = false;
	public boolean xboxXVal = false;
	public boolean xboxYVal = false;
	public boolean joystickTriggerVal = false;
	public boolean joystickButton2 = false;
	public boolean joystickButton3 = false;
	
	private int xboxRightJoy = 1;
	private int xboxLeftJoy = 5;
	private int joystickPotentiometer = 2;
	private int xboxButtonA = 1;
	private int xboxButtonB = 2;
	private int xboxButtonX = 3;
	private int xboxButtonY = 4;
	private int joystickTrigger = 1;
	private int joystickButtonLabeled2 = 2;
	private int joystickButtonLabeled3 = 3;
	
	public OI(int xboxPort, int joystickPort){
		xbox = new Joystick(xboxPort);
		joystick = new Joystick(joystickPort);
	}
	
	public void updateVals(){
		xboxRightJoystickVal = xbox.getRawAxis(xboxRightJoy);
		xboxLeftJoystickVal = xbox.getRawAxis(xboxLeftJoy);
		joystickPotentiometerVal = joystick.getRawAxis(joystickPotentiometer);
		xboxAVal = xbox.getRawButton(xboxButtonA);
		xboxBVal = xbox.getRawButton(xboxButtonB);
		xboxXVal = xbox.getRawButton(xboxButtonX);
		xboxYVal = xbox.getRawButton(xboxButtonY);
		joystickTriggerVal = joystick.getRawButton(joystickTrigger);
		joystickButton2 = joystick.getRawButton(joystickButtonLabeled2);
		joystickButton3 = joystick.getRawButton(joystickButtonLabeled3);
		//Add joystick stuff if we ever need a joystick
	}
}
