/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.callil.rotatingsentries.singleton.GameSingleton;

/**
 * @author Callil
 * Defines that the entity can generate an area of effect attack.
 */
public class SkillComponent extends Component {

	/** The sprite of the icon to display to the upper left of the HUD. */
	private Sprite iconSprite;
	
	/** The rectangle representing the cooldown (alpha=0.5). */
	private Rectangle cooldownRectangle;
	private float cooldownRectangeMaxHeight;
	
	/** The frame of the icon. */
	private Sprite iconFrame;
	
	/** The cooldown of the attack. */
	private float cooldown;
	
	/** Whether the attack is ready or in cooldown. */
	private boolean ready;
	
	/** The last time the attack has been performed. */
	private float lastAttackTime;
	
	/**
	 * Constructor.
	 * @param sprite the animation sprite
	 * @param cooldown the cooldown of the attack
	 */
	public SkillComponent(Sprite sprite, Sprite frame, float cooldown, VertexBufferObjectManager vb) {
		this.iconSprite = sprite;
		this.iconSprite.setZIndex(10);
		this.iconFrame = frame;
		this.iconFrame.setZIndex(12);
		this.cooldown = cooldown;
		this.ready = true;
		
		Rectangle cooldownRect = new Rectangle(sprite.getX() + 3, sprite.getY() + 3, sprite.getWidth() -6 , sprite.getHeight() - 6, vb);
		cooldownRect.setColor(0, 0, 0, 0.5f);
		this.cooldownRectangle = cooldownRect;
		this.cooldownRectangle.setZIndex(11);
		this.cooldownRectangle.setVisible(false);
		this.cooldownRectangeMaxHeight = cooldownRect.getHeight();
	}
	
	
	/**
	 * Perform an attack.
	 * @return the amount of damages dealt.
	 */
	public void performAction() {
		setLastAttackTime(GameSingleton.getInstance().getTotalTime());
		setReady(false);
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

	public boolean isReady() {
		return ready;
	}


	public void setReady(boolean ready) {
		this.ready = ready;
	}


	public Sprite getIconSprite() {
		return iconSprite;
	}


	public void setIconSprite(Sprite iconSprite) {
		this.iconSprite = iconSprite;
	}


	public Sprite getIconFrame() {
		return iconFrame;
	}


	public void setIconFrame(Sprite iconFrame) {
		this.iconFrame = iconFrame;
	}


	public Rectangle getCooldownRectangle() {
		return cooldownRectangle;
	}


	public void setCooldownRectangle(Rectangle cooldownRectangle) {
		this.cooldownRectangle = cooldownRectangle;
	}


	public float getCooldownRectangeMaxHeight() {
		return cooldownRectangeMaxHeight;
	}


	public void setCooldownRectangeMaxHeight(float cooldownRectangeMaxHeight) {
		this.cooldownRectangeMaxHeight = cooldownRectangeMaxHeight;
	}


}
