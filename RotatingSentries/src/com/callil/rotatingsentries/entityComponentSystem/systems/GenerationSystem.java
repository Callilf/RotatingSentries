package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;
import java.util.Random;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractSecondaryAttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.PrimaryShootingComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.SecondaryShootingComponent;
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
		//TODO : probably move this, and handle the enemy generation in a better way than that :P
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(DiamondComponent.class);
	    for (Entity entity : entities) {
	    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	Sprite sprite = spriteComponent.getSprite();
	    	DiamondComponent diamondComponent = this.entityManager.getComponent(DiamondComponent.class, entity);
	    	float nextGeneratingTime = diamondComponent.getFrequency() + diamondComponent.getLastGenerateTime();
	    	float currentDuration = GameSingleton.getInstance().getTotalTime();
	    	if (nextGeneratingTime < currentDuration) {
	    		diamondComponent.setLastGenerateTime(currentDuration);
	    		
	    		//Lower the frequency at each generation
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
				
				//Generate robber or red robber
				int robberType = rand.nextInt(5);
				if (robberType < 3) {
					this.entityFactory.generateRobber(generatedX, generatedY, 2, sprite, ropeAngle);
				} else if (robberType == 3) {
					this.entityFactory.generateRobberRed(generatedX, generatedY, 2, sprite, ropeAngle);
					//Since robberRed are stronger, re-increase the frequency when they appear
					diamondComponent.setFrequency(diamondComponent.getFrequency() + 0.2f);
				} else {
					if (ropeAngle == 0) generatedY += 100;
					if (ropeAngle == 90) generatedX -= 100;
					if (ropeAngle == 180) generatedY -= 100;
					if (ropeAngle == 270) generatedX += 100;
					this.entityFactory.generateNinja(generatedX, generatedY, sprite, ropeAngle);

					//Since robberRed are stronger, re-increase the frequency when they appear
					diamondComponent.setFrequency(diamondComponent.getFrequency() + 0.1f);
				}
				
	    	}
	    }
	    
	    
	    //Handle the alternate fire
	    GameSingleton singleton = GameSingleton.getInstance();
	    if (singleton.isTouchingArea && singleton.hasReleasedArea) {
	    	entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(AbstractSecondaryAttackComponent.class, true);
	    	for (Entity entity : entities) {
	    		AbstractSecondaryAttackComponent secondaryAttackComponent = this.entityManager.getComponent(AbstractSecondaryAttackComponent.class, entity);
	    		secondaryAttackComponent.setActive(true);
	    	}
	    	singleton.hasReleasedArea = false;
	    }
	    
	    
	    
	    // MANAGE GENERATION OF PROJECTILES
	    // 1 - Primary components
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(PrimaryShootingComponent.class);
	    for (Entity entity : entities) {
	    	PrimaryShootingComponent shootingComponent = this.entityManager.getComponent(PrimaryShootingComponent.class, entity);
	    	float nextGeneratingTime = shootingComponent.getPeriodOfSpawn() + shootingComponent.getLastGenerateTime();
	    	float currentDuration = GameSingleton.getInstance().getTotalTime();
	    	if (nextGeneratingTime < currentDuration) {
	    		shootingComponent.setLastGenerateTime(currentDuration);
	    		SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    		Sprite sprite = spriteComponent.getSprite();
	    		entityFactory.generateProjectile(shootingComponent.getProjectileType(), spriteComponent.getSprite());
	    		
	    		//If the shooter sprite is animated and an animation is registered in shootingComponent, PLAY IT
	    		if (spriteComponent.getSprite() instanceof AnimatedSprite && shootingComponent.getShootAnimFrames() != null) {
	    			((AnimatedSprite)sprite).animate(shootingComponent.getShootAnimDurations(), shootingComponent.getShootAnimFrames(), false);
	    		}
	    	}
	    }
	    
	    // 2 - Secondary Components
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SecondaryShootingComponent.class);
	    for (Entity entity : entities) {
	    	SecondaryShootingComponent secondaryShootingComponent = this.entityManager.getComponent(SecondaryShootingComponent.class, entity);
	    	// Check the remaining number of ammo
	    	if (secondaryShootingComponent.getCurrentAmmo() > 0) {
		    	float nextGeneratingTime = secondaryShootingComponent.getFrequency() + secondaryShootingComponent.getLastGenerateTime();
		    	float currentDuration = GameSingleton.getInstance().getTotalTime();
		    	if (nextGeneratingTime < currentDuration) {
		    		secondaryShootingComponent.setLastGenerateTime(currentDuration);
		    		secondaryShootingComponent.shoot();
		    		SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
		    		Sprite sprite = spriteComponent.getSprite();
		    		entityFactory.generateProjectile(secondaryShootingComponent.getProjectileType(), spriteComponent.getSprite());
		    		
		    		//If the shooter sprite is animated and an animation is registered in shootingComponent, PLAY IT
		    		if (spriteComponent.getSprite() instanceof AnimatedSprite && secondaryShootingComponent.getShootAnimFrames() != null) {
		    			((AnimatedSprite)sprite).animate(secondaryShootingComponent.getShootAnimDurations(), secondaryShootingComponent.getShootAnimFrames(), false);
		    		}
		    		//After 1 shot, the secondary weapon becomes inactive again
		    		secondaryShootingComponent.setActive(false);
		    	}
	    	}
	    }

	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
