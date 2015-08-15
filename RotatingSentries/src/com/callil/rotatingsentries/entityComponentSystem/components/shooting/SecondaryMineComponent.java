/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * @author Callil
 *
 */
public class SecondaryMineComponent extends AbstractSecondaryAttackComponent {
	
	/**
	 * The possible states of the mine component.
	 * @author Callil
	 * INACTIVE : when the user has the mine as a secondary weapon but isn't using it
	 * PLACING : when the user tap the secondary shoot button and the target appears to allow choosing the placement
	 * PLACED : when the mine is placed and waiting for an enemy to enter it's field of explosion
	 */
	public enum SecondaryMineState {
		INIT,
		INACTIVE,
		INIT_PLACING,
		PLACING
	}
	
	/** The current state. */
	private SecondaryMineState state;
	/** The sprite of the target use to choose the placement of the mine. */
	private Sprite targetSprite;
	
	/**
	 * @param icon the icon of the mines
	 * @param maxAmmo the max ammo
	 * @param ammoText the Text that displays the number of ammo
	 * @param targetSprite the sprite of the target to choose the placement of the mine
	 */
	public SecondaryMineComponent(Sprite icon, int maxAmmo, Text ammoText, Sprite targetSprite) {
		super(icon, maxAmmo, ammoText);
		this.targetSprite = targetSprite;
		this.state = SecondaryMineState.INIT;
	}
	
	@Override
	public void destroy() {
		targetSprite.detachSelf();
	}
	
	
	//Get Set
	
	public Sprite getTarget() {
		return targetSprite;
	}

	public void setTarget(Sprite target) {
		this.targetSprite = target;
	}




	public SecondaryMineState getState() {
		return state;
	}




	public void setState(SecondaryMineState state) {
		this.state = state;
	}


}
