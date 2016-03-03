package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This class declares variables/functions related specifically to the DriveBase (and only the DriveBase).
 * @author Usaid Malik
 */
public class driveBase {
	private static int wheelDiameter = 10;									//In inches. Used to calculate distancePerPulse
	private static double distancePerPulse = wheelDiameter * Math.PI / 1440;//Calculates distancePerPulse in inches
	private static int samplesToAverage = 7; 								//How many pulses to do before averaging them (smoothes encoder count)
	private static double minEncRate = 0.0; 								//Minimum Encoder Rate before hardware thinks encoders are stopped
	enum brakeMode{															//What brake mode the motors should be in
		Brake, NoBrake;
	}
	CANTalon frontright, frontleft, rearright, rearleft;					//Declaration of CANTalons used in the drivebase
	Encoder rightBaseEncoder, leftBaseEncoder;								//Declaration of encoders used in the drivebase
	
	public driveBase(brakeMode mode)										//Constructor used if brakeMode is specified (should be but probably won't be used)
	{
		this.frontright = new CANTalon(RobotMap.frontright);				//Instantiates CANTalons based on mapping in RobotMap
		this.frontleft = new CANTalon(RobotMap.frontleft);
		this.rearright = new CANTalon(RobotMap.rearright);
		this.rearleft = new CANTalon(RobotMap.rearleft);
		
		if(mode == brakeMode.Brake){										//If in BrakeMode, set CANTalons to brakeMode, otherwise, don't
			frontright.enableBrakeMode(true);
			frontleft.enableBrakeMode(true);
			rearright.enableBrakeMode(true);
			rearleft.enableBrakeMode(true);
		}
		else{
			frontright.enableBrakeMode(false);
			frontleft.enableBrakeMode(false);
			rearright.enableBrakeMode(false);
			rearleft.enableBrakeMode(false);
		}
		rightBaseEncoder = new Encoder(RobotMap.rightBaseEnc, EncodingType::k4X);
		leftBaseEncoder = new Encoder(RobotMap.leftBaseEnc, EncodingType::k4X);
		rightBaseEncoder.setDistancePerPulse(distancePerPulse);
		leftBaseEncoder.setDistancePerPulse(distancePerPulse);
		rightBaseEncoder.setSamplesToAverage(samplesToAverage);
		leftBaseEncoder.setSamplesToAverage(samplesToAverage);
		rightBaseEncoder.setReverseDirection(false);
		leftBaseEncoder.setReverseDirection(false);
		rightBaseEncoder.setMinRate(minEncRate);
		leftBaseEncoder.setMinRate(minEncRate);
	}
}