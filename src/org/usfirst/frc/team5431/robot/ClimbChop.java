package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbChop {
	final DoubleSolenoid extendClimber, raiseClimber, choppers;
	final CANTalon winch;
	
	public ClimbChop(){
		extendClimber = new DoubleSolenoid(RobotMap.climberExtend1, RobotMap.climberExtend2);
		raiseClimber = new DoubleSolenoid(RobotMap.climberRaise1, RobotMap.climberRaise2);
		choppers = new DoubleSolenoid(RobotMap.choppers1, RobotMap.choppers2);
		extendClimber.set(DoubleSolenoid.Value.kReverse);
		raiseClimber.set(DoubleSolenoid.Value.kReverse);
		choppers.set(DoubleSolenoid.Value.kReverse);
		winch = new CANTalon(RobotMap.winch);
	}
}
