package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwitchCase {
	private static double[] encodersDistance = {0, 0};
	//private double[] encodersRPM = {0};
	private static double wheelCircum = driveBase.wheelDiameter * Math.PI;//10 is distance in inches - must change;
	private static double driveForwardDistance = 0;
	static Vision cameraVision = new Vision();
	private static boolean pass = false;
	public final static int abortAutoAim = -42;		//Get the joke, anyone?
	private static int shootStates = 0;
	private static long autoAimTimer = 0;
	private static long autoAimIntakeTimer = 0;
	private static long autoAimManualTimer = 0;
	private static long extendClimberTimer = 0;
	private static long raiseClimberTimer = 0;
	//private static int autoAimRemoteState = 0;	//Used for the shoot() function within autoAim()
	private static double[] off = {0, 0};
	private static boolean inAuto = false;
	public static double moveAmount = 0.47;
	public static int checkAmount = 3;
	private static int timesCount = 0;
	public static boolean shotTheBall = false;
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
	public static int driveForward(int state, int distance){
		switch(state){
			default:
				break;
			case 0:
				break;
			case 1:
				driveForwardDistance = wheelCircum * distance;
				Robot.drivebase.drive(.75, .75);
				state = 2;
				break;
			case 2:
				encodersDistance = Robot.drivebase.getEncDistance();
				if(encodersDistance[0] > driveForwardDistance || encodersDistance[1] > driveForwardDistance){
					Robot.drivebase.drive(0.0, 0.0);
					state = 3;
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
	public static int autoAim(int state){
		SmarterDashboard.putBoolean("AUTO", state>0);
		
		switch(state){
			default:
				break;
			case 0:
				cameraVision.Update();
				inAuto = false;
				timesCount = 0;
				break;
			case 1:
				SmartDashboard.putNumber("STATE STATE STATE", state);
				//If David's autoAim code says to shoot
				cameraVision.Update();
				
				if(Vision.manVals[1] == 0) {
					pass = true;
				} 
				else if(Vision.manVals[1] == 1){
					Robot.drivebase.drive(0.46, 0.46);
					state = 1;
					pass = false;
				}
				else if(Vision.manVals[1] == 2){
					Robot.drivebase.drive(-0.46, -0.46);
					state = 1;
					pass = false;
				} else if(Vision.manVals[0] == 5) {// || Vision.manVals[1] == 5){
					SmarterDashboard.putString("ERROR", "It's too close and too far");
					Robot.drivebase.drive(0, 0);
					state = abortAutoAim;
					
				}
				
				//You get it now, right?
				if(pass) {
					Robot.drivebase.enableBrakeMode();
					if(Vision.manVals[0] == 0) {
						if(timesCount > checkAmount) {
							state = 4;//Change when you want f/backward		
						} else {
							state = 1;
							timesCount += 1;
							Timer.delay(0.1);
						}
					}
					else if(Vision.manVals[0] == 1){
						Robot.drivebase.drive(-moveAmount,  moveAmount);
						state = 1;
					}
					else if(Vision.manVals[0] == 2){
						Robot.drivebase.drive(moveAmount,  -moveAmount);
						state = 1;
					}
					else if(Vision.manVals[0] == 5) {// || Vision.manVals[1] == 5){
						SmarterDashboard.putString("ERROR", "It's too close and too far");
						Robot.drivebase.drive(0, 0);
						state = abortAutoAim;
						
					}
					else {
						state = abortAutoAim; //manVals[0] should only be 0-2. Nothing else. Somethign is wrong.
					}
				}
				break;
			/*
			case 3:
				cameraVision.Update();
				SmartDashboard.putNumber("STATE STATE STATE", state);
				if(Vision.manVals[1] == 0){
					if(Vision.manVals[0] != 0)//Make sure turn left + right is alright
						state = 2;
					else{
						state = 4;
						//autoAimRemoteState = 1;
						SmartDashboard.putNumber("remoteBugIn", autoAimRemoteState);
					}
				}
				else if(Vision.manVals[1] == 1){
					Robot.drivebase.drive(-0.55, -0.55);
					state = 3;
				}
				else if(Vision.manVals[1] == 2){
					Robot.drivebase.drive(.55, .55);
					state = 3;
				}
				else
					state = abortAutoAim;
				break;*/
			case 4:
				Robot.drivebase.disableBrakeMode();
				SmartDashboard.putNumber("STATE STATE STATE", state);
				//SmartDashboard.putNumber("remoteBug", autoAimRemoteState);
				//autoAimRemoteState = 
				//if(autoAimRemoteState == 4)
					//state = 0;
				shootStates = 1;
				inAuto = true;
				state = 5;
				break;
			case 5:	
				state = 5;
				//shootStates = shoot(shootStates, 0.8);
				//if(shootStates == 0) {state = -1;}
				double speeds3[] = {1, 1}; 
				for(int a = 0; a < 1000; a++) {
					Robot.flywheels.setFlywheelSpeed(speeds3);
					Timer.delay(0.005);
					if(a > 600) {
						Robot.flywheels.setIntakeSpeed(1);
					}
				}
				shotTheBall = true;
				Robot.flywheels.setFlywheelSpeed(off);
				state = 1;
				break;
			case abortAutoAim:
				SmartDashboard.putString("Bug", "Failed to AutoAim");
				state = 1;
				break;
			case -1:
				Robot.drivebase.disableBrakeMode();
				SmartDashboard.putNumber("STATE STATE STATE", -1);
				Robot.flywheels.setFlywheelSpeed(off);
				Robot.flywheels.setIntakeSpeed(0);
				state = 0;
				break;
			}
		return state;
	}
	
	public static int shoot(int state, double shootSpeed){//shootSpeed is temp since there is no camera at the time of coding
		double toSetSpeed = shootSpeed+SmarterDashboard.getNumber("OVERDRIVE", 0.0);
		cameraVision.Update();
		SmartDashboard.putNumber("SysTime", System.currentTimeMillis());
		/*if(Vision.manVals[1] == 1) {
			SmarterDashboard.addDebugString("You're too close");
		} else if(Vision.manVals[1] == 2) {
			SmarterDashboard.addDebugString("You're too far");
		} else {
			SmarterDashboard.addDebugString("You're good");
		}*/
		switch(state){
		default:
				break;
			case 0:
				break;
			case 1:
				SmarterDashboard.putBoolean("AUTO", true);
				double[] speeds = {toSetSpeed, toSetSpeed};
				Robot.flywheels.setFlywheelSpeed(speeds);
				autoAimTimer = System.currentTimeMillis() + 2500;
	    		Robot.flywheels.leftFW.setVoltageRampRate(1);
	    		Robot.flywheels.rightFW.setVoltageRampRate(1);
				
				state = 2;
			case 2:
				SmartDashboard.putNumber("shootBug", System.currentTimeMillis());
				SmartDashboard.putNumber("shootBug2", autoAimTimer);
				
				double[] curRPM = Robot.flywheels.getRPM();
				double[] speedsTwo = cameraVision.getRPMS(curRPM, toSetSpeed);
				SmartDashboard.putString("LEFTRIGHT", String.valueOf(speedsTwo[2]) + ":" + String.valueOf(speedsTwo[3]));
				SmartDashboard.putString("NEEDEDLEFTRIGHT", String.valueOf(speedsTwo[4]) + ":" + String.valueOf(speedsTwo[5]));
				double leftPower = Robot.flywheels.getLeftPower(); //SmarterDashboard.getNumber("OVERDRIVE", 0.0);;
				double rightPower = Robot.flywheels.getRightPower(); //+ SmarterDashboard.getNumber("OVERDRIVE", 0.0);;
				double newPower[] = {leftPower +(speedsTwo[0] / 3.5), rightPower + (speedsTwo[1] /3.5)};
				Robot.flywheels.setFlywheelSpeed(newPower);
				if(cameraVision.withIn(speedsTwo[2], -400, 400) && cameraVision.withIn(speedsTwo[3], -400, 400)) {
					
					/*if(passedTimes < 4) {
						Timer.delay(0.1);
						passedTimes += 1;
					} else {*/
				//if(System.currentTimeMillis() >= autoAimTimer){]
						final double newSpeed[] = {newPower[0] - 0.1, // + 0.009 + (SmarterDashboard.getNumber("OVERDRIVE", 0.0) /10), 
								newPower[1] - 0.1};// + 0.009 + (SmarterDashboard.getNumber("OVERDRIVE", 0.0) /10)};
						Robot.flywheels.setFlywheelSpeed(newSpeed);
						//Timer.delay(0.2);
						//SmartDashboard.putNumber("autoAimIntakebug", System.currentTimeMillis());
						Robot.flywheels.setIntakeSpeed(1);
						if(inAuto) {
							for(int a = 0;a < 200; a++) {
								Robot.flywheels.setIntakeSpeed(1);
								Timer.delay(0.005);
							}
							shotTheBall = true;
							Robot.flywheels.setFlywheelSpeed(off);
						}
						autoAimIntakeTimer = System.currentTimeMillis() + 1750;
						SmartDashboard.putNumber("autoAimIntakeBug2", autoAimIntakeTimer);
						state = 3;
					//}
				}
				else
					state = 2;
				break;
			case 3:
				if(System.currentTimeMillis() >= autoAimIntakeTimer){
					SmarterDashboard.putBoolean("AUTO", false);
					Robot.flywheels.setIntakeSpeed(0);
					Robot.flywheels.setFlywheelSpeed(off);
		    		Robot.flywheels.leftFW.setVoltageRampRate(10);
		    		Robot.flywheels.rightFW.setVoltageRampRate(10);
					state = 4;
				}
				else
					state = 3;
				break;
			case 4:			//This is to allow remoteStates to know when program is done
				state = 0;
				break;
			case -1://You are aborting
				Robot.flywheels.setIntakeSpeed(0);
				Robot.flywheels.setFlywheelSpeed(off);
	    		Robot.flywheels.leftFW.setVoltageRampRate(10);
	    		Robot.flywheels.rightFW.setVoltageRampRate(10);
				state = 0;
		}
		return state;
	}
	
	public static int shootManual(int state){
		switch(state){
		default:
			break;
		case 0:
			break;
		case 1:
			autoAimManualTimer = System.currentTimeMillis() + 750;
			Robot.flywheels.setIntakeSpeed(1);
			state = 2;
			break;
		case 2:
			SmartDashboard.putNumber("autoAimManualTimer", autoAimManualTimer);
			if(autoAimManualTimer >= System.currentTimeMillis()){
				Robot.flywheels.setIntakeSpeed(0);
				state = 3;
			}
			break;
		case 3://In case you want to know that it is done
			state = 0;
			break;
		case -1:
			Robot.flywheels.setIntakeSpeed(0);
			Robot.flywheels.setFlywheelSpeed(off);
			state = 0;
			break;
		}
		return state;
	}
	
	public static int climb(int state){
		switch(state){
		default:
			break;
		case 0:
			break;
		case 1:
			Robot.pneumatics.extendClimber.set(DoubleSolenoid.Value.kForward);
			extendClimberTimer = System.currentTimeMillis() + 2000;
			state = 2;
			break;
		case 2:
			if(System.currentTimeMillis() >= extendClimberTimer){
				state = 3;
			}
			break;
		case 3:
			raiseClimberTimer = System.currentTimeMillis() + 2000;
			Robot.pneumatics.raiseClimber.set(DoubleSolenoid.Value.kForward);
			state = 4;
			break;
		case 4:
			if(System.currentTimeMillis() >= raiseClimberTimer){
				state = 5;
			}
			break;
		case 5:
			Robot.pneumatics.extendClimber.set(DoubleSolenoid.Value.kReverse);
			extendClimberTimer = System.currentTimeMillis() + 500;
			state = 6;
			break;
		case 6:
			if(System.currentTimeMillis() >= extendClimberTimer){
				state = 7;
			}
			break;
		case 7:	//Done, do winch now.
			state = 0;
			break;
		//For reverse-climbing. Make sure to update the delays for BOTH climbing/letting go
		case 8:
			Robot.pneumatics.extendClimber.set(DoubleSolenoid.Value.kForward);
			extendClimberTimer = System.currentTimeMillis() + 500;
			state = 9;
			break;
		case 9:
			if(System.currentTimeMillis() >= extendClimberTimer){
				state = 10;
			}
			break;
		case 10:
			Robot.pneumatics.raiseClimber.set(DoubleSolenoid.Value.kReverse);
			raiseClimberTimer = System.currentTimeMillis() + 2000;
			state = 11;
			break;
		case 11:
			if(System.currentTimeMillis() >= raiseClimberTimer){
				state = 12;
			}
			break;
		case 12:
			Robot.pneumatics.extendClimber.set(DoubleSolenoid.Value.kReverse);
			extendClimberTimer = System.currentTimeMillis() + 2000;
			state = 13;
			break;
		case 13:
			Robot.pneumatics.extendClimber.set(DoubleSolenoid.Value.kReverse);
			Robot.pneumatics.raiseClimber.set(DoubleSolenoid.Value.kReverse);
			state = 14;
			break;
		case 14:
			state = 0;
			break;
		case -1:
			state = 13;
			break;
		}
		return state;
	}
}
