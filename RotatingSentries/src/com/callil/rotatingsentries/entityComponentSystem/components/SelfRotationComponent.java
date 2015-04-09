/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;


/**
 * @author Callil
 * Defines that the entity rotates on itself.
 */
public class SelfRotationComponent extends Component {

	/** The speed of the rotation. */
	private float rotationSpeed;

	/** the current value of the rotation. */
	private float currentRotation;
	
	
	/**
	 * Constructor.
	 * @param rotationSpeed the speed of rotation. Positive for trigo rotation, negative for time rotation.
	 */
	public SelfRotationComponent(float rotationSpeed) {
		this.setRotationSpeed(rotationSpeed);
		this.setCurrentRotation(0);
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
	
	/**
	 * @return the rotationSpeed
	 */
	public float getRotationSpeed() {
		return rotationSpeed;
	}

	/**
	 * @param rotationSpeed the rotationSpeed to set
	 */
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	/**
	 * @return the currentRotation
	 */
	public float getCurrentRotation() {
		return currentRotation;
	}


	/**
	 * @param currentRotation the currentRotation to set
	 */
	public void setCurrentRotation(float currentRotation) {
		this.currentRotation = currentRotation;
	}


}
