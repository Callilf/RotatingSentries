package com.callil.rotatingsentries.entityComponentSystem.components;

/**
 * Entity that can hit and be hit by AttackComponent
 * 
 * @author Thomas
 */
public class DefenseComponent extends AttackComponent {

	public DefenseComponent(int hp, int damage) {
		super(hp, damage);
	}

}
