package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.AbstractAttDefComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.AttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.DetectableComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.ExplosiveComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractSecondaryAttackComponent.ExplosiveType;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.PrimaryShootingComponent;
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
	    			
	    			//Check detection of attackers, sentries....
	    			List<Entity> detectables = this.entityManager.getAllEntitiesPosessingComponentOfClass(DetectableComponent.class);
	    			for (Entity detectable : detectables) {
	    				SpriteComponent detectableSpriteComponent = this.entityManager.getComponent(SpriteComponent.class, detectable);
	    				if (detectableSpriteComponent != null) { // in case the entity is already dead
	    					IShape detectableHitbox = this.entityManager.getComponent(SpriteComponent.class, detectable).getHitbox();
	    					if (detectionArea.collidesWith(detectableHitbox)) {
	    						// DETECTED !!!
	    						Log.i("RS", "INTRUDER DETECTED - Activate proximity mine !");
	    						triggerMine(explosiveComponent, spriteComponent);
	    						break;
	    					}
	    				}
	    			}
	    			
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
	    				//Deal damages to attackers or projectiles
		    			List<Entity> hitables = this.entityManager.getAllEntitiesPosessingComponentOfClass(AbstractAttDefComponent.class);
		    			for (Entity hitable : hitables) {
		    				if (hitable.getEid() == entity.getEid()) {
		    					//Do not destroy the mine by it's own explosion...
		    					continue;
		    				}
		    				
		    				SpriteComponent hitableSpriteCompo = this.entityManager.getComponent(SpriteComponent.class, hitable);
		    				if (hitableSpriteCompo != null) { // in case the entity is already dead
		    					IShape hitableHitbox = this.entityManager.getComponent(SpriteComponent.class, hitable).getHitbox();
		    					if (blastArea.collidesWith(hitableHitbox)) {
		    						// Attacker or projectile is caught in the blast
		    						Log.i("RS", "Something was caught in the blast of the explosion !");
		    						AbstractAttDefComponent attDefCompo = this.entityManager.getComponent(AbstractAttDefComponent.class, hitable);
		    						boolean isEntityDead = attDefCompo.hit(explosiveComponent.getDamage());
		    						if (isEntityDead) {
		    							//TODO : externalize enemy death
		    							entityManager.removeEntity(hitable);
		    						}
		    					}
		    				}
		    			}
		    			// Deal damage to the diamond
		    			List<Entity> diamondEntities = this.entityManager.getAllEntitiesPosessingComponentOfClass(DiamondComponent.class);
		    			if (diamondEntities != null && diamondEntities.size() > 0) {
		    				for (Entity diamond : diamondEntities) {
			    				SpriteComponent diamondSpriteCompo = this.entityManager.getComponent(SpriteComponent.class, diamond);
			    				if (blastArea.collidesWith(diamondSpriteCompo.getHitbox())) {
			    					Log.i("RS", "DIAMOND DAMAGED BY THE EXPLOSION !");
				    				DiamondComponent diamondComponent = this.entityManager.getComponent(DiamondComponent.class, diamond);
				    				diamondComponent.takeDamage(explosiveComponent.getDamage());
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

	/**
	 * @param explosiveComponent
	 * @param spriteComponent
	 */
	private void triggerMine(ExplosiveComponent explosiveComponent,
			SpriteComponent spriteComponent) {
		explosiveComponent.setTriggered(true);
		explosiveComponent.setTriggeredTime(GameSingleton.getInstance().getTotalTime());
		
		//If animated sprite, animate it
		if (spriteComponent != null && spriteComponent.getSprite() != null 
				&& spriteComponent.getSprite() instanceof AnimatedSprite) {
			AnimatedSprite as = (AnimatedSprite)spriteComponent.getSprite();
			as.animate(SpriteAnimationEnum.MINE_BLINK_FAST.getFrameDurations(), 
					SpriteAnimationEnum.MINE_BLINK_FAST.getFrames());
		}
	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
