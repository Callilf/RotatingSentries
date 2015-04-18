/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IShape;

import com.callil.rotatingsentries.entityComponentSystem.components.AttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.DefenseComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

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
		
		List<Entity> hittables = this.entityManager.getAllEntitiesPosessingComponentOfClass(AttackComponent.class);
		List<Entity> hitters = this.entityManager.getAllEntitiesPosessingComponentOfClass(DefenseComponent.class);
		for (Entity hitter : hitters) {
			IShape hHitter = this.entityManager.getComponent(SpriteComponent.class, hitter).getHitbox();
			DefenseComponent cHitter = this.entityManager.getComponent(DefenseComponent.class, hitter);
			for (Entity hittable : hittables) {
				SpriteComponent scHittable = this.entityManager.getComponent(SpriteComponent.class, hittable);
				if (scHittable != null) { // in case the entity is already dead
					IShape hHittable = this.entityManager.getComponent(SpriteComponent.class, hittable).getHitbox();
					if (hHitter.collidesWith(hHittable)) {
						AttackComponent cHittable = this.entityManager.getComponent(AttackComponent.class, hittable);
						// if dead should continue
						boolean hitterDead = cHitter.hit(cHittable.getDamage());
						boolean hittableDead = cHittable.hit(cHitter.getDamage());
						if (hittableDead) {
							entityManager.removeEntity(hittable);
						}
						if (hitterDead) {
							entityManager.removeEntity(hitter);
							break; // if dead, cannot kill another component
						}
					}
				}
			}
		}
		
		// MANAGER HITABLE ENTITY COLLISIONS WITH ITEMS
//		List<Entity> entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(AttackComponent.class);
//		for (Entity entity : entities) {
//			
//			AttackComponent hitableComponent = (AttackComponent) this.entityManager.getComponent(AttackComponent.class, entity);
//			HealthComponent healthComponent = (HealthComponent) this.entityManager.getComponent(HealthComponent.class, entity);
//			SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class, entity);
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
//						this.entityManager.getAllEntitiesPosessingComponentOfClass(LevitatingItemComponent.class);
//				for (Entity item : items) {
//					
//					LevitatingItemComponent itemItemComponent = (LevitatingItemComponent) this.entityManager.getComponent(LevitatingItemComponent.class, item);
//					if (itemItemComponent != null && itemItemComponent.isActive()) {
//						SpriteComponent itemSprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class, item);
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
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(PlayerComponent.class);
//		for (Entity player : entities) {
//			
//			PlayerComponent playerComponent = (PlayerComponent) this.entityManager.getComponent(PlayerComponent.class, player);
//			SpriteComponent playerSprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class, player);
//			
//			//Check whether the player is in recovery
//			if (playerComponent.getLastHitTime() != null && playerComponent.isInRecovery()) {
//				//Player still in recovery
//				continue;
//			}
//			
//			// Iterate over enemies
//			List<Entity> enemies = 
//					this.entityManager.getAllEntitiesPosessingComponentOfClass(EnemyComponent.class);
//			for (Entity enemy : enemies) {
//				
//				SpriteComponent enemySprite = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class, enemy);
//				EnemyComponent enemyEnemyComponent = (EnemyComponent) this.entityManager.getComponent(EnemyComponent.class, enemy);
//				if (enemySprite.getSprite().collidesWith(playerSprite.getSprite())) {
//					// Enemy collides with player !
//					Log.d("RS", "Enemy collides with player. Player looses " + enemyEnemyComponent.getDamages() + " hp.");
//					HealthComponent playerHealth = (HealthComponent) this.entityManager.getComponent(HealthComponent.class, player);
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
