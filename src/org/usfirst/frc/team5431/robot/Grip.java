package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.robot.Robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Class that gets values from Grip and sends them to VisionMath.java
 * to be processed.
 * 
 * @author David Smerkous, Liav Turkia (documenter)
 */
public class Grip {
	
	private NetworkTable grip;
	private final double[] defaults = {0, 0, 0, 0};
	
	public Grip() {
		try {
			grip = NetworkTable.getTable("GRIP/vision");
		} catch(Throwable gripError) {
			Robot.table.putString("ERROR", "Couldn't get grip table");
			gripError.printStackTrace();
		}
	}
	
	/**
	 * Stops the {@linkplain NetworkTable} safely, to stop any errors.
	 * */
	public void stop() {
		NetworkTable.shutdown(); //Shutdown table so we don't get dumb error
	}
	
	/**
	 * Gets the X value of all the holes from the {@link NetworkTable}
	 * 
	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.
	 * */
	public double[] getX() {
		final double holesY[] = grip.getNumberArray("centerX", this.defaults);
		return (this.mult(holesY)) ? holesY : this.defaults; //Same for all below return array 0 if a problem
	}
	
	/**
	 * Gets the Y value of all the holes from the {@link NetworkTable}
	 * 
	 	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] getY() {
		final double holesX[] = grip.getNumberArray("centerY", this.defaults);
		return (this.mult(holesX)) ? holesX : this.defaults;
	}
	
	/**
	 * Gets the distance value of all the holes from the {@link NetworkTable}
	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.
	 * */
	public double[] distance(VisionMath math) {
		final double objects[] = 
				this.getY(), 
				distances[] = {0};
		int num = 0;
		for(double object : objects) { //Get distance for each object
			distances[num] = math.DistanceCalc(object); //Change based on regression
			num++;
		}
		return distances;
	}
	
	/**
	 * Gets the distance from the center of the screen in pixels of all the holes from the {@link NetworkTable}
	 * 
	 * @param HalfSize Center of the camera vision in pixels
	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] fromCenter(double HalfSize, VisionMath math) {
		final double objects[] = 
				this.getX(),
				distances[] = {0};
		int num = 0;
		for(double object : objects) { //Find distance from each object from the center
			distances[num] = math.fromCenter(HalfSize, object);
			num++;
		}
		return distances;
	}
	
    //check if returned array is good
    /**
     * Checks an array to make sure it is valid
     * @return whether the array is good and valid.
     * @param multi Array to validate
     * */
    private boolean mult(double[] multi) {
    	try  {
    		return (multi[0] != 0 && multi.length >= 1); //If no error in array (Null) then return array
    	}
    	catch(Exception ignored) {
    		return false; //If error then return false to 
    	}
    }
	
	/**
	 * Gets the area of all the holes from the {@link NetworkTable}

	 * @param Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] area() { //Get area for each object
		final double areas[] = grip.getNumberArray("area", this.defaults);
		return (this.mult(areas)) ? areas : this.defaults;
	}
	
	/**
	 * Gets the solidity of all the holes from the {@link NetworkTable}

	 	 * @param Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] solidity() { //Get solidity of object 0-100
		final double solidities[] = grip.getNumberArray("solidity", this.defaults);
		return (this.mult(solidities)) ? solidities : this.defaults;
	}
	
}
