/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.sprite.Sprite;

/**
 * @author Callil
 * Defines that the entity rotates around a sprite.
 */
public class LevitatingItemComponent extends Component {

	/** The sprite around which the entity rotates. */
	private Sprite parent;
	
	/** The rotation speed (positive or negative). */
	private float speed;
	
	/** The distance between the entity and it's rotation center. */
	private float radius;
	
	/** The amount of damages dealt by each hit. */
	private int damages;
	
	/** The max number of stacked items. If 1, not stackable.
	 * DO NOT PUT 0 ! */
	private int stackableNumber;
	
	/** Whether the item has been picked up or not. */
	private boolean active;
	
	/** The rotation component. */
	private SelfRotationComponent rotationComponent;
	
	//Begin Computation attributes
	private boolean initiated;
	private float currentDeg;
	private float currentX1;
	private float currentY1;
	//End computation attributes
	
	
	/**
	 * Constructor.
	 */
	public LevitatingItemComponent(float speed, float radius, SelfRotationComponent src, int damages, int stackableNumber) {
		this.speed = speed;
		this.radius = radius;
		this.rotationComponent = src;
		this.damages = damages;
		this.stackableNumber = stackableNumber;
		this.currentDeg = 0;
		this.initiated = false;
		this.active = false;
	}
	
	/**
	 * Constructor.
	 */
	public LevitatingItemComponent(float speed, float radius, SelfRotationComponent src, int damages, int stackableNumber, boolean active) {
		this.speed = speed;
		this.radius = radius;
		this.rotationComponent = src;
		this.damages = damages;
		this.stackableNumber = stackableNumber;
		this.active = active;
		this.currentDeg = 0;
		this.initiated = false;
	}
	
	
	
	
	//Gettes & Setters

	/**
	 * @return the centerSprite
	 */
	public Sprite getParent() {
		return parent;
	}

	/**
	 * @param centerSprite the centerSprite to set
	 */
	public void setParent(Sprite parent) {
		this.parent = parent;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * @return the currentDeg
	 */
	public float getCurrentDeg() {
		return currentDeg;
	}

	/**
	 * @param currentDeg the currentDeg to set
	 */
	public void setCurrentDeg(float currentDeg) {
		this.currentDeg = currentDeg;
	}

	/**
	 * @return the currentX1
	 */
	public float getCurrentX1() {
		return currentX1;
	}
	
	/**
	 * @param currentX1 the currentX1 to set
	 */
	public void setCurrentX1(float currentX1) {
		this.currentX1 = currentX1;
	}

	/**
	 * @return the currentY1
	 */
	public float getCurrentY1() {
		return currentY1;
	}
	
	/**
	 * @param currentY1 the currentY1 to set
	 */
	public void setCurrentY1(float currentY1) {
		this.currentY1 = currentY1;
	}

	/**
	 * @return the initiated
	 */
	public boolean isInitiated() {
		return initiated;
	}

	/**
	 * @param initiated the initiated to set
	 */
	public void setInitiated(boolean initiated) {
		this.initiated = initiated;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the rotationComponent
	 */
	public SelfRotationComponent getRotationComponent() {
		return rotationComponent;
	}

	/**
	 * @param rotationComponent the rotationComponent to set
	 */
	public void setRotationComponent(SelfRotationComponent rotationComponent) {
		this.rotationComponent = rotationComponent;
	}

	/**
	 * @return the stackableNumber
	 */
	public int getStackableNumber() {
		return stackableNumber;
	}

	/**
	 * @param stackableNumber the stackableNumber to set
	 */
	public void setStackableNumber(int stackableNumber) {
		this.stackableNumber = stackableNumber;
	}

	/**
	 * @return the damages
	 */
	public int getDamages() {
		return damages;
	}

	/**
	 * @param damages the damages to set
	 */
	public void setDamages(int damages) {
		this.damages = damages;
	}

}
