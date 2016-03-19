package org.usfirst.frc.team5431.robot;
import org.usfirst.frc.team5431.robot.Robot.AutoTask;

import edu.wpi.first.wpilibj.Timer;

/**
 * Class for Autonomous commands. Uses switch-cases in order to acheive multi-threading without
 * creating multiple threads. Also much faster compared to multi-threading and takes less space.
 * 
 * @author Usaid Malik
 */
public class Autonomous {
	
	private int driveForwardState = 0;
	private int touchForwardState = 0;
	private int moatForwardState = 0;
	private int autoAIMState = 0;
	private long crossMoatTimer = 0;
	private double[] 
			driveDistance = { 0, 0 };
	
	private final double[]
			speedToOuterWork = { 0.5, 0.5 },
			speedToCross = { 0.6, 0.6 },
			speedToCrossMoat = { 1, 1 };
	
	private static final double 
				distanceToOuterWork = 48,
				distanceToCrossWork = 270,
				curveAmount = 0.09;
	
	
	/**
	 * Initializes the Autonomous class.
	 */
	public Autonomous(){
		
	}
	
	private void curveFix(double speeds[]) {
		final double toDrive[] = { 0, 0 };
		
		/*if(driveDistance[0] < driveDistance[1]) {
			toDrive[0] = speeds[0] + curveAmount;
			toDrive[1] = speeds[1] - curveAmount;
		} else if(driveDistance[0] > driveDistance[1]) {
			toDrive[0] = speeds[0] - curveAmount;
			toDrive[1] = speeds[1] + curveAmount;
		} else {*/
			toDrive[0] = speeds[0];
			toDrive[1] = speeds[1];
		//}
		Robot.drivebase.drive(-toDrive[0], -toDrive[1]);
	}
	
	
	private void touchForward() {
		
		if((driveDistance[0] < distanceToOuterWork || driveDistance[1] < distanceToOuterWork) && driveForwardState == 0) {
			curveFix(speedToOuterWork);
		} else {
			Robot.drivebase.resetDrive();
			driveForwardState = 1;
		}
		
		//touchForwardState = SwitchCase.driveForward(touchForwardState, 72);
	}
	
	private void crossForward(){
		if((driveDistance[0] < distanceToCrossWork || driveDistance[1] < distanceToCrossWork) && driveForwardState == 0) {
			curveFix(speedToCross);
		} else {
			Robot.drivebase.resetDrive();
			driveForwardState = 1;
		}
	}
	
	private int moatForward(int state){
		switch(state){
		case 0:
			break;
		default:
			break;
		case 1:
			curveFix(speedToCrossMoat);
			state = 2;
			break;
		case 2:
			if(!(driveDistance[0] < distanceToCrossWork || driveDistance[1] < distanceToCrossWork) && driveForwardState == 0){
				Robot.drivebase.resetDrive();
				driveForwardState = 1;
				state = 3;
			}
			break;
		case 3:
			crossMoatTimer = System.currentTimeMillis() + 3000;
			state = 4;
			break;
		case 4:
			if(System.currentTimeMillis() >= crossMoatTimer){
				Robot.drivebase.drive(0, 0);
				state = 5;
			}
			break;
		case 5:
			state = 0;
			break;
		}
		/*
		if((driveDistance[0] < distanceToCrossWork || driveDistance[1] < distanceToCrossWork) && driveForwardState == 0) {
			curveFix(speedToCrossMoat);
		} else {
			Robot.drivebase.resetDrive();
			driveForwardState = 1;
		}
		*/
		return state;
	}
	
	private void encoderUpdate() {
		driveDistance = Robot.drivebase.getEncDistance();
	}
	
	/**
	 * Updates the state of various autonomous functions. This must be called in <b>autonomousPeriodic()</b>.
	 * <ul>Currently updates</ul>:
	 * <li>driveForward()</li>
	 * <li>autoAim()</li>
	 */
	public void updateStates(AutoTask currentAuto){
		
		encoderUpdate();
		
    	switch(currentAuto) {
    	case TouchOuterWork:
    		touchForward();
    		break;
    	case CrossOuter:
    		crossForward();
    		break;
    	case Moat:
    		//moatForward();
    		moatForwardState = 1;
    		break;
    	case AutoShoot:
        //Put custom auto code here   +201
            break;
    	case StandStill:
    	default:
    		Timer.delay(0.1);
            break;
    	}
		touchForwardState = SwitchCase.driveForward(touchForwardState, 72);
		moatForwardState = moatForward(moatForwardState);
		//driveForwardState = SwitchCase.driveForward(driveForwardState);
		//autoAIMState = SwitchCase.autoAim(autoAIMState);
	}
	
}
