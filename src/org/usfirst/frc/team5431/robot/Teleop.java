package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for Teleop commands.
 * 
 * @author Usaid Malik
 *
 */
public class Teleop {
	private static int prevFlywheel = 0;
	private static int prevIntakeIn = 0;
	private static int prevIntakeOut = 0;
	private static int prevIntakeShoot = 0;
	public static int currentShootState = 0;
	private static int currentAutoAimState = 0;
	private static int currentClimbState = 0;
	private static int currentShootManualState = 0;
	private static final double[] offState = {0.0, 0.0};
	private static boolean ballIn = false;
	private static int flywheelspeed=0;
	private static boolean manualEnable = false;
	
	/**
	 * Update function for the Teleop() class. Should be called in teleopPeriodic().
	 * @param input OI object that should also be updated in teleopPeriodic().
	 */
	public void Update(OI input){
		//
		//SmarterDashboard.putNumber("OVERDRIVE", ((-input.joystickYVal/2.0)*0.25)+0.875); //Even out values to become 0.875
		SmarterDashboard.putNumber("MANUALDRIVE", ((-input.joystickYVal/2.0)*0.5)+0.75);
		
		Robot.drivebase.drive(input.xboxLeftJoystickVal, input.xboxRightJoystickVal);
		/*
		if(input.xboxLeftJoystickVal < -0.2 || input.xboxRightJoystickVal < -0.2 
				|| input.xboxLeftJoystickVal > 0.2 || input.xboxRightJoystickVal > 0.2) currentAutoAimState = 0;
		
		*/
		if((input.joystickButton2 ? 1:0) > prevFlywheel){
			//SmartDashboard.putNumber("Flywheel speed", Robot.flywheels.getFlywheelSpeed());
			if(flywheelspeed>0) {
				flywheelspeed=0;
				manualEnable = false;
			} else {
				double getOver = ((-input.joystickYVal/2.0)*0.5)+0.75;//SmarterDashboard.getNumber("MANUALDRIVE", 0);
				flywheelspeed=(int)getOver;
				manualEnable = true;
			}
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
		
		final boolean intakeon = (input.xbox.getRawAxis(3)) > 0.3; // if passed 1/3 move on
		final boolean intakereverse = (input.xbox.getRawAxis(2)) > 0.3;
		
		if((intakereverse ? 1:0) > prevIntakeOut && currentShootState == 0 && currentAutoAimState == 0){
			if(Robot.flywheels.getIntakeSpeed() < 0.0)
				Robot.flywheels.setIntakeSpeed(0);
			else
				Robot.flywheels.setIntakeSpeed(-1.0);
			//Intake in
		}
		prevIntakeOut = (intakereverse ? 1:0);
		ballIn = !(Robot.flywheels.intakeLimit.get());
		if(Robot.flywheels.getIntakeSpeed() > 0.0){
			SmartDashboard.putNumber("Bug", -1.54);
			if(ballIn && currentShootState == 0){
				SmartDashboard.putNumber("Bug", 1.345);
				Robot.flywheels.setIntakeSpeed(0);
			} else{
				SmartDashboard.putNumber("Bug", 5.431);
			}
		}
		SmarterDashboard.putBoolean("boulder", ballIn);
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
		if((intakeon ? 1:0) > prevIntakeIn  && currentShootState == 0 && currentAutoAimState == 0){
			SmartDashboard.putNumber("Bug", 3.14);
			SmartDashboard.putNumber("Intake speed", Robot.flywheels.getIntakeSpeed());
			if(Robot.flywheels.getIntakeSpeed() != 0.0)
				Robot.flywheels.setIntakeSpeed(0);
			else
				Robot.flywheels.setIntakeSpeed(1.0);
			//Intake out
		}
		prevIntakeIn = (intakeon ? 1:0);
		
		//if(input.xboxYVal) Robot.flywheels.climb(); //Climb the tower
		if(input.joystickTriggerVal && currentAutoAimState == 0) currentShootState = 1;//&& currentAutoAimState == 0) 
		if(input.joystickButton4 && currentShootState == 0) currentAutoAimState = 1;
		if(input.joystickButton5){
			currentAutoAimState = -1;
			currentShootState = -1;
			currentShootManualState = -1;
			currentClimbState=-1;
			manualEnable = false;
			double off[] = {0, 0};
			Robot.flywheels.setFlywheelSpeed(off);
		}
		
		double getOver = ((-input.joystickYVal/2.0)*0.5)+0.75;
		//double getOver = SmarterDashboard.getNumber("MANUAL-SPEED", 0.0);
		SmarterDashboard.putNumber("POWER", getOver);
		double[] curRPM = Robot.flywheels.getRPM();
		SwitchCase.cameraVision.getRPMS(curRPM, getOver);
		
		if(manualEnable) {
			int woawvers[] = {(int)(getOver*4500.0), (int)(getOver*4500.0)};
			Robot.flywheels.setPIDSpeed(woawvers);
		}
		//SmarterDashboard.putNumber("PRESSURE", R
		/*
		if(input.joystickButton6 && currentAutoAimState == 0 && currentShootState == 0 && currentShootManualState == 0 && currentClimbState < 8){
			currentClimbState = 1;
		}
		else if(input.joystickButton7 && currentAutoAimState == 0 && currentShootState == 0 && currentShootManualState == 0 && currentClimbState < 8){
			currentClimbState = 8;
		}
		*/
		//if(input.joystickButton6){
		if(input.xboxBRight){
			ClimbChop.choppers.set(DoubleSolenoid.Value.kReverse);
			SmarterDashboard.putBoolean("CHOPPERS",false);
		}
		//else if(input.joystickButton7){
		else if(input.xboxBLeft){
					SmarterDashboard.putBoolean("CHOPPERS",true);
			ClimbChop.choppers.set(DoubleSolenoid.Value.kForward);
		}
		SmartDashboard.putBoolean("intakeon", intakeon);
		SmartDashboard.putBoolean("intakeReverse", intakereverse);
		SmartDashboard.putNumber("currentShootState", currentShootState);
		SmartDashboard.putNumber("currentAutoAimState", currentAutoAimState);
		//SmartDashboard.putNumber("currentClimbState", currentClimbState);
		
		currentAutoAimState = SwitchCase.autoAim(currentAutoAimState);
		currentShootState = SwitchCase.shoot(currentShootState, (input.joystickPotentiometerVal + 1.0)/2.0);
		//currentShootState = SwitchCase.shoot(currentShootState, 0.0);
		//currentShootManualState = SwitchCase.shootManual(currentShootManualState);
		//currentClimbState = SwitchCase.climb(currentClimbState);
		
		if(input.joystickButton3 || input.joystickButton8) {// && !Robot.flywheels.intakeLimit.get()){
			Robot.flywheels.setIntakeSpeed(1);
		}
		
	}
}
