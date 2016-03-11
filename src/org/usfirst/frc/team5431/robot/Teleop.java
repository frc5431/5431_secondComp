package org.usfirst.frc.team5431.robot;
/**
 * Class for Teleop commands.
 * 
 * @author Usaid Malik
 *
 */
public class Teleop {
	
	private static double constFlyWheelSpeed = .8;
	
	public Teleop(){
		//Doop-de do. A lot of stuff to do here, no?
	}
	
	public void Update(OI input){
		Robot.drivebase.drive(input.xboxLeftJoystickVal, input.xboxRightJoystickVal);
		if(input.xboxAVal){
			Robot.flywheels.setFlywheelSpeed(constFlyWheelSpeed);
		}
		if(input.xboxBVal){
			//Intake in
		}
		if(input.xboxXVal){
			//Intake out
		}
	}
}
