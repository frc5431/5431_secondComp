package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class to control the flywheels and intake of the robot. Also does climber.
 * @author Usaid Malik
 */
public class Shooter {
	Solenoid initialClimbSolenoid;
	Solenoid secondClimbSolenoid;
	CANTalon rightFW, leftFW, intakeMotor, winchMotor;
	Encoder rightRpmEnc, leftRpmEnc;
	
	/**
	 * Constructor for the Shooter() class. Assigns motors, encoders, and sets settings for them.
	 * Encoders are reset after all the motors/encoders are assigned.
	 * Do not reset encoders by calling this multiple times. If you do so, you are an idiot. Period.
	 */
	public Shooter(){
		initialClimbSolenoid = new Solenoid(RobotMap.firstSolenoid);
		secondClimbSolenoid = new Solenoid(RobotMap.secondSolenoid);
		initialClimbSolenoid.set(false);
		secondClimbSolenoid.set(false);
		rightFW = new CANTalon(RobotMap.rightFlyWheel);
		leftFW = new CANTalon(RobotMap.leftFlyWheel);
		intakeMotor = new CANTalon(RobotMap.intake);
		winchMotor = new CANTalon(RobotMap.winch);
		winchMotor.reverseOutput(false);
		intakeMotor.reverseOutput(false);
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
	/**
	 * Resets encoders on the shooter flywheels. Make sure to reset the encoders
	 * using this function. Note that there shouldn't be a need to reset the encoders since they
	 * measure RPM.
	 */
	public void resetEncoders(){
		leftRpmEnc.reset();
		rightRpmEnc.reset();
	}
	
	/**
	 * Gets the RPM of the flywheels.
	 * @return double array where index 0 is the RPM of the right, and 1 is the RPM of the left.
	 */
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
	
	public double getFlywheelSpeed(){
		return rightFW.get();
	}
	public void setIntakeSpeed(double speed){
		intakeMotor.set(speed);
	}
	public double getIntakeSpeed(){
		return intakeMotor.get();
	}
	
	public void climb(){
		initialClimbSolenoid.set(true);
		Timer.delay(3);
		secondClimbSolenoid.set(true);
		Timer.delay(3);
		secondClimbSolenoid.set(false);
		winchMotor.set(1.0);
		Timer.delay(5);
		winchMotor.set(0.0);
	}
}
                                                                                                             