
package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5431.robot.driveBase;
import org.usfirst.frc.team5431.robot.Shooter;
/**
 * This is the second version of competition code (sans David as per request). This competition code is intended to be a functional
 * version without the bells and whistles that are added to the first version of the competition code. 
 */
public class Robot extends IterativeRobot {
    SendableChooser chooser;
    static driveBase drivebase;
    static Shooter flywheels;
    static ClimbChop pneumatics;
    static Teleop teleop;
    static Autonomous auton;
    static OI oiInput;
    static SendableChooser auton_select;
    
	enum AutoTask{ RockWall, Moat, TouchOuterWork, Lowbar, AutoShoot, StandStill, CrossOuter, Spybox};
	static AutoTask currentAuto;
	
	public static final boolean brakeMode = false;
	private static boolean runOnce = false;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		
		//Autonomous selection
        auton_select = new SendableChooser();
        auton_select.addDefault("AutoShoot Lowbar", AutoTask.AutoShoot);
        auton_select.addObject("StandStill", AutoTask.StandStill);
        auton_select.addObject("Touch Outer", AutoTask.TouchOuterWork);
        auton_select.addObject("Cross Outer", AutoTask.CrossOuter);
        auton_select.addObject("Moat Over", AutoTask.Moat);
        auton_select.addObject("Rockwall Over", AutoTask.RockWall);
        auton_select.addObject("Spybox", AutoTask.Spybox);
        SmartDashboard.putData("Auto choices", auton_select);
        
        drivebase = new driveBase(brakeMode);
        flywheels = new Shooter();
        teleop = new Teleop();
        auton = new Autonomous();
        oiInput = new OI(0, 1);
        pneumatics = new ClimbChop();
        
        //SmarterDashboard.addDebugString("Robot started");
        
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
    	SmarterDashboard.putBoolean("AUTO", true);
    	currentAuto = (AutoTask) auton_select.getSelected();
 		SmartDashboard.putString("Auto Selected: ", currentAuto.toString());
 		drivebase.resetDrive();
    }
    
    public void disabledPeriodic(){
    	SmarterDashboard.putBoolean("AUTO", false);
    	SmarterDashboard.putBoolean("ENABLED", false);
    	SmarterDashboard.putBoolean("connection", true);
    	SmarterDashboard.periodic();
    	drivebase.resetDrive();
    	//SwitchCase.moveAmount = 0.468;
    	Autonomous.autoAIMState = false;
    	Autonomous.currAIM = 0;
    	Autonomous.driveForwardState = 0;
    	Timer.delay(1); //Sleep a little for little overhead time
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(!runOnce) {
    		flywheels.leftFW.setVoltageRampRate(1);
    		flywheels.rightFW.setVoltageRampRate(1);
    		runOnce = true;
    	}
    	auton.updateStates(currentAuto);
    	SmarterDashboard.putBoolean("connection", true);
    	SmarterDashboard.putBoolean("AUTO", true);
    	Timer.delay(0.005); // Wait 50 Hz
    	SmarterDashboard.periodic();
    }

    /**
     * This function is called periodically during operator control.
     * Calls the update functions for the OI and the Teleop classes.
     */
    public void teleopPeriodic() {
    	/*if(!runOnce) {
    		SwitchCase.moveAmount = 0.468;
    		runOnce = true;
    	}*/
    	//SwitchCase.moveAmount = 0.468;
        oiInput.updateVals();
        teleop.Update(oiInput);
        
        //Update connection
        SmarterDashboard.putBoolean("ENABLED", true);
        SmarterDashboard.putBoolean("connection", true);
        
        //Update RPM for fly wheels
        final double[] rpms = flywheels.getRPM();
		SmarterDashboard.putNumber("FLY-LEFT", rpms[0]);
		SmarterDashboard.putNumber("FLY-RIGHT", rpms[1]);
		
		//Update drivetrain distance
		final double[] driverpm = drivebase.getEncDistance();
		SmarterDashboard.putNumber("DRIVE-DISTANCE-LEFT", driverpm[0]);
		SmarterDashboard.putNumber("DRIVE-DISTANCE-RIGHT", driverpm[1]);
    	SmarterDashboard.periodic();
    }

    public void testPeriodic() {} //Not used
    
}
