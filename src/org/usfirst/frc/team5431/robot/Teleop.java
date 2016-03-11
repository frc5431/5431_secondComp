package org.usfirst.frc.team5431.robot;
/**
 * Class for Teleop commands.
 * 
 * @author Usaid Malik
 *
 */
public class Teleop {
	
	private static double constFlyWheelSpeed = .8;
	private int prevFlywheel = 0;
	private int prevIntakeIn = 0;
	private int prevIntakeOut = 0;
	
	/**
	 * Teleop constructor for the Teleop class. Other than being a constructor, 
	 * this does nothing special.
	 */
	public Teleop(){
		//Doop-de do. A lot of stuff to do here, no?
	}
	
	/**
	 * Update function for the Teleop() class. Should be called in teleopPeriodic().
	 * @param input OI object that should also be updated in teleopPeriodic().
	 */
	public void Update(OI input){
		Robot.drivebase.drive(input.xboxLeftJoystickVal, input.xboxRightJoystickVal);
		if((input.xboxAVal ? 1:0) > prevFlywheel){
			if(Robot.flywheels.getFlywheelSpeed() > 0)
				Robot.flywheels.setFlywheelSpeed(0.0);
			else
				Robot.flywheels.setFlywheelSpeed(constFlyWheelSpeed);				
		}
		if((input.xboxBVal ? 1:0) > prevIntakeOut){
			if(Robot.flywheels.getIntakeSpeed() > 0)
				Robot.flywheels.setFlywheelSpeed(0);
			else
				Robot.flywheels.setFlywheelSpeed(-1.0);
			//Intake in
		}
		if((input.xboxXVal ? 1:0) > prevIntakeIn){
			if(Robot.flywheels.getIntakeSpeed() > 0)
				Robot.flywheels.setFlywheelSpeed(0);
			else
				Robot.flywheels.setFlywheelSpeed(1.0);
			//Intake out
		}
		if(input.xboxYVal)
			Robot.flywheels.climb();
		prevFlywheel = (input.xboxAVal ? 1:0);
		prevIntakeIn = (input.xboxBVal ? 1:0);
		prevIntakeOut = (input.xboxXVal ? 1:0);
	}
}
