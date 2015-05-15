package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;
import java.util.Random;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.ShootingComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.ShootingComponent.ProjectileType;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.singleton.GameSingleton;

public class GenerationSystem extends System {

	private EntityFactory entityFactory;
	
	private RectangularShape gameArea;
	
	public GenerationSystem(EntityManager em, EntityFactory ef, RectangularShape gameArea) {
		super(em);
		this.entityFactory = ef;
		this.gameArea = gameArea;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		
	    // MANAGE THE SPAWNING OF ENEMIES
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(DiamondComponent.class);
	    for (Entity entity : entities) {
	    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	Sprite sprite = spriteComponent.getSprite();
	    	DiamondComponent diamondComponent = this.entityManager.getComponent(DiamondComponent.class, entity);
	    	float nextGeneratingTime = diamondComponent.getFrequency() + diamondComponent.getLastGenerateTime();
	    	float currentDuration = GameSingleton.getInstance().getTotalTime();
	    	if (nextGeneratingTime < currentDuration) {
	    		diamondComponent.setLastGenerateTime(currentDuration);
	    		diamondComponent.setFrequency(Math.max(diamondComponent.getFrequency() - 0.1f, 1.0f));
	    		
	    		// Generate a new enemy
				Random rand = new Random();
				int wallInt = rand.nextInt(4);
				float generatedX = 0;
				float generatedY = 0;
				float ropeAngle = 0;
				
				switch (wallInt) {
				case 0:
					 generatedY = 0;
					 ropeAngle = 0;
					 generatedX = rand.nextInt((int) (gameArea.getWidth() - 96.0 - 30.0)) + 30; 
					break;
				case 1:
					generatedX = gameArea.getWidth() - 96;
					ropeAngle = 90;
					generatedY = rand.nextInt((int) (gameArea.getHeight() - 96.0 - 30.0)) + 30; 
					break;
				case 2:
					generatedY = gameArea.getHeight() - 96;
					ropeAngle = 180;
					generatedX = rand.nextInt((int) (gameArea.getWidth() - 96.0 - 30.0)) + 30; 
					break;
				case 3:
					generatedX = 0;
					ropeAngle = 270;
					generatedY = rand.nextInt((int) (gameArea.getHeight() - 96.0 - 30.0)) + 30; 
					break;
					default:
				}
				this.entityFactory.generateRobber(generatedX, generatedY, 2, sprite, ropeAngle);
				
	    	}
	    }
	    
	    // MANAGE GENERATION OF PROJECTILES
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(ShootingComponent.class);
	    for (Entity entity : entities) {
	    	ShootingComponent shootingComponent = this.entityManager.getComponent(ShootingComponent.class, entity);
	    	float nextGeneratingTime = shootingComponent.getFrequency() + shootingComponent.getLastGenerateTime();
	    	float currentDuration = GameSingleton.getInstance().getTotalTime();
	    	if (nextGeneratingTime < currentDuration) {
	    		shootingComponent.setLastGenerateTime(currentDuration);
	    		SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    		Sprite sprite = spriteComponent.getSprite();
	    		entityFactory.generateProjectile(ProjectileType.STANDARD, spriteComponent.getSprite());
	    		
	    		//If the shooter sprite is animated and an animation is registered in shootingComponent, PLAY IT
	    		if (spriteComponent.getSprite() instanceof AnimatedSprite && shootingComponent.getShootAnimFrames() != null) {
	    			((AnimatedSprite)sprite).animate(shootingComponent.getShootAnimDurations(), shootingComponent.getShootAnimFrames(), false);
	    		}
	    	}
	    }

	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
