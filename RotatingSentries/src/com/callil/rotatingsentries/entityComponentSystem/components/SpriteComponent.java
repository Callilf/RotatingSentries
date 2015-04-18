/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import com.callil.rotatingsentries.GameActivity;

/**
 * @author Callil
 * Defines that the entity has a sprite.
 */
public class SpriteComponent extends Component {

	/** The sprite. */
	private Sprite sprite;
	
	/** Hitbox of the sprite */
	private IShape hitbox;
	
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
		this.hitbox = sprite;
		
		this.bounded = bounded;
		
		this.setAttached(false);
		
		//Blink modifier
		this.blinkModifier = new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(0.1f), new FadeInModifier(0.1f)));
		this.blinking = false;
	}
	
	/**
	 * Set the hitbox with the 4 coordinates relatively to the Sprite
	 * 
	 * @param dxmin left distance to the sprite
	 * @param dymin top distance to the sprite
	 * @param dxmax right distance to the sprite
	 * @param dymax bottom distance to the sprite
	 * @return this so this call can be inline
	 */
	public SpriteComponent defineRectangularHitboxDiff(float dxmin, float dymin, float dxmax, float dymax) {
		return defineRectangularHitbox(dxmin, dymin, sprite.getWidth() - dxmin - dxmax, sprite.getHeight() - dymin - dymax);
	}
	
	/**
	 * Set the hitbox relatively to the Sprite
	 * 
	 * @param xmin left distance to the sprite
	 * @param ymin top distance to the sprite
	 * @param width width of the hitbox
	 * @param height height of the hitbox
	 * @return this so this call can be inline
	 */
	public SpriteComponent defineRectangularHitbox(float xmin, float ymin, float width, float height) {
		sprite.setColor(Color.RED);
		hitbox = new Rectangle(xmin, ymin, width, height, sprite.getVertexBufferObjectManager());
		
		if (GameActivity.DEBUG_MODE) {
			hitbox.setVisible(true); // to set to false
			hitbox.setAlpha(10); // DON'T WORK
			hitbox.setColor(Color.BLUE);
		}
		sprite.attachChild(hitbox);
		return this;
	}
	
	@Override
	public void destroy() {
		if (sprite != null) {
			sprite.detachSelf();
		}
	}
	
	//Getters & Setters
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public IShape getHitbox() {
		return hitbox;
	}

	public void setHitbox(IShape hitbox) {
		this.hitbox = hitbox;
	}

	public boolean isBounded() {
		return bounded;
	}

	public void setBounded(boolean bounded) {
		this.bounded = bounded;
	}

	public LoopEntityModifier getBlinkModifier() {
		return blinkModifier;
	}

	public void setBlinkModifier(LoopEntityModifier blinkModifier) {
		this.blinkModifier = blinkModifier;
	}

	public boolean isBlinking() {
		return blinking;
	}

	public void setBlinking(boolean blinking) {
		this.blinking = blinking;
	}


	public boolean isAttached() {
		return attached;
	}

	public void setAttached(boolean attached) {
		this.attached = attached;
	}
}
