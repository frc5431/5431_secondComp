package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private int currentShootState = 0;
	private int currentAutoAimState = 0;
	private boolean ballIn = false;
	private boolean overrideLimit = false;
	
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
		//
		Robot.table.putNumber("OVERDRIVE", ((((-input.joystick.getZ())/2.0))*0.25)+0.875);
		Robot.drivebase.drive(input.xboxLeftJoystickVal, input.xboxRightJoystickVal);
		if((input.joystickButton2 ? 1:0) > prevFlywheel){
			SmartDashboard.putNumber("Flywheel speed", Robot.flywheels.getFlywheelSpeed());
			if(Robot.flywheels.getFlywheelSpeed() > 0.0)
				Robot.flywheels.setFlywheelSpeed(0.0);
			else
				Robot.flywheels.setFlywheelSpeed(Robot.table.getNumber("OVERDRIVE", 0)+0.0);				
		}
		prevFlywheel = (input.joystickButton2 ? 1:0);
		/*
		if(input.xboxXVal){
			Robot.flywheels.setIntakeSpeed(-1.0);
		}
		/*
		else
			Robot.flywheels.setIntakeSpeed(0.0);
			*/
		
		final boolean intakeon = input.xbox.getRawAxis(3)>0.2;
		final boolean intakereverse = input.xbox.getRawAxis(2)>0.2;
		
		if((intakereverse ? 1:0) > prevIntakeOut){
			if(Robot.flywheels.getIntakeSpeed() < 0.0)
				Robot.flywheels.setIntakeSpeed(0);
			else
				Robot.flywheels.setIntakeSpeed(-1.0);
			//Intake in
		}
		prevIntakeOut = (intakereverse ? 1:0);
		if(Robot.flywheels.getIntakeSpeed() > 0.0){
			SmartDashboard.putNumber("Bug", -1.54);
			if(!Robot.flywheels.intakeLimit.get() && currentShootState == 0){
				SmartDashboard.putNumber("Bug", 1.345);
				Robot.flywheels.setIntakeSpeed(0);
				ballIn = true;
			}
			else{
				SmartDashboard.putNumber("Bug", 5.431);
				ballIn = false;
			}
		}
		Robot.table.putBoolean("boulder", ballIn);
		/*//Commented due to conflict between driver/shooter control over intake 
		if(ballIn){
			SmartDashboard.putNumber("Bug", -1);
			if(input.joystickTriggerVal)
				Robot.flywheels.setIntakeSpeed(1.0);
			else
				Robot.flywheels.setIntakeSpeed(0);
		}
		*/
		SmartDashboard.putNumber("Bug", 1.280000000006);
		if((intakeon ? 1:0) > prevIntakeIn){
			/*
			if(Robot.flywheels.intakeLimit.get()){
				overrideLimit = true;
			}
			else
				overrideLimit = false;
				*/
			SmartDashboard.putNumber("Bug", 3.14);
			SmartDashboard.putNumber("Intake speed", Robot.flywheels.getIntakeSpeed());
			if(Robot.flywheels.getIntakeSpeed() != 0.0)
				Robot.flywheels.setIntakeSpeed(0);
			else
				Robot.flywheels.setIntakeSpeed(1.0);
			//Intake out
		}
		prevIntakeIn = (intakeon ? 1:0);
		if(input.xboxYVal)
			Robot.flywheels.climb();
		if(input.joystickTriggerVal && currentAutoAimState == 0){
			currentShootState = 1;
		}
		if(input.joystickButton4 && currentShootState == 0){
			currentAutoAimState = 1;
		}
		SmartDashboard.putBoolean("intakeon", intakeon);
		SmartDashboard.putBoolean("intakeReverse", intakereverse);
		SmartDashboard.putNumber("currentShootState", currentShootState);
		SmartDashboard.putNumber("currentAutoAimState", currentAutoAimState);
		currentAutoAimState = SwitchCase.autoAim(currentAutoAimState);
		//currentShootState = SwitchCase.shoot(currentShootState, (input.joystickPotentiometerVal + 1.0)/2.0);
		currentShootState = SwitchCase.shoot(currentShootState, 0.0);
	}
}
