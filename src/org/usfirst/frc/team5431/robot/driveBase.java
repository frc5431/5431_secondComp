package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * This class declares variables/functions related specifically to the DriveBase (and only the DriveBase).
 * @author Usaid Malik
 */
public class driveBase {
	public static int wheelDiameter = 10;									//In inches. Used to calculate distancePerPulse
	private static double distancePerPulse = wheelDiameter * Math.PI / 1440;//Calculates distancePerPulse in inches
	private static int samplesToAverage = 7; 								//How many pulses to do before averaging them (smoothes encoder count)
	private static double minEncRate = 0.0; 								//Minimum Encoder Rate before hardware thinks encoders are stopped
	enum brakeMode{															//What brake mode the motors should be in
		Brake, NoBrake;
	}
	CANTalon frontright, frontleft, rearright, rearleft;					//Declaration of CANTalons used in the drivebase
	Encoder rightBaseEncoder, leftBaseEncoder;								//Declaration of encoders used in the drivebase
	RobotDrive tankDriveBase;
	/**
	 * Constructor for driveBase. All CANTalons are set to coast. If you want
	 * to set the driveBase to brake, use driveBase(brakeMode).
	 */
	public driveBase(){
		this.frontright = new CANTalon(RobotMap.frontright);				//Instantiates CANTalons based on mapping in RobotMap
		this.frontleft = new CANTalon(RobotMap.frontleft);
		this.rearright = new CANTalon(RobotMap.rearright);
		this.rearleft = new CANTalon(RobotMap.rearleft);
		frontright.setInverted(false);										//Inverts(or doesn't) motors
		frontleft.setInverted(false);
		rearright.setInverted(false);
		rearleft.setInverted(false);
		frontright.enableBrakeMode(false);									//Default brake mode will be coast
		frontleft.enableBrakeMode(false);
		rearright.enableBrakeMode(false);
		rearleft.enableBrakeMode(false);
		tankDriveBase = new RobotDrive(frontleft, rearleft, frontright, rearright);//Initializes RobotDrive to use tankDrive()
		rightBaseEncoder = new Encoder(RobotMap.rightBaseEnc1, RobotMap.rightBaseEnc2, false, EncodingType.k4X);//Using 4X encoding for encoders
		leftBaseEncoder = new Encoder(RobotMap.leftBaseEnc1, RobotMap.leftBaseEnc2, false, EncodingType.k4X);
		rightBaseEncoder.setDistancePerPulse(distancePerPulse);				//Sets distance robot would travel every encoder pulse
		leftBaseEncoder.setDistancePerPulse(distancePerPulse);
		rightBaseEncoder.setSamplesToAverage(samplesToAverage);				//Averages encoder count rate every samplesToAverage pulses
		leftBaseEncoder.setSamplesToAverage(samplesToAverage);
		rightBaseEncoder.setReverseDirection(false);						//Reverses encoder direction based on position on robot
		leftBaseEncoder.setReverseDirection(false);
		rightBaseEncoder.setMinRate(minEncRate);							//Sets minimum rate for encoder before hardware thinks it is stopped
		leftBaseEncoder.setMinRate(minEncRate);
	}
	/**
	 * Constructor for the driveBase class. This specifies whether the
	 * CANTalons are/aren't in brake mode. 
	 * @param mode A brakeMode enum. Can be brakeMode.Brake or brakeMode.NoBrake
	 */
	public driveBase(brakeMode mode){										//Constructor used if brakeMode is specified (should be but probably won't be used){
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
		rightBaseEncoder = new Encoder(RobotMap.rightBaseEnc1, RobotMap.rightBaseEnc2, false, EncodingType.k4X);
		leftBaseEncoder = new Encoder(RobotMap.leftBaseEnc1, RobotMap.leftBaseEnc2, false, EncodingType.k4X);
		rightBaseEncoder.setDistancePerPulse(distancePerPulse);
		leftBaseEncoder.setDistancePerPulse(distancePerPulse);
		rightBaseEncoder.setSamplesToAverage(samplesToAverage);
		leftBaseEncoder.setSamplesToAverage(samplesToAverage);
		rightBaseEncoder.setReverseDirection(false);
		leftBaseEncoder.setReverseDirection(false);
		rightBaseEncoder.setMinRate(minEncRate);
		leftBaseEncoder.setMinRate(minEncRate);
	}
	/**
	 * Drive function to control motors. Control driveBase motors through
	 * this function.
	 * @param left Power to right motor. From -1 to 1.
	 * @param right Power to left motor. From -1 to 1.
	 */
	public void drive(double left, double right){
		tankDriveBase.tankDrive(left, right);
	}
	/**
	 * Gets distance traveled by driveBase encoders since the last encoder reset.
	 * @return double array where index 0 is left distance and 1 is right distance.
	 */
	public double[] getEncDistance(){
		double returnVals[] = {0};
		returnVals[0] = leftBaseEncoder.getDistance();
		returnVals[1] = rightBaseEncoder.getDistance();
		return returnVals;
	}
}