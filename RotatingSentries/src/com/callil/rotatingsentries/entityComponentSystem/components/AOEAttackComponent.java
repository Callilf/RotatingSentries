/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;

import com.callil.rotatingsentries.enums.SpriteAnimationEnum;
import com.callil.rotatingsentries.singleton.GameSingleton;

/**
 * @author Callil
 * Defines that the entity can generate an area of effect attack.
 */
public class AOEAttackComponent extends Component {

	/** The trigger of the attack, which means the area that will trigger the attack when an enemy collide with it. */
	private IShape trigger;
	/** The animation of the attack */
	private AnimatedSprite sprite;
	
	/** damage deals on hit */
	private int damage;
	
	/** The cooldown of the attack. */
	private float cooldown;
	
	/** Whether the attack is ready or in cooldown. */
	private boolean ready;
	/** Whether the attack is being performed. */
	private boolean attacking;
	/** The last time the attack has been performed. */
	private float lastAttackTime;
	
	/**
	 * Constructor.
	 * @param sprite the animation sprite
	 * @param cooldown the cooldown of the attack
	 */
	public AOEAttackComponent(IShape trigger, AnimatedSprite sprite, float cooldown, int damage) {
		this.trigger = trigger;
		this.sprite = sprite;
		this.cooldown = cooldown;
		this.damage = damage;
		this.ready = true;
	}
	
	
	/**
	 * Perform an attack.
	 * @return the amount of damages dealt.
	 */
	public int performAttack() {
		setLastAttackTime(GameSingleton.getInstance().getTotalTime());
		setReady(false);
		setAttacking(true);
		sprite.setVisible(true);
		sprite.animate(SpriteAnimationEnum.SENTRY_ELECTRIC_ATTACK.getFrameDurations(), SpriteAnimationEnum.SENTRY_ELECTRIC_ATTACK.getFrames(), false);
		return getDamage();
	}
	
	/**
	 * Finish an attack.
	 * @return the amount of damages dealt.
	 */
	public void finishAttack() {
		setAttacking(false);
		sprite.setVisible(false);
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


	public boolean isReady() {
		return ready;
	}


	public void setReady(boolean ready) {
		this.ready = ready;
	}


	public boolean isAttacking() {
		return attacking;
	}


	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}


	public IShape getTrigger() {
		return trigger;
	}


	public void setTrigger(IShape trigger) {
		this.trigger = trigger;
	}

}
