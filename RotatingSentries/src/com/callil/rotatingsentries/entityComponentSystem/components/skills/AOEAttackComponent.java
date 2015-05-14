/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.skills;

import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.callil.rotatingsentries.enums.SpriteAnimationEnum;

/**
 * @author Callil
 * Defines that the entity can generate an area of effect attack.
 */
public class AOEAttackComponent extends SkillComponent {

	/** The trigger of the attack, which means the area that will trigger the attack when an enemy collide with it. */
	private IShape trigger;
	/** The animation of the attack */
	private AnimatedSprite sprite;
	
	/** damage deals on hit */
	private int damage;
	
	/** Whether the attack is being performed. */
	private boolean attacking;

	
	/**
	 * Constructor.
	 * @param sprite the animation sprite
	 * @param cooldown the cooldown of the attack
	 */
	public AOEAttackComponent(AnimatedSprite sprite, Sprite icon, Sprite iconFrame, IShape trigger, float cooldown, int damage, VertexBufferObjectManager vb) {
		super(icon, iconFrame, cooldown, vb);
		this.trigger = trigger;
		this.sprite = sprite;
		this.damage = damage;
	}
	
	
	/**
	 * Perform an attack.
	 * @return the amount of damages dealt.
	 */
	public int performAttack() {
		super.performAction();
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

	public int getDamage() {
		return damage;
	}


	public void setDamage(int damage) {
		this.damage = damage;
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
