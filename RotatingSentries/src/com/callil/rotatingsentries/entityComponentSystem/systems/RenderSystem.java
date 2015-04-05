/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;

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
				this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class.getName());
	    for (Entity entity : entities) {
	    	
	    	SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
	    	if (spriteComponent != null) {
	    		this.scene.attachChild(spriteComponent.getSprite());
	    		spriteComponent.setAttached(true);
	    		
//	    		HealthComponent healthComponent = (HealthComponent) this.entityManager.getComponent(HealthComponent.class.getName(), entity);
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
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class.getName());
		for (Entity entity : entities) {
			SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
			if (spriteComponent != null && !spriteComponent.isAttached()) {
				this.scene.attachChild(spriteComponent.getSprite());
				spriteComponent.setAttached(true);
			}
		}
		
		
		// MANAGE SELF ROTATIONS
//		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SelfRotationComponent.class.getName());
//	    for (Entity entity : entities) {
//	    	
//	    	SelfRotationComponent rotationComponent = (SelfRotationComponent) this.entityManager.getComponent(SelfRotationComponent.class.getName(), entity);
//	    	SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
//	    	if (rotationComponent != null && spriteComponent != null) {
//	    		rotationComponent.setCurrentRotation( (rotationComponent.getCurrentRotation() + rotationComponent.getRotationSpeed()) % 360);
//	    		spriteComponent.getSprite().setRotation(rotationComponent.getCurrentRotation());
//	    	}
//	    	
//	    }
	    
	    // MANAGER ENEMY RECOVERY BLINK
//	    entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(HitableComponent.class.getName());
//		for (Entity entity : entities) {
//			
//			HitableComponent hitableComponent = (HitableComponent) this.entityManager.getComponent(HitableComponent.class.getName(), entity);
//			SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
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
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(PlayerComponent.class.getName());
//		for (Entity player : entities) {
//			PlayerComponent playerComponent = (PlayerComponent) this.entityManager.getComponent(PlayerComponent.class.getName(), player);
//			SpriteComponent playerSprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), player);
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
