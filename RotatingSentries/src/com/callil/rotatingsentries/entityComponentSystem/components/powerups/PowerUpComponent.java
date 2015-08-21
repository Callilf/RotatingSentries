/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.powerups;

import org.andengine.entity.text.Text;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;
import com.callil.rotatingsentries.enums.PowerUpTypeEnum;
import com.callil.rotatingsentries.singleton.GameSingleton;

/**
 * @author Callil
 * Defines that the entity can be picked up by shooting it, and disappears after some time.
 */
public class PowerUpComponent extends Component {
	
	/** The number of seconds before it disappears. */
	private float timeToLive;
	
	/** The time at which it spawned. */
	private float spawnTime;
	
	/** The text to display on the powerup. */
	private Text text;
	
	/** The type. */
	private PowerUpTypeEnum type;
	
	
	/**
	 * Constructor.
	 * @param timeToLive the number of seconds before the power up disappears
	 */
	public PowerUpComponent(PowerUpTypeEnum type, float timeToLive, Text text) {
		this.timeToLive = timeToLive;
		this.spawnTime = GameSingleton.getInstance().getTotalTime();
		this.type = type;
		
		this.text = text;
		
		//Handle label of power ups : display the label if the power up is know,
		//displays ???? if not.
		GameSingleton singleton = GameSingleton.getInstance();
		if (singleton.getKnownPowerUps().contains(type)) {
			this.text.setText(type.getLabel());
		} else {
			this.text.setText("????");
		}
	}


	
	
	
	
	public float getTimeToLive() {
		return timeToLive;
	}


	public void setTimeToLive(float timeToLive) {
		this.timeToLive = timeToLive;
	}


	public float getSpawnTime() {
		return spawnTime;
	}


	public void setSpawnTime(float spawnTime) {
		this.spawnTime = spawnTime;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public PowerUpTypeEnum getType() {
		return type;
	}

	public void setType(PowerUpTypeEnum type) {
		this.type = type;
	}

}
