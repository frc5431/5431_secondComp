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
	private int autoAIMState = 0;
	
	
	/**
	 * Initializes the Autonomous class.
	 */
	public Autonomous(){
		
	}
	/**
	 * Updates the state of various autonomous functions. This must be called in <b>autonomousPeriodic()</b>.
	 * <ul>Currently updates</ul>:
	 * <li>driveForward()</li>
	 * <li>autoAim()</li>
	 */
	public void updateStates(){
		driveForwardState = SwitchCase.driveForward(driveForwardState);
		autoAIMState = SwitchCase.autoAim(autoAIMState);
	}
	
}
