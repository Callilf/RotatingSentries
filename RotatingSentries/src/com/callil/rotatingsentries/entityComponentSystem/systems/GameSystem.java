package com.callil.rotatingsentries.entityComponentSystem.systems;

import android.util.SparseArray;

import com.callil.rotatingsentries.GameActivity;
import com.callil.rotatingsentries.entityComponentSystem.components.Component;
import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
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
		SparseArray<Component> diamonds = entityManager.getComponentsByClass().get(DiamondComponent.class);
		if (diamonds == null || diamonds.size() == 0) {
			activity.endGame();
		}
	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
