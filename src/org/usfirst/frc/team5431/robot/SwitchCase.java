package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwitchCase {
	//private static double[] encodersDistance = {0};
	//private double[] encodersRPM = {0};
	//private static double driveForwardDistance = driveForwardDistance = driveBase.wheelDiameter * Math.PI * 10;//10 is distance in inches - must change;
	private static Vision cameraVision = new Vision();
	
	public final static int abortAutoAim = -42;		//Get the joke, anyone?
	private static long autoAimTimer = 0;
	private static long autoAimIntakeTimer = 0;
	private static int autoAimRemoteState = 0;	//Used for the shoot() function within autoAim()
	
	public SwitchCase() {
		
	}
	
	/**
	 * Function that uses switch-case autonomous to
	 * allow the robot to drive forward at full speed
	 * until the driveForwardDistance specified in Autonomous.java.
	 * <b>Should only be called within updateStates()</b> Won't work otherwise.
	 * @param state
	 * @return state
	 */
	public static int driveForward(int state){
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
				//encodersDistance = Robot.drivebase.getEncDistance();
				/*if(encodersDistance[0] > driveForwardDistance || encodersDistance[1] > driveForwardDistance){
					Robot.drivebase.drive(0.0, 0.0);
				}*/
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
	public static int autoAim(int state){
		Robot.table.putBoolean("AUTO", state>0);
		switch(state){
			default:
				break;
			case 0:
				cameraVision.Update();
				break;
			case 1:
				//David's autoAim checking happens here
				cameraVision.Update();
				if(Vision.manVals[0] == 0 && Vision.manVals[1] == 0){
					autoAimRemoteState = 1;
					state = 4;
				}
				else
					state = 2;
				break;
			case 2:
				//If David's autoAim code says to shoot
				cameraVision.Update();
				//WE ARE IGNORING CASE 3 DUE TO BUGTESTING.
				//REPEAT: WE ARE IGNORING CASE 3 DUE TO BUGTESTING. FIX.
				//FIX.
				//FIX.
				//You get it now, right?
				if(Vision.manVals[0] == 0)
					state = 4;
				else if(Vision.manVals[0] == 1){
					Robot.drivebase.drive(-0.5,  0.5);
					state = 2;
				}
				else if(Vision.manVals[0] == 2){
					Robot.drivebase.drive(0.5,  -0.5);
					state = 2;
				}
				else if(Vision.manVals[0] == 666){
					SmartDashboard.putString("ERROR", "Vision failed");
					Robot.drivebase.drive(0, 0);
					return state; //Why go any further
				}
				else
					state = abortAutoAim; //manVals[0] should only be 0-2. Nothing else. Somethign is wrong.
				break;
			case 3:
				cameraVision.Update();
				if(Vision.manVals[1] == 0){
					if(Vision.manVals[0] != 0)//Make sure turn left + right is alright
						state = 2;
					else{
						state = 4;
						autoAimRemoteState = 1;
						SmartDashboard.putNumber("remoteBugIn", autoAimRemoteState);
					}
				}
				else if(Vision.manVals[1] == 1){
					Robot.drivebase.drive(-0.6, -0.6);
					state = 3;
				}
				else if(Vision.manVals[1] == 2){
					Robot.drivebase.drive(.6, .6);
					state = 3;
				}
				else
					state = abortAutoAim;
				break;
			case 4:
				SmartDashboard.putNumber("remoteBug", autoAimRemoteState);
				autoAimRemoteState = shoot(autoAimRemoteState, .8);//Remember to remove .8 with Vision.getSpeed() once camera is installed
				if(autoAimRemoteState == 4)
					state = 0;
			case abortAutoAim:
				SmartDashboard.putString("Bug", "Failed to AutoAim");
			}
		return state;
	}
	
	public static int shoot(int state, double shootSpeed){//shootSpeed is temp since there is no camera at the time of coding
		double toSetSpeed = shootSpeed+Robot.table.getNumber("OVERDRIVE", 0.0);
		double[] off = {0, 0};
		cameraVision.Update();
		
		switch(state){
		default:
				break;
			case 0:
				break;
			case 1:
				Robot.table.putBoolean("AUTO", true);
				double[] speeds = {toSetSpeed, toSetSpeed};
				Robot.flywheels.setFlywheelSpeed(speeds);
				autoAimTimer = System.currentTimeMillis() + 2500;
				state = 2;
			case 2:
				SmartDashboard.putNumber("shootBug", System.currentTimeMillis());
				SmartDashboard.putNumber("shootBug2", autoAimTimer);
				//if(System.currentTimeMillis() >= autoAimTimer){
				double[] curRPM = Robot.flywheels.getRPM();
				double[] speedsTwo = cameraVision.getRPMS(curRPM, toSetSpeed);
				if(cameraVision.withIn(speedsTwo[3], -0.08, 0.08) && cameraVision.withIn(speedsTwo[4], -0.08, 0.08)) {
					SmartDashboard.putNumber("autoAimIntakebug", System.currentTimeMillis());
					Robot.flywheels.setIntakeSpeed(1);
					autoAimIntakeTimer = System.currentTimeMillis() + 750;
					SmartDashboard.putNumber("autoAimIntakeBug2", autoAimIntakeTimer);
					state = 3;
				}
				else
					state = 2;
				break;
			case 3:
				if(System.currentTimeMillis() >= autoAimIntakeTimer){
					Robot.table.putBoolean("AUTO", false);
					Robot.flywheels.setIntakeSpeed(0);
					Robot.flywheels.setFlywheelSpeed(off);
					state = 4;
				}
				else
					state = 3;
				break;
			case 4:			//This is to allow remoteStates to know when program is done
				state = 0;
				break;
				
		}
		return state;
	}
}
