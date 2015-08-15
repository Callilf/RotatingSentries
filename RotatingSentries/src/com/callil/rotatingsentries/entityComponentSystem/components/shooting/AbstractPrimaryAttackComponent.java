/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * @author Callil
 * Defines that the entity can shoot with a primary fire.
 */
public abstract class AbstractPrimaryAttackComponent extends Component {

	public enum ProjectileType {
		STANDARD,
		PIERCING;
	}
	
	/** The fire icon. */
	private Sprite icon;
	
	/**
	 * 
	 */
	public AbstractPrimaryAttackComponent(Sprite icon) {
		this.icon = icon;
	}

	/**
	 * @param name
	 */
	public AbstractPrimaryAttackComponent(String name, Sprite icon) {
		super(name);
		this.icon = icon;
	}

	
	
	
	public Sprite getIcon() {
		return icon;
	}

	public void setIcon(Sprite icon) {
		this.icon = icon;
	}

}
