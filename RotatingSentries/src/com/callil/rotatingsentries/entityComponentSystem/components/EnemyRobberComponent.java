/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.util.Couple;

/**
 * @author Callil
 * Defines that this entity is a robber. This component is used to manage the workflow of a robber.
 */
public class EnemyRobberComponent extends Component {

	public enum EnemyRobberStateType {
		INITIALIZING,
		ARRIVING,
		WALKING,
		ATTACKING;
	}
	
	/**
	 * The state of the robber.
	 */
	private EnemyRobberStateType state;
	
	/**
	 * The speed of the robber when walking.
	 */
	private float speed;
	/**
	 * The target towards which the robber will move.
	 */
	private Sprite target;
	
	/** The position at which the robber will arrive in climbing and start walking. */
	private Couple<Float> arrivingPosition;
	
	/**
	 * The rope.
	 */
	private Sprite rope;
	private float ropeRotation;
	
	
	
	/**
	 * Constructor.
	 */
	public EnemyRobberComponent(float speed, Sprite target, Sprite rope, float ropeRotation) {
		this(EnemyRobberStateType.INITIALIZING, speed, target, rope, ropeRotation);
	}
	
	/**
	 * Constructor.
	 */
	public EnemyRobberComponent(EnemyRobberStateType state, float speed, Sprite target, Sprite rope, float ropeRotation) {
		this.state = state;
		this.speed = speed;
		this.target = target;
		this.rope = rope;
		this.rope.setVisible(false);
		this.ropeRotation = ropeRotation;
	}

	
	
	public EnemyRobberStateType getState() {
		return state;
	}

	public void setState(EnemyRobberStateType state) {
		this.state = state;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Sprite getTarget() {
		return target;
	}

	public void setTarget(Sprite target) {
		this.target = target;
	}

	/**
	 * @return the rope
	 */
	public Sprite getRope() {
		return rope;
	}

	/**
	 * @param rope the rope to set
	 */
	public void setRope(Sprite rope) {
		this.rope = rope;
	}

	/**
	 * @return the ropeRotation
	 */
	public float getRopeRotation() {
		return ropeRotation;
	}

	/**
	 * @param ropeRotation the ropeRotation to set
	 */
	public void setRopeRotation(float ropeRotation) {
		this.ropeRotation = ropeRotation;
	}

	public Couple<Float> getArrivingPosition() {
		return arrivingPosition;
	}

	public void setArrivingPosition(Couple<Float> arrivingPosition) {
		this.arrivingPosition = arrivingPosition;
	}


}
