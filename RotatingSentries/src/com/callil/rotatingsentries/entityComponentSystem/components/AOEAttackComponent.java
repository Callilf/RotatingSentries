/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.sprite.AnimatedSprite;

/**
 * @author Callil
 * Defines that the entity can generate an area of effect attack.
 */
public class AOEAttackComponent extends Component {

	/** The animation of the attack */
	private AnimatedSprite sprite;
	
	/** damage deals on hit */
	private int damage;
	
	/** The cooldown of the attack. */
	private float cooldown;
	
	/** The last time the attack has been performed. */
	private float lastAttackTime;
	
	/**
	 * Constructor.
	 * @param sprite the animation sprite
	 * @param cooldown the cooldown of the attack
	 */
	public AOEAttackComponent(AnimatedSprite sprite, float cooldown, int damage) {
		this.sprite = sprite;
		this.cooldown = cooldown;
		this.damage = damage;
	}


	public AnimatedSprite getSprite() {
		return sprite;
	}

	public void setSprite(AnimatedSprite sprite) {
		this.sprite = sprite;
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}

	public float getLastAttackTime() {
		return lastAttackTime;
	}

	public void setLastAttackTime(float lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}


	public int getDamage() {
		return damage;
	}


	public void setDamage(int damage) {
		this.damage = damage;
	}

}
