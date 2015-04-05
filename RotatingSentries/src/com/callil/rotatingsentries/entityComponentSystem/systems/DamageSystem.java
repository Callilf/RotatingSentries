/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;

import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

import android.util.Log;

/**
 * @author Callil
 * Handle the collisions and loss of HPs.
 */
public class DamageSystem extends System {

	/** The scene. */
	private Scene scene;
	
	/**
	 * @param em
	 */
	public DamageSystem(EntityManager em, Scene scene) {
		super(em);
		this.scene = scene;
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		
		// MANAGER HITABLE ENTITY COLLISIONS WITH ITEMS
//		List<Entity> entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(HitableComponent.class.getName());
//		for (Entity entity : entities) {
//			
//			HitableComponent hitableComponent = (HitableComponent) this.entityManager.getComponent(HitableComponent.class.getName(), entity);
//			HealthComponent healthComponent = (HealthComponent) this.entityManager.getComponent(HealthComponent.class.getName(), entity);
//			SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
//
//			if (hitableComponent != null) {
//				//Check whether the enemy is in recovery
//				if (hitableComponent.getLastHitTime() != null && hitableComponent.isInRecovery()) {
//					//Enemy still in recovery
//					continue;
//				}
//			
//			
//				//These entities can be hit. Check each items to see if they collide.
//				List<Entity> items = 
//						this.entityManager.getAllEntitiesPosessingComponentOfClass(LevitatingItemComponent.class.getName());
//				for (Entity item : items) {
//					
//					LevitatingItemComponent itemItemComponent = (LevitatingItemComponent) this.entityManager.getComponent(LevitatingItemComponent.class.getName(), item);
//					if (itemItemComponent != null && itemItemComponent.isActive()) {
//						SpriteComponent itemSprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), item);
//						if (itemSprite.getSprite() != null && itemSprite.getSprite().collidesWith(spriteComponent.getSprite())) {
//							//Collision with an item
//							Log.d("RS", "Enemy collides with item ! Takes " + itemItemComponent.getDamages() + " damages.");
//							healthComponent.takeDamage(itemItemComponent.getDamages());
//							
//							if (healthComponent.isDead()) {
//								// Dead !!
//								Log.d("RS", "Enemy dead !");
//								spriteComponent.getSprite().detachChildren();
//								this.scene.detachChild(spriteComponent.getSprite());
//								this.entityManager.removeEntity(entity);
//							} else {
//								//Still alive, activate recovery mode
//								hitableComponent.activateRecovery();
//							}
//						}
//					}
//					
//				}
//			}
//			
//		}
		
		
		
		// MANAGER COLLISIONS BETWEEN THE PLAYER AND THE ENEMIES
//		entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(PlayerComponent.class.getName());
//		for (Entity player : entities) {
//			
//			PlayerComponent playerComponent = (PlayerComponent) this.entityManager.getComponent(PlayerComponent.class.getName(), player);
//			SpriteComponent playerSprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), player);
//			
//			//Check whether the player is in recovery
//			if (playerComponent.getLastHitTime() != null && playerComponent.isInRecovery()) {
//				//Player still in recovery
//				continue;
//			}
//			
//			// Iterate over enemies
//			List<Entity> enemies = 
//					this.entityManager.getAllEntitiesPosessingComponentOfClass(EnemyComponent.class.getName());
//			for (Entity enemy : enemies) {
//				
//				SpriteComponent enemySprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), enemy);
//				EnemyComponent enemyEnemyComponent = (EnemyComponent) this.entityManager.getComponent(EnemyComponent.class.getName(), enemy);
//				if (enemySprite.getSprite().collidesWith(playerSprite.getSprite())) {
//					// Enemy collides with player !
//					Log.d("RS", "Enemy collides with player. Player looses " + enemyEnemyComponent.getDamages() + " hp.");
//					HealthComponent playerHealth = (HealthComponent) this.entityManager.getComponent(HealthComponent.class.getName(), player);
//					playerHealth.takeDamage(enemyEnemyComponent.getDamages());
//					
//					if (playerHealth.isDead()) {
//						// Dead !!
//						Log.d("RS", "Player dead !");
//						//TODO handle end of game
//						
//					} else {
//						//Still alive, activate recovery mode
//						playerComponent.activateRecovery();
//					}
//				}
//				
//			}
//			
//		}
		
	}

	
	
	
	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
