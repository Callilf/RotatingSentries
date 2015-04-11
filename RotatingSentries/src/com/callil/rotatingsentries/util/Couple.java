/**
 * 
 */
package com.callil.rotatingsentries.util;

/**
 * @author Callil
 * A couple of objects. use couple.getX() and getY() to retrieve the values.
 */
public class Couple<T> {
	
	private T x;
	private T y;

	/**
	 * Constructor.
	 */
	public Couple(T x, T y) {
		this.x = x;
		this.y = y;
	}


	public T getX() {
		return x;
	}

	public void setX(T x) {
		this.x = x;
	}

	public T getY() {
		return y;
	}

	public void setY(T y) {
		this.y = y;
	}

}
