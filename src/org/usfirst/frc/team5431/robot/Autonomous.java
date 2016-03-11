package org.usfirst.frc.team5431.robot;
import org.usfirst.frc.team5431.robot.driveBase;
/**
 * Class for Autonomous commands. Uses switch-cases in order to acheive multi-threading without
 * creating multiple threads. Also much faster compared to multi-threading and takes less space.
 * 
 * @author Usaid Malik
 */
public class Autonomous {
	
	private int driveForwardState = 0;
	private double driveForwardDistance = 0;
	private int autoAIMState = 0;
	private double[] encodersDistance = {0};
	private double[] encodersRPM = {0};
	
	/**
	 * Initializes the Autonomous class.
	 */
	public Autonomous(){
		driveForwardDistance = driveBase.wheelDiameter * Math.PI * 10;//10 is distance in inches - must change
	}
	/**
	 * Updates the state of various autonomous functions. This must be called in <b>autonomousPeriodic()</b>.
	 * <ul>Currently updates</ul>:
	 * <li>driveForward()</li>
	 * <li>autoAim()</li>
	 */
	public void updateStates(){
		driveForwardState = driveForward(driveForwardState);
		autoAIMState = autoAim(autoAIMState);
	}
	
	/**
	 * Function that uses switch-case autonomous to
	 * allow the robot to drive forward at full speed
	 * until the driveForwardDistance specified in Autonomous.java.
	 * <b>Should only be called within updateStates()</b> Won't work otherwise.
	 * @param state
	 * @return state
	 */
	public int driveForward(int state){
		switch(state){
			default:
				break;
			case 0:
				break;
			case 1:
				Robot.drivebase.drive(1.0, 1.0);
				state = 2;
				break;
			case 2:
				encodersDistance = Robot.drivebase.getEncDistance();
				if(encodersDistance[0] > driveForwardDistance || encodersDistance[1] > driveForwardDistance){
					Robot.drivebase.drive(0.0, 0.0);
				}
				break;
			case 3:
				state = 0;
				break;
		}
		return state;
	}
	/**
	 * Function that uses switch-case autonomous to
	 * allow the robot to constantly look for a target to do auto-aim then fires..
	 * <b>Should only be called within updateStates().</b> Won't work otherwise.
	 * @param state
	 * @return state
	 */
	public int autoAim(int state){
		switch(state){
			default:
				break;
			case 0:
				break;
			case 1:
				//David's autoAim checking happens here
				break;
			case 2:
				//If David's autoAim code says to shoot
				Robot.flywheels.setFlywheelSpeed(1);
				state = 3;
				break;
			case 3:
				encodersRPM = Robot.flywheels.getRPM();
				/*
				if(encodersRPM[0] > minRPM && encoderRPM[1] > minRPM){
					state = 4;
				}
				*/
				break;
			case 4:
				
			}
		return state;
	}
}
