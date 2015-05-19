/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import org.andengine.entity.text.Text;

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
	
	/** The text to display the number of ammunitions. */
	private Text ammoText;
	
	/**
	 * @param maxAmmo the max number of ammunitions
	 */
	public AbstractSecondaryAttackComponent(int maxAmmo, Text ammoText) {
		this.maxAmmo = maxAmmo;
		this.currentAmmo = maxAmmo;
		this.ammoText = ammoText;
		updateAmmoText();
	}

	/**
	 * @param name
	 */
	public AbstractSecondaryAttackComponent(String name) {
		super(name);
	}
	
	
	/**
	 * Shoot one ammo.
	 */
	public void shoot() {
		this.currentAmmo -= 1;
		updateAmmoText();
	}
	
	private void updateAmmoText() {
		ammoText.setText(String.valueOf(this.currentAmmo));
		if (this.currentAmmo == 0) {
			ammoText.setColor(0.8f, 0.2f, 0.2f);
		} else {
			ammoText.setColor(1f, 1f, 1f);
		}
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

	public Text getAmmoText() {
		return ammoText;
	}

	public void setAmmoText(Text ammoText) {
		this.ammoText = ammoText;
	}

}
