package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;

import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

/**
 * @author Callil
 *
 */
public class RenderSystem extends System {

	/** The scene on which we want to render. */
	private Scene scene;
	
	/**
	 * @param em
	 */
	public RenderSystem(EntityManager em, Scene scene) {
		super(em);
		this.setScene(scene);
	}

	@Override
	public void onPopulateScene() {
		
		//ATTACH SPRITES TO SCENE AND HEALTH TO SPRITES
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class);
	    for (Entity entity : entities) {
	    	
	    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	if (spriteComponent != null) {
	    		this.scene.attachChild(spriteComponent.getSprite());
	    		spriteComponent.setAttached(true);
	    		
//	    		HealthComponent healthComponent = this.entityManager.getComponent(HealthComponent.class, entity);
//	    		if (healthComponent != null && healthComponent.isDisplayHP()) {
//	    			spriteComponent.getSprite().attachChild(healthComponent.getText());
//	    			healthComponent.getText().setPosition(0, spriteComponent.getSprite().getHeight());
//	    		}
	    	}

	    }
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		
		// ATTACH SPRITES
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class);
		for (Entity entity : entities) {
			SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
			if (spriteComponent != null && !spriteComponent.isAttached()) {
				this.scene.attachChild(spriteComponent.getSprite());
				spriteComponent.setAttached(true);
			}
			
			//Attach diamond's life
			DiamondComponent diamondComponent = this.entityManager.getComponent(DiamondComponent.class, entity);
    		if (diamondComponent != null && diamondComponent.getLifeText() != null && !diamondComponent.getLifeText().hasParent()) {
    			this.scene.attachChild(diamondComponent.getLifeText());
    			diamondComponent.getLifeText().setPosition(20, 20);
    		}
		}
		
		
		// MANAGE SELF ROTATIONS
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SelfRotationComponent.class);
	    for (Entity entity : entities) {
	    	
	    	SelfRotationComponent rotationComponent = this.entityManager.getComponent(SelfRotationComponent.class, entity);
	    	if (rotationComponent.isActive()) {
	    		if (rotationComponent.isAffectedByButton()) {
	    			// define the direction of rotate if a button is active
	    			if (rotationComponent.isLastLeftPressed()) {
	    				rotationComponent.setClockwise(false);
	    			}
	    			else if (rotationComponent.isLastRightPressed()) {
	    				rotationComponent.setClockwise(true);
	    			}
	    			else {
	    				continue;
	    			}
	    		}
		    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
		    	float speed = rotationComponent.getRotationSpeed() * (rotationComponent.isClockwise()? 1 : -1);
		    	if (rotationComponent != null && spriteComponent != null) {
		    		rotationComponent.setCurrentRotation( (rotationComponent.getCurrentRotation() + speed) % 360);
		    		spriteComponent.getSprite().setRotation(rotationComponent.getCurrentRotation());
		    	}
	    	}
	    	
	    }
	    
	    
	    // MANAGER ENEMY RECOVERY BLINK
//	    entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(AttackComponent.class);
//		for (Entity entity : entities) {
//			
//			AttackComponent hitableComponent = this.entityManager.getComponent(AttackComponent.class, entity);
//			SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
//			if (hitableComponent != null && hitableComponent.getLastHitTime() != null) {
//				//Check whether the enemy is in recovery
//				if (hitableComponent.isInRecovery()) {
//					if (!spriteComponent.isBlinking()) {
//						//Enemy enters recovery
//						spriteComponent.getSprite().registerEntityModifier(spriteComponent.getBlinkModifier());
//						spriteComponent.setBlinking(true);
//					}
//				} else {
//					if (spriteComponent.isBlinking()) {
//						//Deactivate blink
//						spriteComponent.getSprite().unregisterEntityModifier(spriteComponent.getBlinkModifier());
//						spriteComponent.getSprite().setAlpha(1.0f);
//						spriteComponent.setBlinking(false);
//					}
//				}
//			}
//		}
		
		// MANAGER PLAYER RECOVERY BLINK
//	    entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(PlayerComponent.class);
//		for (Entity player : entities) {
//			PlayerComponent playerComponent = this.entityManager.getComponent(PlayerComponent.class, player);
//			SpriteComponent playerSprite = this.entityManager.getComponent(SpriteComponent.class, player);
//			if (playerComponent != null && playerComponent.getLastHitTime() != null) {
//				//Check whether the enemy is in recovery
//				if (playerComponent.isInRecovery()) {
//					if (!playerSprite.isBlinking()) {
//						//Enemy enters recovery
//						playerSprite.getSprite().registerEntityModifier(playerSprite.getBlinkModifier());
//						playerSprite.setBlinking(true);
//					}
//				} else {
//					if (playerSprite.isBlinking()) {
//						//Deactivate blink
//						playerSprite.getSprite().unregisterEntityModifier(playerSprite.getBlinkModifier());
//						playerSprite.getSprite().setAlpha(1.0f);
//						playerSprite.setBlinking(false);
//					}
//				}
//			}
//		}
		
	}

	
	@Override
	public void reset() {}

	
	//Getters & Setters
	
	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * @param scene the scene to set
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
