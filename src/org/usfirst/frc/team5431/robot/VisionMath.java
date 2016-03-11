package org.usfirst.frc.team5431.robot;

public class VisionMath {
	
	//Choose hole options (Total should be 1.0)
	private static final double 
			areaNum = 0.2,
			distNum = 0.2,
			solidNum = 0.4,
			fromNum = 0.2;
	
	//Distances and resolution values
	public static final double 
			screenHalf = 160,
			minDistance = 107,
			maxDistance = 180,
			leftTrig = -60,
			rightTrig = 60;
	
	private static final double
			maxArea = 200,
			maxSolidity = 100;
			
	public static volatile double override = 0.0;
	
	/**
	 * Calculates the distance of a location
	 * <p>
	 * <b>Make sure to pretest the values</b>
	 * 
	 * @param pixelsFromTop Number of pixels from the top
	 * @return The distance from the hole
	 * */
	public double DistanceCalc(double pixelsFromTop) {
		return ((50.6234) * Math.pow(1.0072, pixelsFromTop)); //THE NEW BOT (SHOULD BE VERY CLOSE)
	}
	
	public double SpeedCalc(double distanceFromTower) {
		return Math.pow((10.9685 * distanceFromTower), -0.5264);//((3.4028) - (0.5551 * Math.log(distanceFromTower))) + override;
	}
	
	/**
	 * Checks the distance of a location from the center of the camera
	 * @param half Center of the camera, in pixels
	 * @param current Location to check
	 * @return Distance from the center of the camera, in pixels. Negative values means it's to the left. 
	 * */
	public double fromCenter(double half, double current) {
		return current - half;
	}
	
	double largest = 0; //Don't mess
	int current = 0; //Don't mess
	
	/**
	 * Returns which hole to use based on various info
	 * @param areas Array with the area of each hole, where each index refers to a hole (array[0]=hole 0)
	 * @param distances Array with the distance of each hole from the camera, where each index refers to a hole (array[0]=hole 0)
	 * @param solidity  Array with the solidity of each hole, where each index refers to a hole (array[0]=hole 0)
	 * @param fromCenter Array with the distance of each hole from the center of the camera in pixels, where each index refers to a hole (array[0]=hole 0.) Negative means to the left.
	* @return Hole ID based on the parameters. If 666 is returned, no hole was found.
	 * */
	public int chooseHole(double[] areas, double[] distances, double[] solidity, double[] fromCenter)
    {	
		final int amount = areas.length;
		
    	try
    	{
    		final double holes[] = {0}; //Don't mess
		
    		//If any of the values are negative make sure that they are positive
	    	for(int now = 0; now < amount; now++) {
	    		areas[now] = Math.abs(areas[now]);
	    		distances[now] = Math.abs(distances[now]);
	    		solidity[now] = Math.abs(solidity[now]);
	    		fromCenter[now] = Math.abs(fromCenter[now]);
	    		
	    		holes[now] = (((areas[now]/maxArea) * areaNum) + ((1 - (distances[now]/maxDistance)) * distNum)
	    		+ ((solidity[now]/maxSolidity) * solidNum) - ((fromCenter[now]/screenHalf) * fromNum))/4;
	    		
	    		if(holes[now] > largest) {
	    			largest = holes[now];
	    			current = now;
	    		}
	    		
	    	}
	    	return current;
    	}
    	catch(Exception ignored) {
    		return 666; //Return 666 which means none found
    	}
    }
	
	//See if number is within two other numbers
	/**
	 * Checks to see if a number is within two other numbers
	 * @param num Number to compare
	 * @param lower Lower bound
	 * @param upper Upper bound
	 * @return Whether num is within lower and upper bounds.
	 * */
    public boolean withIn(double num, double lower, double upper) {
    	return ((num >= lower) && (num <= upper));
    }
	
}
