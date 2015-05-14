package com.callil.rotatingsentries.entityComponentSystem.components.attackDefense;

/**
 * Entity that can hit and be hit by AttackComponent
 * 
 * @author Thomas
 */
public class DefenseComponent extends AttDefComponent {

	/** Whether the entity bounces against solid objects. */
	private boolean bounce;
	
	public DefenseComponent(int hp, int damage, boolean bounce) {
		super(hp, damage);
		this.bounce = bounce;
	}

	public boolean isBounce() {
		return bounce;
	}

	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

}
