
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
    SendableChooser chooser;
    static driveBase drivebase;
    static Shooter flywheels;
    static Teleop teleop;
    static Autonomous auton;
    static OI oiInput;
    static SendableChooser auton_select;
	public static NetworkTable table;
    
	enum AutoTask{ AutoShoot, StandStill};
	static AutoTask currentAuto;
	
	public static final boolean brakeMode = false;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		table = NetworkTable.getTable("5431");
		
		//Autonomous selection
        auton_select = new SendableChooser();
        auton_select.addDefault("AutoShoot Lowbar", AutoTask.AutoShoot);
        auton_select.addObject("StandStill", AutoTask.StandStill);
        
        SmartDashboard.putData("Auto choices", auton_select);
        
        drivebase = new driveBase(brakeMode);
        flywheels = new Shooter();
        teleop = new Teleop();
        auton = new Autonomous();
        oiInput = new OI(0, 1);
        
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
    	table.putBoolean("AUTO", true);
    	currentAuto = (AutoTask) auton_select.getSelected();
 		SmartDashboard.putString("Auto Selected: ", currentAuto.toString());
    }
    
    public void disabledPeriodic(){
    	table.putBoolean("AUTO", false);
    	table.putBoolean("ENABLED", false);
    	table.putBoolean("connection", true);
    	Timer.delay(1); //Sleep a little for little overhead time
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	auton.updateStates(currentAuto);
    	table.putBoolean("connection", true);
    	table.putBoolean("AUTO", true);
    	Timer.delay(0.005); // Wait 50 Hz
    }

    /**
     * This function is called periodically during operator control.
     * Calls the update functions for the OI and the Teleop classes.
     */
    public void teleopPeriodic() {
        oiInput.updateVals();
        teleop.Update(oiInput);
        
        //Update connection
        table.putBoolean("ENABLED", true);
        table.putBoolean("connection", true);
        
        //Update RPM for fly wheels
        final double[] rpms = flywheels.getRPM();
		table.putNumber("FLY-LEFT", rpms[0]);
		table.putNumber("FLY-RIGHT", rpms[1]);
		
		//Update drivetrain distance
		final double[] driverpm = drivebase.getEncDistance();
		table.putNumber("DRIVE-DISTANCE-LEFT", driverpm[0]);
		table.putNumber("DRIVE-DISTANCE-RIGHT", driverpm[1]);
    }

    public void testPeriodic() {} //Not used
    
}
