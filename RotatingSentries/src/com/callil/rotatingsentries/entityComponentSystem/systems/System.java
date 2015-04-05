/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import org.andengine.engine.handler.IUpdateHandler;

import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;


/**
 * @author Callil
 *
 */
public abstract class System implements IUpdateHandler {
	
	/** The entity manager. */
	protected EntityManager entityManager;

	/**
	 * Constructor.
	 */
	public System(EntityManager em) {
		this.entityManager = em;
	}
		
	
	//Methods
	
	public abstract void onPopulateScene();
	
	//Getters & Setters

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
