package com.callil.rotatingsentries.entityComponentSystem.components.attackDefense;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * Entity that can hit and be hit by DefenseComponent
 * 
 * @author Thomas
 */
public class AttDefComponent extends Component {

	/** hit points */
	protected int hp;
	
	/** damage deals on hit */
	protected int damage;
	
	/**
	 * Constructor
	 * @param hp initial hp.
	 * @param damage damage on hit
	 */
	public AttDefComponent(int hp, int damage) {
		this.hp = hp;
		this.damage = damage;
	}
	
	/**
	 * The entity is hit.
	 * @param damage the damages
	 * @return true if the entity is dead, false otherwise
	 */
	public boolean hit(int damage) {
		hp -= damage;
		return hp <= 0;
	}

	// GETTER & SETTER
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
