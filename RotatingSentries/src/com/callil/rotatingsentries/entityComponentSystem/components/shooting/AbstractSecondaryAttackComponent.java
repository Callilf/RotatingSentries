/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * @author Callil
 * Defines that the entity can shoot with a primary fire.
 */
public abstract class AbstractSecondaryAttackComponent extends Component {

	/** The max number of ammunitions. */
	private int maxAmmo;
	/** The current number of ammunitions. */
	private int currentAmmo;
	
	/**
	 * @param maxAmmo the max number of ammunitions
	 */
	public AbstractSecondaryAttackComponent(int maxAmmo) {
		this.maxAmmo = maxAmmo;
		this.currentAmmo = maxAmmo;
	}

	/**
	 * @param name
	 */
	public AbstractSecondaryAttackComponent(String name) {
		super(name);
	}
	
	
	
	

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}

	public int getCurrentAmmo() {
		return currentAmmo;
	}

	public void setCurrentAmmo(int currentAmmo) {
		this.currentAmmo = currentAmmo;
	}

}
