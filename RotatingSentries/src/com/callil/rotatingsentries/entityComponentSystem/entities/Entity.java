/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.entities;

/**
 * @author Callil
 * Represent one entity in the game. Entities are only an id, 
 * the components they have are stored in the entityManager.
 */
public class Entity {
	
	/** The entity id. (unique) */
	private int eid;

	/**
	 * 
	 */
	public Entity(int eid) {
		this.eid = eid;
	}

	
	
	// Getters & Setters

	/**
	 * @return the eid
	 */
	public int getEid() {
		return eid;
	}

	/**
	 * @param eid the eid to set
	 */
	public void setEid(int eid) {
		this.eid = eid;
	}

}
