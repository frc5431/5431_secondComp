package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class does Vision processing.
 * 
 * @author David Smerkous (code writer)
 * @author Liav Turkia (code commenter)
 *
 */
public class Vision {
	public volatile static VisionMath math;
	public Grip grip;
	public volatile double screenHalf = 120, offVal = 0;

	private static volatile double[] areas = { 0 }, distances = { 0 }, fromCenters = { 0 }, holeSolids = { 0 };

	public static volatile double area = 0, distance = 0, fromCenter = 0, holeSolid = 0;
	
	/**
	 * Contains information about position of robot/actions needed to be taken
	 * based on vision processing. Note that this only updates if the Update()
	 * function is called repeatedly.
	 * Index and what they stand for:
	 * <ol>0 - Whether or not to turn to shoot. 0 is SHOOT, 1 is for Turn Left, 2 is for Turn Right</ol>
	 * <ol>1 - Whether or not to move forward/back. 0 is SHOOT, 1 is for Drive Back, 2 is for Drive Forward</ol>
	 */
	public static volatile double[] manVals = { 0, 0, 0 };
	
	/**
	 * Constructor for the Vision() class.
	 */
	public Vision() {
		try {
			math = new VisionMath();
			grip = new Grip();
		} catch (Throwable initError) {
			Robot.table.putString("ERROR", "Error initializing grip");
			SmartDashboard.putString("stringBug", "Error initializing grip");
		}
	}
	
	/**
	 * Update function for the Vision() class.
	 * <b>This function must be called repeatedly for the Vision() class to work</b>
	 */
	public void Update(){
		this.updateVals();
		this.calcVals();
	}
	
	private void updateVals() {
		areas = grip.area();
		distances = grip.distance(math);
		fromCenters = grip.fromCenter(this.screenHalf, math);
		holeSolids = grip.solidity();
		SmartDashboard.putNumber("Hole Area", areas[0]);
		SmartDashboard.putNumber("Hole distance", distances[0]);
		SmartDashboard.putNumber("Hole fromCenter", fromCenters[0]);
		SmartDashboard.putNumber("holeSolids", holeSolids[0]);
		Robot.table.putNumber("HOLE-AREA", areas[0]);
		Robot.table.putNumber("HOLE-DISTANCE", distances[0]);
		Robot.table.putNumber("HOLE-SOLIDITY", holeSolids[0]);
	}

	private void calcVals() {
		final int toShoot = math.chooseHole(areas, distances, holeSolids, fromCenters); // Chooses
																					// an
																					// object
																					// to
																					// shoot
																					// at(Method
																					// below)
		Robot.table.putNumber("HOLE-NUM", toShoot); // Display to dashboard what
													// to shoot at
		if (toShoot != 666) {// Don't shoot at nothing (THE DEVIL)
			final double tempCenter = grip.fromCenter(this.screenHalf, math)[toShoot]; // Temp
																					// center
																					// values
			// Display values to SmartDashboard!
			Robot.table.putNumber("HOLE-AREA", areas[toShoot]);
			Robot.table.putNumber("HOLE-DISTANCE", distances[toShoot]);
			Robot.table.putNumber("HOLE-CENTER", tempCenter);
			Robot.table.putNumber("HOLE-SOLITIY", holeSolids[toShoot]);

			manVals[1] = (math.withIn(distances[toShoot], VisionMath.minDistance, VisionMath.maxDistance)) ? 0
					: (distances[toShoot] < VisionMath.minDistance) ? 1 : 2; // Get
																				// which
																				// direction
																				// to
																				// drive

			manVals[0] = (math.withIn(tempCenter, VisionMath.leftTrig, VisionMath.rightTrig)) ? 0
					: (tempCenter < VisionMath.leftTrig) ? 1 : 2; // Amount to
																	// turn the
																	// turrent

			Robot.table.putNumber("AUTO-AIM-SPEED", Vision.getSpeed());

			if ((manVals[1] == 0) && (manVals[0] == 0)) {
				Robot.table.putString("FIRE", "F");
				Robot.table.putString("PULL", "F");
				// Robot.led.wholeStripRGB(255, 0, 0);
				manVals[1] = 0;
				manVals[2] = 0;
			} else {
				Robot.table.putString("PULL", "NA");
				Robot.table.putString("FIRE", "NA");

				if (manVals[1] == 1) {
					Robot.table.putString("PULL", "DB");
					// Robot.led.backwards(0, 0, 255, 60);
				} else if (manVals[1] == 2) {
					Robot.table.putString("PULL", "DF");
					// Robot.led.forwards(0, 255, 255, 60);
				}

				if (manVals[0] == 1) {
					Robot.table.putString("FIRE", "TL");
					// Robot.led.turnLeft(255, 135, 0, 65);
				} else if (manVals[0] == 2) {
					Robot.table.putString("FIRE", "TR");
					// Robot.led.turnRight(255, 135, 0, 65);
				}
				
			}
		} else {
			Robot.table.putString("FIRE", "NA");
			Robot.table.putString("PULL", "NA");
			// Robot.led.wholeStripRGB(120, 140, 120);
			manVals[0] = 5;
			manVals[1] = 5;
		}
	}

	public static double getSpeed() {
		return math.SpeedCalc(distance);
	}

	public double getDistance() {
		return distance;
	}

	public double getFromCenter() {
		return fromCenter;
	}

	public double getLeftRight() {
		return manVals[0];
	}

	public double getForwardBackward() {
		return manVals[1];
	}
}
