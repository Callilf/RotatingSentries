/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.attackDefense;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.color.Color;

import com.callil.rotatingsentries.GameActivity;
import com.callil.rotatingsentries.singleton.GameSingleton;

import android.util.Log;

/**
 * Defines that the entity can explode and cause damages to both attack and defense.
 * @author Callil
 *
 */
public class ExplosiveComponent extends AbstractAttDefComponent {

	/** The radius of the blast. */
	private RectangularShape blastArea;
	
	/** The sprite of the explosion. */
	private AnimatedSprite explosion;
	
	
	
	//proximity explosive attributes
	/** Detection area if the explosive is triggered by enemies. */
	private RectangularShape detectionArea;
	/** Whether it has been triggered. */
	private boolean triggered;
	/** The time when it was triggered. */
	private float triggeredTime;
	/** The time it takes to explode after triggered. */
	private float timeBeforeExplosion;
	

	
	/**
	 * @param hp
	 * @param damage
	 */
	public ExplosiveComponent(int damage, RectangularShape blastArea, AnimatedSprite explosion) {
		super(1, damage);
		this.blastArea = blastArea;
		this.blastArea.setVisible(false);		
		this.explosion = explosion;
		this.explosion.setVisible(false);
		
		if (GameActivity.DEBUG_MODE) {
			this.blastArea.setVisible(true); // to set to false
			this.blastArea.setAlpha(10); // DOESN'T WORK
			this.blastArea.setColor(Color.BLUE);
		}
	}
	
	/**
	 * @param damage the amount of damages
	 * @param trigger area that triggers the explosion
	 * @param timeBeforeExplosion timme before explosion after triggered
	 */
	public ExplosiveComponent(int damage, RectangularShape blastArea, RectangularShape trigger, 
			float timeBeforeExplosion, AnimatedSprite explosion) {
		this(damage, blastArea, explosion);
		this.detectionArea = trigger;
		this.detectionArea.setVisible(false);
		this.timeBeforeExplosion = timeBeforeExplosion;
		Log.d("RS", "ExplosiveComponent created.");
		
		if (GameActivity.DEBUG_MODE) {
			this.detectionArea.setVisible(true); // to set to false
			this.detectionArea.setAlpha(10); // DOESN'T WORK
			this.detectionArea.setColor(Color.PINK);
		}
	}
	
	
	@Override
	public void destroy() {
		this.blastArea.detachSelf();
		this.detectionArea.detachSelf();
		this.explosion.detachSelf();
	}
	
	/** Trigger the mine. */
	public void trigger() {
		this.triggered = true;
		this.triggeredTime = GameSingleton.getInstance().getTotalTime();
	}
	
	
	//Get Set

	public RectangularShape getBlastArea() {
		return blastArea;
	}

	public void setBlastArea(RectangularShape blastArea) {
		this.blastArea = blastArea;
	}

	public RectangularShape getDetectionArea() {
		return detectionArea;
	}

	public void setDetectionArea(RectangularShape detectionArea) {
		this.detectionArea = detectionArea;
	}

	public float getTimeBeforeExplosion() {
		return timeBeforeExplosion;
	}

	public void setTimeBeforeExplosion(float timeBeforeExplosion) {
		this.timeBeforeExplosion = timeBeforeExplosion;
	}

	public boolean isTriggered() {
		return triggered;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	public float getTriggeredTime() {
		return triggeredTime;
	}

	public void setTriggeredTime(float triggeredTime) {
		this.triggeredTime = triggeredTime;
	}

	public AnimatedSprite getExplosion() {
		return explosion;
	}

	public void setExplosion(AnimatedSprite explosion) {
		this.explosion = explosion;
	}

}
