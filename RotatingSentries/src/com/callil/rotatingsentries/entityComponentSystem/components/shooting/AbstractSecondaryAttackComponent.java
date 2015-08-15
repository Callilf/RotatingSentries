/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * @author Callil
 * Defines that the entity can shoot with a primary fire.
 */
public abstract class AbstractSecondaryAttackComponent extends Component {
	
	public enum ExplosiveType {
		GRENADE,
		MINE;
	}

	/** The fire icon. */
	private Sprite icon;
	
	/** The max number of ammunitions. */
	private int maxAmmo;
	/** The current number of ammunitions. */
	private int currentAmmo;
	
	/** The text to display the number of ammunitions. */
	private Text ammoText;
	
	/**
	 * @param maxAmmo the max number of ammunitions
	 */
	public AbstractSecondaryAttackComponent(Sprite icon, int maxAmmo, Text ammoText) {
		this.icon = icon;
		this.maxAmmo = maxAmmo;
		this.currentAmmo = maxAmmo;
		this.ammoText = ammoText;
		updateAmmoText();
		
		//A secondary fire is never active by default. It's activated only when the user activates it on the HUD
		this.setActive(false);
	}

	/**
	 * @param name
	 */
	public AbstractSecondaryAttackComponent(String name, Sprite icon) {
		super(name);
		this.icon = icon;
		
		//A secondary fire is never active by default. It's activated only when the user activates it on the HUD
		this.setActive(false);
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

	public Sprite getIcon() {
		return icon;
	}

	public void setIcon(Sprite icon) {
		this.icon = icon;
	}

}
