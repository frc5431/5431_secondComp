package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Class to control the flywheels of the robot.
 * @author Usaid Malik
 */
public class Flywheels {
	CANTalon rightFW, leftFW;
	Encoder rightRpmEnc, leftRpmEnc;
	public Flywheels(){
		rightFW = new CANTalon(RobotMap.rightFlyWheel);
		leftFW = new CANTalon(RobotMap.leftFlyWheel);
		rightFW.reverseOutput(false);
		leftFW.reverseOutput(false);
		rightRpmEnc = new Encoder(RobotMap.rightFWEnc1, RobotMap.rightFWEnc2, false, EncodingType.k1X);
		leftRpmEnc = new Encoder(RobotMap.leftFWEnc1, RobotMap.leftFWEnc2, false, EncodingType.k1X);
		rightRpmEnc.setReverseDirection(false);
		leftRpmEnc.setReverseDirection(false);
		rightRpmEnc.setSamplesToAverage(120);
		leftRpmEnc.setSamplesToAverage(120);
		rightRpmEnc.reset();
		leftRpmEnc.reset();
	}
	
	public double[] getRPM(){
		double returnVals[] = {0};
		returnVals[0] = (60/(360 * rightRpmEnc.getRate()));
		returnVals[1] = (60/(360 * leftRpmEnc.getRate()));
		return returnVals;
	}
	
	public void setFlywheelSpeed(double speed){
		rightFW.set(speed);
		leftFW.set(speed);
	}
}
