package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

/**
 * Class to control the flywheels and intake of the robot. Also does climber.
 * @author Usaid Malik
 */
public class Shooter {
	Solenoid initialClimbSolenoid;
	Solenoid secondClimbSolenoid;
	DigitalInput intakeLimit;
	CANTalon rightFW, leftFW, intakeMotor, winchMotor;
	
	/**
	 * Constructor for the Shooter() class. Assigns motors, encoders, and sets settings for them.
	 * Encoders are reset after all the motors/encoders are assigned.
	 * Do not reset encoders by calling this multiple times. If you do so, you are an idiot. Period.
	 */
	public Shooter(){
		intakeLimit = new DigitalInput(RobotMap.intakeLim);
		initialClimbSolenoid = new Solenoid(RobotMap.firstSolenoid);
		secondClimbSolenoid = new Solenoid(RobotMap.secondSolenoid);
		initialClimbSolenoid.set(false);
		secondClimbSolenoid.set(false);
		rightFW = new CANTalon(RobotMap.rightFlyWheel);
		leftFW = new CANTalon(RobotMap.leftFlyWheel);
		intakeMotor = new CANTalon(RobotMap.intake);
		winchMotor = new CANTalon(RobotMap.winch);
		winchMotor.setInverted(false);
		intakeMotor.setInverted(false);
		
		rightFW.setInverted(true);
		leftFW.setInverted(false);
		rightFW.setFeedbackDevice(FeedbackDevice.EncRising);
		leftFW.setFeedbackDevice(FeedbackDevice.EncRising);
		leftFW.configEncoderCodesPerRev(1024);
		rightFW.configEncoderCodesPerRev(1024);
	}
	
	/**
	 * Gets the RPM of the flywheels.
	 * @return double array where index 0 is the RPM of the right, and 1 is the RPM of the left.
	 */
	public double[] getRPM(){
		double returnVals[] = {0,0};
		//the 600 is multiplied for 2 reasons:
		//1 - getEncVelocity() returns it as RPS (rotations per second), so you multiply it by 60
		//2 - getEncVelocity() returns it as  1/10th as the actual RPS, so you multiply it by 10
		// thus, 60*10 = 600
		returnVals[0] = ((600*leftFW.getEncVelocity())/1024);
		returnVals[1] = ((600*rightFW.getEncVelocity())/1024);
		return returnVals;
	}
	
	public void setFlywheelSpeed(double speed){
		rightFW.set(speed);
		leftFW.set(speed);
		Robot.table.putBoolean("turret", speed>0);
	}
	
	public double getFlywheelSpeed(){
		return leftFW.get();
	}
	public void setIntakeSpeed(double speed){
		intakeMotor.set(speed);
		Robot.table.putBoolean("intake", speed!=0);
		Robot.table.putBoolean("INTAKE-REVERSE", speed<0);
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
                                                                                                             