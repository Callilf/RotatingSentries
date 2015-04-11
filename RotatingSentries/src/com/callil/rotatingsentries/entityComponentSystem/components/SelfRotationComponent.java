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
	
	
	/** The speed of the rotation. */
	private float rotationSpeed;

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
	 * @param rotationSpeed the speed of rotation. Positive for trigo rotation, negative for time rotation.
	 */
	public SelfRotationComponent(float rotationSpeed) {
		this.setRotationSpeed(rotationSpeed);
		this.setCurrentRotation(0);
		this.clockwise = true;
		this.active = true;
		this.affectedByButton = false;
	}
	
	/**
	 * Constructor.
	 * @param rotationSpeed the speed of rotation. Positive for trigo rotation, negative for time rotation.
	 * @param currentRotation the initial rotation
	 * @param active if the rotation is active or not
	 */
	public SelfRotationComponent(float rotationSpeed, float currentRotation, boolean active, boolean affectedByButton) {
		this(rotationSpeed);
		this.currentRotation = currentRotation;
		this.active = active;
		this.affectedByButton = affectedByButton;
	}
	
	/**
	 * Constructor.
	 * @param src the {@link SelfRotationComponent} to copy.
	 */
	public SelfRotationComponent(SelfRotationComponent src) {
		this.setRotationSpeed(src.getRotationSpeed());
		this.setCurrentRotation(0);
	}

	
	//Getters & Setters
	
	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
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


}
