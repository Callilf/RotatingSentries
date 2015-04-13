package com.callil.rotatingsentries.entityComponentSystem.components;

/**
 * Entity that can hit and be hit by DefenseComponent
 * 
 * @author Thomas
 */
public class AttackComponent extends Component {

	/** hit points */
	private int hp;
	
	/** damage deals on hit */
	private int damage;
	
	/**
	 * Constructor
	 * @param hp initial hp.
	 * @param damage damage on hit
	 */
	public AttackComponent(int hp, int damage) {
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
