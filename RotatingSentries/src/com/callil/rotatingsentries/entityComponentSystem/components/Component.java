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
	
	public Component() {}
	
	public Component(String name) {
		this.name = name;
	}
	
	/** Call when the associated entity is destroy. To override if a destroy action is needed */
	public void destroy() {}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
