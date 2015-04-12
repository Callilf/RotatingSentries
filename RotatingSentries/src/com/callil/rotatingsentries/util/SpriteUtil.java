/**
 * 
 */
package com.callil.rotatingsentries.util;

import org.andengine.entity.sprite.Sprite;

/**
 * @author Callil
 * Useful methods for handling Sprites.
 */
public class SpriteUtil {


	private SpriteUtil() {}

	/**
	 * Return the coordinates of the center of the sprite.
	 * @param sprite the sprite
	 * @return the center of the sprite
	 */
	public static Couple<Float> getCenter(Sprite sprite) {
		return new Couple<Float>(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);	
	}
	
	/**
	 * Return the distance between the center of 2 sprites.
	 * @param source the source sprite
	 * @param dest the destination sprite
	 * @return the distance between the center of both sprites
	 */
	public static float distanceBetweenCenters(Sprite source, Sprite dest) {
		Couple<Float> sourceCenter = getCenter(source);
		Couple<Float> destCenter = getCenter(dest);
		float dist = (float) Math.abs(Math.sqrt(
	            Math.pow(sourceCenter.getX() - destCenter.getX(), 2) +
	            Math.pow(sourceCenter.getY() - destCenter.getY(), 2) ));
		return dist;
	}
}
