
package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5431.robot.driveBase;
import org.usfirst.frc.team5431.robot.Shooter;
/**
 * This is the second version of competition code (sans David as per request). This competition code is intended to be a functional
 * version without the bells and whistles that are added to the first version of the competition code. 
 */
public class Robot extends IterativeRobot {
	public static NetworkTable table;
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    static driveBase drivebase = new driveBase();
    static Shooter flywheels = new Shooter();
    static Teleop teleop = new Teleop();
    static Autonomous auton = new Autonomous();
    static OI oiInput = new OI(0, 1);
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	//table = NetworkTable.getTable("5431");
    	//NetworkTable.setServerMode();
		//NetworkTable.setIPAddress("roborio-5431-frc.local");
		table = NetworkTable.getTable("5431");
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	Robot.table.putBoolean("AUTO", true);
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }
    
    public void disabledPeriodic(){
    	Robot.table.putBoolean("AUTO", false);
    	Robot.table.putBoolean("ENABLED", false);
    	Robot.table.putBoolean("connection", true);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	/*
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    	*/
    	auton.updateStates();
    	Robot.table.putBoolean("connection", true);
    	Robot.table.putBoolean("AUTO", true);
    }

    /**
     * This function is called periodically during operator control.
     * Calls the update functions for the OI and the Teleop classes.
     */
    public void teleopPeriodic() {
        oiInput.updateVals();
        teleop.Update(oiInput);
        Robot.table.putBoolean("ENABLED", true);
        Robot.table.putBoolean("connection", true);
        final double[] rpms = flywheels.getRPM();
		Robot.table.putNumber("FLY-LEFT", rpms[0]);
		Robot.table.putNumber("FLY-RIGHT", rpms[1]);
		final double[] driverpm = drivebase.getEncDistance();
		Robot.table.putNumber("DRIVE-DISTANCE-LEFT", driverpm[0]);
		Robot.table.putNumber("DRIVE-DISTANCE-RIGHT", driverpm[1]);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
