/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

/**
 * @author Callil
 * Handle collisions between power ups and projectiles, and generation of the skill/fire contained in the power up.
 */
public class PowerUpSystem extends System {

	/**
	 * @param em
	 */
	public PowerUpSystem(EntityManager em) {
		super(em);
	}

	/* (non-Javadoc)
	 * @see org.andengine.engine.handler.IUpdateHandler#onUpdate(float)
	 */
	@Override
	public void onUpdate(float pSecondsElapsed) {

		//COLLISION WITH PROJECTILES
		//TODO : collision between power up and projectile
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.andengine.engine.handler.IUpdateHandler#reset()
	 */
	@Override
	public void reset() {}

	/* (non-Javadoc)
	 * @see com.callil.rotatingsentries.entityComponentSystem.systems.System#onPopulateScene()
	 */
	@Override
	public void onPopulateScene() {}

}
