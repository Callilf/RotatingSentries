package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;
import java.util.Random;

import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.GameActivity;
import com.callil.rotatingsentries.GameSingleton;
import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.ShootingComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.ShootingComponent.ProjectileType;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

public class GenerationSystem extends System {

	private EntityFactory entityFactory;
	
	public GenerationSystem(EntityManager em, EntityFactory ef) {
		super(em);
		this.entityFactory = ef;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		
	    // MANAGE THE SPAWNING OF ENEMIES
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(DiamondComponent.class.getName());
	    for (Entity entity : entities) {
	    	SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
	    	Sprite sprite = spriteComponent.getSprite();
	    	DiamondComponent diamondComponent = (DiamondComponent) this.entityManager.getComponent(DiamondComponent.class.getName(), entity);
	    	float nextGeneratingTime = diamondComponent.getFrequency() + diamondComponent.getLastGenerateTime();
	    	float currentDuration = GameSingleton.getInstance().getTotalTime();
	    	if (nextGeneratingTime < currentDuration) {
	    		diamondComponent.setLastGenerateTime(currentDuration);
	    		
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
					 generatedX = GameActivity.ROOM_X + rand.nextInt((int) (GameActivity.ROOM_WIDTH - 96.0 - 30.0)) + 30; 
					break;
				case 1:
					generatedX = GameActivity.ROOM_X + GameActivity.ROOM_WIDTH - 96;
					ropeAngle = 90;
					generatedY = GameActivity.ROOM_Y + rand.nextInt((int) (GameActivity.ROOM_HEIGHT - 96.0 - 30.0)) + 30; 
					break;
				case 2:
					generatedY = GameActivity.ROOM_Y + GameActivity.ROOM_HEIGHT - 96;
					ropeAngle = 180;
					generatedX = GameActivity.ROOM_X + rand.nextInt((int) (GameActivity.ROOM_WIDTH - 96.0 - 30.0)) + 30; 
					break;
				case 3:
					generatedX = GameActivity.ROOM_X;
					ropeAngle = 270;
					generatedY = GameActivity.ROOM_Y + rand.nextInt((int) (GameActivity.ROOM_HEIGHT - 96.0 - 30.0)) + 30; 
					break;
					default:
				}
				this.entityFactory.generateRobber(generatedX, generatedY, 2, sprite, ropeAngle);
				
	    	}
	    }
	    
	    // MANAGE GENERATION OF PROJECTILES
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(ShootingComponent.class.getName());
	    for (Entity entity : entities) {
	    	ShootingComponent shootingComponent = (ShootingComponent) this.entityManager.getComponent(ShootingComponent.class.getName(), entity);
	    	float nextGeneratingTime = shootingComponent.getFrequency() + shootingComponent.getLastGenerateTime();
	    	float currentDuration = GameSingleton.getInstance().getTotalTime();
	    	if (nextGeneratingTime < currentDuration) {
	    		shootingComponent.setLastGenerateTime(currentDuration);
	    		SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
	    		entityFactory.generateProjectile(ProjectileType.STANDARD, spriteComponent.getSprite());
	    	}
	    }

	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
