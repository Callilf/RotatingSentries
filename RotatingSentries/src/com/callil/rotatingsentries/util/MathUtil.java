/**
 * 
 */
package com.callil.rotatingsentries.util;

/**
 * @author Callil
 * Utils for computations.
 */
public class MathUtil {

	/**
	 * Forbidden.
	 */
	private MathUtil() {}

	/**
	 * Compute the directional vector to go from source to dest.
	 * @param source the source coordinates
	 * @param dest the destination coordinates
	 * @return the vector.
	 */
	public static Couple<Float> computeDirectionalVector(Couple<Float> source, Couple<Float> dest) {
		Couple<Float> res = new Couple<Float>(0f,0f);
		float vx = dest.getX() - source.getX();
		float vy = dest.getY() - source.getY();
		if (vx != 0 && vy != 0) {
			float xMove = vx/(Math.abs(vx) + Math.abs(vy));
			float yMove = vy/(Math.abs(vx) + Math.abs(vy));
			float speedFactor = (float) ((Math.abs(xMove) + Math.abs(yMove))/Math.sqrt(xMove*xMove + yMove*yMove));
			res.setX(xMove * speedFactor);
			res.setY(yMove * speedFactor);
		}
		
		return res;
	}
	
	/**
	 * Compute the angle corresponding to the rotation needed to face a direction.
	 * THIS ASSUME THAT THE BASIC ORIENTATION (angle = 0) IS FACING DOWN !
	 * If you want to change that, change the + 270 in the computation of angleInDegree
	 * @param directionalVector the direction
	 * @return the angle in degrees
	 */
	public static float computeOrientationAngleFromDirectionalVector(Couple<Float> directionalVector) {
		double angle = Math.atan2(directionalVector.getY(), directionalVector.getX());
		double angleInDegree = ((angle > 0 ? angle : (2*Math.PI + angle)) * 360 / (2*Math.PI) + 270) % 360;
		return (float)angleInDegree;
	}
	
}
