package com.callil.rotatingsentries.entityComponentSystem.components.attackDefense;

/**
 * Entity that can hit and be hit by DefenseComponent
 * 
 * @author Thomas
 */
public class AttackComponent extends AbstractAttDefComponent {

	/**
	 * Constructor
	 * @param hp initial hp.
	 * @param damage damage on hit
	 */
	public AttackComponent(int hp, int damage) {
		super(hp,damage);
	}

	// GETTER & SETTER
	
	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
