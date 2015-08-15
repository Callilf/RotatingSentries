package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.shooting.SecondaryMineComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractSecondaryAttackComponent.ExplosiveType;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.SecondaryMineComponent.SecondaryMineState;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
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
		
		
		//Handle mines

	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
