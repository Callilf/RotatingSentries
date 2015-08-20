/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;
import java.util.Random;

import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.RectangularShape;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.DefenseComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.powerups.PowerUpComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.singleton.GameSingleton;
import com.callil.rotatingsentries.util.SpriteLoader;

/**
 * @author Callil
 * Handle the apparition of power ups, the collisions between power ups and projectiles, 
 * and generation of the skill/fire contained in the power up.
 */
public class PowerUpSystem extends System {
	
	private EntityFactory entityFactory;
	private RectangularShape gameArea;

	/** How frequently power ups will spawn in SECONDS. */
	public float powerUpSpawnPeriod = 30;
	
	/** The last spawn time. */
	public float lastPowerUpSpawn = 0;
	
	/**
	 * @param em
	 */
	public PowerUpSystem(EntityManager em, EntityFactory ef, RectangularShape gameArea) {
		super(em);
		this.entityFactory = ef;
		this.gameArea = gameArea;
	}

	/* (non-Javadoc)
	 * @see org.andengine.engine.handler.IUpdateHandler#onUpdate(float)
	 */
	@Override
	public void onUpdate(float pSecondsElapsed) {

		//Spawn of power ups
		GameSingleton singleton = GameSingleton.getInstance();
		if (singleton.getTotalTime() > lastPowerUpSpawn + powerUpSpawnPeriod) {
			
			Random r = new Random();
			//Only use width here since powerup and senty are squares
			float powerUpWidth = SpriteLoader.getInstance().getPowerUpTextureRegion().getWidth();
			float sentryWidth = SpriteLoader.getInstance().getSentryTextureRegion().getWidth(0);
			
			float randX = 0;
			float randY = 0;
			switch(r.nextInt(4)) {
			case 0:
				//Upper rectangle
				randX = r.nextInt((int) (gameArea.getWidth() - (powerUpWidth * 2))) + (powerUpWidth * 2);
				randY = r.nextInt((int) (gameArea.getHeight()/2 - sentryWidth - (powerUpWidth * 2))) + (powerUpWidth * 2);
				Log.d("RS", "Generate power up on upper rectangle at (" + randX + "," + randY + ")");

			break;
			case 1:
				//Right rectangle
				randX = r.nextInt((int) (gameArea.getWidth()/2 - sentryWidth - (powerUpWidth * 2))) + (gameArea.getWidth()/2 + powerUpWidth * 2);
				randY = r.nextInt((int) (gameArea.getHeight() - (powerUpWidth * 2))) + (powerUpWidth * 2);
				Log.d("RS", "Generate power up on right rectangle at (" + randX + "," + randY + ")");

				break;
			case 2:
				//Lower rectangle
				randX = r.nextInt((int) (gameArea.getWidth() - (powerUpWidth * 2))) + (powerUpWidth * 2);
				randY = r.nextInt((int) (gameArea.getHeight()/2 - sentryWidth - (powerUpWidth * 2))) + (gameArea.getHeight()/2 + powerUpWidth * 2);
				Log.d("RS", "Generate power up on lower rectangle at (" + randX + "," + randY + ")");
				
				break;
			default:
				//Left rectangle
				randX = r.nextInt((int) (gameArea.getWidth()/2 - sentryWidth - (powerUpWidth * 2))) + (powerUpWidth * 2);
				randY = r.nextInt((int) (gameArea.getHeight() - (powerUpWidth * 2))) + (powerUpWidth * 2);
				Log.d("RS", "Generate power up on left rectangle at (" + randX + "," + randY + ")");

			}

			this.entityFactory.generatePowerUp(randX, randY);
			lastPowerUpSpawn = singleton.getTotalTime();
		}
		
		// COLLISION WITH PROJECTILES
		// MANAGE COLLISIONS OF PROJECTILES
		List<Entity> projectiles = this.entityManager.getAllEntitiesPosessingComponentOfClass(DefenseComponent.class);
		for (Entity projectile : projectiles) {
			IShape projHitbox = this.entityManager.getComponent(SpriteComponent.class, projectile).getHitbox();

			// With enemies
			List<Entity> powerUps = this.entityManager.getAllEntitiesPosessingComponentOfClass(PowerUpComponent.class);
			for (Entity powerUp : powerUps) {
				SpriteComponent powerUpSprite = this.entityManager.getComponent(SpriteComponent.class, powerUp);
				if (powerUpSprite != null) { // in case the entity is already dead
					IShape powerUpHitbox = this.entityManager.getComponent(SpriteComponent.class, powerUp).getHitbox();
					if (projHitbox.collidesWith(powerUpHitbox)) {
						//TODO: right now, hitting a power up always destroys the projectile
						entityManager.removeEntity(powerUp);
						entityManager.removeEntity(projectile);
						break;
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
