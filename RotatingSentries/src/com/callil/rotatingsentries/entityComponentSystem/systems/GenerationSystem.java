package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import com.callil.rotatingsentries.GameSingleton;
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
	    
	    // MANAGE GENERATION OF PROJECTILES
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(ShootingComponent.class.getName());
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
