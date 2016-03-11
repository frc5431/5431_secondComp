package org.usfirst.frc.team5431.robot;
/**
 * Class that stores the mapping/ports of motors and encoders.
 * @author Usaid Malik
 *
 */
public class RobotMap {
	public static int frontright = 4;		//Mapping for driveBase motors
	public static int frontleft = 6;
	public static int rearright = 3;
	public static int rearleft = 7;
	
	public static int rightFlyWheel = 2;	//Mapping for Flywheel motors
	public static int leftFlyWheel = 8;
	public static int intake = 5;
	public static int winch = 9;
	public static int firstSolenoid = 1;
	public static int secondSolenoid = 2;
	
	
	public static int rightBaseEnc1 = 10;	//Mapping for encoders' DIO ports
	public static int rightBaseEnc2 = 11;
	public static int leftBaseEnc1 = 12;
	public static int leftBaseEnc2 = 13;
	public static int rightFWEnc1 = 14;
	public static int rightFWEnc2 = 15;
	public static int leftFWEnc1 = 16;
	public static int leftFWEnc2 = 17;
	
	public static int intakeLim = 0;
}
