package com.callil.rotatingsentries.enums;

public enum PowerUpTypeEnum {

	SHOOT_SPEED_UP("SHOOT SPEED UP"),
	SHOOT_SPEED_DOWN("SHOOT SPEED DOWN"),
	SENTRY_SPEED_UP("SENTRY SPEED UP"),
	SENTRY_SPEED_DOWN("SENTRY SPEED DOWN"),
	SECONDARY_FIRE_AMMO("BAG OF AMMO");
	
	
	
	// attr & constructor
	
	/** The name to display on the power up. */
	private String label;
	
	/** Constructor. */
	private PowerUpTypeEnum(String label) {
		this.setLabel(label);
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
