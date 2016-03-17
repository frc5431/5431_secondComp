package org.usfirst.frc.team5431.robot;
/**
 * Class that stores the mapping/ports of motors and encoders.
 * @author Usaid Malik
 *
 */
public class RobotMap {
	public static final int 
		frontright = 4,		//Mapping for driveBase motors
		frontleft = 6,
		rearright = 3,
		rearleft = 7,
		
		
		rightFlyWheel = 2,	//Mapping for Flywheel motors
		leftFlyWheel = 8,
		intake = 5,
		winch = 9,
		firstSolenoid = 1,
		secondSolenoid = 2,
		
		rightBaseEnc1 = 0,	//Mapping for encoders' DIO ports
		rightBaseEnc2 = 1,
		leftBaseEnc1 = 2,
		leftBaseEnc2 = 3,
		rightFWEnc1 = 14,
		rightFWEnc2 = 15,
		leftFWEnc1 = 16,
		leftFWEnc2 = 17,
		intakeLim = 9;
}
