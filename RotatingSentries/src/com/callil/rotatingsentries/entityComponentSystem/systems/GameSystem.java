package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import android.util.SparseArray;

import com.callil.rotatingsentries.GameActivity;
import com.callil.rotatingsentries.entityComponentSystem.components.Component;
import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

public class GameSystem extends System {

	/** The game activity */
	private GameActivity activity;
	
	public GameSystem(EntityManager em, GameActivity activity) {
		super(em);
		this.activity = activity;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		
		// Kill the diamond if life = 0
		List<Entity> diamondEntities = this.entityManager.getAllEntitiesPosessingComponentOfClass(DiamondComponent.class);
		if (diamondEntities != null && diamondEntities.size() > 0) {
			for (Entity diamond : diamondEntities) {
				DiamondComponent diamondComponent = this.entityManager.getComponent(DiamondComponent.class, diamond);
				if (diamondComponent.getLife() <= 0) {
					entityManager.removeEntity(diamond);
				}
			}
		}
		
		// Game over is there are no more diamond in the game
		diamondEntities = this.entityManager.getAllEntitiesPosessingComponentOfClass(DiamondComponent.class);
		if (diamondEntities == null || diamondEntities.size() == 0) {
			activity.endGame();
		}
	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
