/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;


/**
 * @author Callil
 * Defines that the entity rotates on itself.
 */
public class SelfRotationComponent extends Component {

	/** 0 if the left button is pressed */
	public static int leftPressed = 0;
	/** 0 if the right button is pressed */
	public static int rightPressed = 0;
	
	/** The acceleration. For instant max speed, set acceleration = maxRotationSpeed */
	private float acceleraton;
	/** The speed of the rotation. */
	private float currentSpeed = 0;
	/** The speed of the rotation. */
	private float maxRotationSpeed;
	/** The speed of the rotation. */
	private float minRotationSpeed;

	/** the current value of the rotation. */
	private float currentRotation;
	
	/** the direction of the rotation */
	private boolean clockwise;
	
	/** if the rotation is active or not */
	private boolean active;
	
	/** if the rotation is active or not */
	private boolean affectedByButton;
	
	/**
	 * Constructor.
	 * @param maxRotationSpeed the speed of rotation. Positive for trigo rotation, negative for time rotation.
	 */
	public SelfRotationComponent(float maxRotationSpeed) {
		this.maxRotationSpeed = maxRotationSpeed;
		this.minRotationSpeed = maxRotationSpeed;
		this.currentSpeed = 0;
		this.currentRotation = 0;
		this.clockwise = true;
		this.active = true;
		this.affectedByButton = false;
	}
	
	/**
	 * Constructor.
	 * @param maxRotationSpeed the speed of rotation. Positive for trigo rotation, negative for time rotation.
	 * @param currentRotation the initial rotation
	 * @param active if the rotation is active or not
	 */
	public SelfRotationComponent(float maxRotationSpeed, float minRotationSpeed, float acceleration, float currentRotation, boolean active, boolean affectedByButton) {
		this(maxRotationSpeed);
		this.minRotationSpeed = minRotationSpeed;
		this.acceleraton = acceleration;
		this.currentRotation = currentRotation;
		this.active = active;
		this.affectedByButton = affectedByButton;
	}
	
	/**
	 * Constructor.
	 * @param src the {@link SelfRotationComponent} to copy.
	 */
	public SelfRotationComponent(SelfRotationComponent src) {
		this.setMaxRotationSpeed(src.getMaxRotationSpeed());
		this.setCurrentRotation(0);
	}
	
	/**
	 * Increase speed if the max is not reach
	 * 
	 * @return the current speed after acceleration
	 */
	public float increaseSpeed() {
		if (currentSpeed == 0) {
			currentSpeed = minRotationSpeed;
		}
		else if (currentSpeed != maxRotationSpeed) {
			currentSpeed = Math.min(maxRotationSpeed, currentSpeed + acceleraton);
		}
		return currentSpeed;
	}

	
	//Getters & Setters
	
	public float getMaxRotationSpeed() {
		return maxRotationSpeed;
	}

	public void setMaxRotationSpeed(float rotationSpeed) {
		this.maxRotationSpeed = rotationSpeed;
	}

	public float getMinRotationSpeed() {
		return minRotationSpeed;
	}

	public void setMinRotationSpeed(float minRotationSpeed) {
		this.minRotationSpeed = minRotationSpeed;
	}

	public float getCurrentRotation() {
		return currentRotation;
	}

	public void setCurrentRotation(float currentRotation) {
		this.currentRotation = currentRotation;
	}

	public boolean isClockwise() {
		return clockwise;
	}

	public void setClockwise(boolean isClockwise) {
		this.clockwise = isClockwise;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAffectedByButton() {
		return affectedByButton;
	}

	public void setAffectedByButton(boolean affectedByButton) {
		this.affectedByButton = affectedByButton;
	}
	
	public boolean isLastLeftPressed() {
		return leftPressed > rightPressed;
	}

	public boolean isLastRightPressed() {
		return rightPressed > leftPressed;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public float getAcceleraton() {
		return acceleraton;
	}

	public void setAcceleraton(float acceleraton) {
		this.acceleraton = acceleraton;
	}


}
