/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

/**
 * @author Callil
 * Defines that the entity is a solid object that destroys or deflects other entities.
 */
public class SolidComponent extends Component {

	/**
	 * Constructor.
	 */
	public SolidComponent() {}

	/**
	 * @param name
	 */
	public SolidComponent(String name) {
		super(name);
	}

}
