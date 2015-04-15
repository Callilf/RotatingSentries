/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

/**
 * @author Callil
 * Component abstract class. A component is a piece of data intended to be
 * associated to entities.
 */
public abstract class Component {

	/** Name (debugging purpose). */
	private String name;
	
	/** Whether this component is active or not. 
	 * If not, the entity posessing this component will be considered like it doesn't have this component. */
	private boolean active = true;
	
	public Component() {}
	
	public Component(String name) {
		this.name = name;
	}
	
	/** Call when the associated entity is destroy. To override if a destroy action is needed */
	public void destroy() {}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
