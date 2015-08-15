package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.AttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.ExplosiveComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractSecondaryAttackComponent.ExplosiveType;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.SecondaryMineComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.SecondaryMineComponent.SecondaryMineState;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.enums.SpriteAnimationEnum;
import com.callil.rotatingsentries.singleton.GameSingleton;
import com.callil.rotatingsentries.util.Couple;
import com.callil.rotatingsentries.util.SpriteUtil;

public class MineSystem extends System {

	private EntityFactory entityFactory;
	private RectangularShape gameArea;
	
	public MineSystem(EntityManager em, EntityFactory ef, RectangularShape gameArea) {
		super(em);
		this.entityFactory = ef;
		this.gameArea = gameArea;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		GameSingleton singleton = GameSingleton.getInstance();
		
		// Handle mine fire compo
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SecondaryMineComponent.class);
		for (Entity entity : entities) {
	    	SecondaryMineComponent secondaryMineComponent = this.entityManager.getComponent(SecondaryMineComponent.class, entity);
	    	Sprite targetSprite = secondaryMineComponent.getTarget();
	    	
			switch (secondaryMineComponent.getState()) {
			case INIT:
				targetSprite.setVisible(false);
				gameArea.attachChild(targetSprite);
				secondaryMineComponent.setState(SecondaryMineState.INACTIVE);
				break;
			case INACTIVE:
				if (secondaryMineComponent.getCurrentAmmo() <= 0) {
					break;
				}
				if (GenerationSystem.isSecondaryFireActive) {
					targetSprite.setVisible(true);
					SpriteUtil.setCenter(targetSprite, gameArea.getWidth()/2, gameArea.getHeight()/4);
					secondaryMineComponent.setState(SecondaryMineState.INIT_PLACING);
				}
				break; 
			case INIT_PLACING:
				if (singleton.isTouchingArea) {
					SpriteUtil.setCenter(targetSprite, singleton.areaTouchX, singleton.areaTouchY);
					secondaryMineComponent.setState(SecondaryMineState.PLACING);
				}
				break;
			case PLACING:
				if (singleton.isTouchingArea) {
					SpriteUtil.setCenter(targetSprite, singleton.areaTouchX, singleton.areaTouchY);
				} else if (!singleton.isTouchingArea) {
					// generate mine at the given position
					Couple<Float> targetCenter = SpriteUtil.getCenter(targetSprite);
					this.entityFactory.generateExplosive(ExplosiveType.MINE, targetCenter.getX(), targetCenter.getY());
					targetSprite.setVisible(false);
					secondaryMineComponent.shoot();
					secondaryMineComponent.setState(SecondaryMineState.INACTIVE);
				}
				break;
			}
	    }
		
		
		//Handle explosives
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(ExplosiveComponent.class);
		for (Entity entity : entities) {
			ExplosiveComponent explosiveComponent = this.entityManager.getComponent(ExplosiveComponent.class, entity);
			SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	RectangularShape detectionArea = explosiveComponent.getDetectionArea();
	    	if (detectionArea != null) {
	    		//Proximity mine
	    		
	    		if (!explosiveComponent.isTriggered()) {
	    			//Still waiting
	    			
	    			//Check detection of attackers
	    			List<Entity> attackers = this.entityManager.getAllEntitiesPosessingComponentOfClass(AttackComponent.class);
	    			for (Entity hittable : attackers) {
	    				SpriteComponent scHittable = this.entityManager.getComponent(SpriteComponent.class, hittable);
	    				if (scHittable != null) { // in case the entity is already dead
	    					IShape hHittable = this.entityManager.getComponent(SpriteComponent.class, hittable).getHitbox();
	    					if (detectionArea.collidesWith(hHittable)) {
	    						// DETECTED !!!
	    						Log.i("RS", "INTRUDER DETECTED - Activate proximity mine !");
	    						explosiveComponent.setTriggered(true);
	    						explosiveComponent.setTriggeredTime(GameSingleton.getInstance().getTotalTime());
	    						
	    						//If animated sprite, animate it
	    						if (spriteComponent != null && spriteComponent.getSprite() != null 
	    								&& spriteComponent.getSprite() instanceof AnimatedSprite) {
	    							AnimatedSprite as = (AnimatedSprite)spriteComponent.getSprite();
	    							as.animate(SpriteAnimationEnum.MINE_BLINK_FAST.getFrameDurations(), 
	    									SpriteAnimationEnum.MINE_BLINK_FAST.getFrames());
	    						}
	    						
	    						break;
	    					}
	    				}
	    			}
	    			
	    			//TODO: check detection of the sentry
	    			
	    		} else {
	    			//Has been triggered
	    			AnimatedSprite explosion = explosiveComponent.getExplosion();
	    			
	    			if (!explosion.isVisible() && GameSingleton.getInstance().getTotalTime() > 
	    				explosiveComponent.getTriggeredTime() + explosiveComponent.getTimeBeforeExplosion()) {
	    				// EXPLOSIOOOOOON
	    				Log.i("RS", "Mine : BAAOOOOOMMMMMMMMMMMM");
	    				
	    				spriteComponent.getSprite().setVisible(false);
	    				explosion.setVisible(true);
	    				explosion.animate(SpriteAnimationEnum.EXPLOSION.getFrameDurations(), SpriteAnimationEnum.EXPLOSION.getFrames(), false);
	    				
	    				// /!\ DAMAGES are dealt only at this frame
	    				IShape blastArea = explosiveComponent.getBlastArea();
	    				//Deal damages to attackers
		    			List<Entity> attackers = this.entityManager.getAllEntitiesPosessingComponentOfClass(AttackComponent.class);
		    			for (Entity attacker : attackers) {
		    				SpriteComponent scAttacker = this.entityManager.getComponent(SpriteComponent.class, attacker);
		    				if (scAttacker != null) { // in case the entity is already dead
		    					IShape attackerHitbox = this.entityManager.getComponent(SpriteComponent.class, attacker).getHitbox();
		    					if (blastArea.collidesWith(attackerHitbox)) {
		    						// Attacker is caught in the blast
		    						Log.i("RS", "Attacker killed by explosion !");
		    						AttackComponent attackerAttackCompo = this.entityManager.getComponent(AttackComponent.class, attacker);
		    						boolean isAttackerDead = attackerAttackCompo.hit(explosiveComponent.getDamage());
		    						if (isAttackerDead) {
		    							//TODO : externalize enemy death
		    							entityManager.removeEntity(attacker);
		    						}
		    					}
		    				}
		    			}
	    				
	    			}
	    			
	    			if (explosion.isVisible()) {
	    				//Explosion animation is playing
	    				if (!explosion.isAnimationRunning()) {
	    					//Animation over, remove the mine entity
	    					entityManager.removeEntity(entity);
	    				}
	    			}
	    		}
	    		
	    	}
		}
	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
