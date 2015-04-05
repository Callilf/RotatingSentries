/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;

/**
 * @author Callil
 * Defines that the entity has a sprite.
 */
public class SpriteComponent extends Component {

	/** The sprite. */
	private Sprite sprite;
	
	/** Whether the sprite can go out of the screen of not. */
	private boolean bounded;
	
	/** Whether it is attached to a scene or entity. */
	private boolean attached;
	
	/** The blink modifier (to make it blink when it has been damaged). */
	private LoopEntityModifier blinkModifier;
	/** Whether the sprite has it's blinkModifier activated. */
	private boolean blinking;
	
	/**
	 * Constructor.
	 */
	public SpriteComponent(Sprite sprite, boolean bounded) {
		this.sprite = sprite;
		this.bounded = bounded;
		
		this.setAttached(false);
		
		//Blink modifier
		this.blinkModifier = new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(0.1f), new FadeInModifier(0.1f)));
		this.blinking = false;
	}

	
	//Getters & Setters
	
	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	/**
	 * @return the bounded
	 */
	public boolean isBounded() {
		return bounded;
	}

	/**
	 * @param bounded the bounded to set
	 */
	public void setBounded(boolean bounded) {
		this.bounded = bounded;
	}


	/**
	 * @return the blinkModifier
	 */
	public LoopEntityModifier getBlinkModifier() {
		return blinkModifier;
	}


	/**
	 * @param blinkModifier the blinkModifier to set
	 */
	public void setBlinkModifier(LoopEntityModifier blinkModifier) {
		this.blinkModifier = blinkModifier;
	}


	/**
	 * @return the blinking
	 */
	public boolean isBlinking() {
		return blinking;
	}


	/**
	 * @param blinking the blinking to set
	 */
	public void setBlinking(boolean blinking) {
		this.blinking = blinking;
	}


	/**
	 * @return the attached
	 */
	public boolean isAttached() {
		return attached;
	}


	/**
	 * @param attached the attached to set
	 */
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
}
