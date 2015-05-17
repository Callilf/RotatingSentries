/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * @author Callil
 * Defines that the entity can shoot with a primary fire.
 */
public abstract class AbstractPrimaryAttackComponent extends Component {

	/**
	 * 
	 */
	public AbstractPrimaryAttackComponent() {
	}

	/**
	 * @param name
	 */
	public AbstractPrimaryAttackComponent(String name) {
		super(name);
	}

}
