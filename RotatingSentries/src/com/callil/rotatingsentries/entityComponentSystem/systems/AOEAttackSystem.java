/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import com.callil.rotatingsentries.GameSingleton;
import com.callil.rotatingsentries.entityComponentSystem.components.AOEAttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

/**
 * @author Callil
 * Handle the aoe attack cooldown.
 */
public class AOEAttackSystem extends System {

	/**
	 * @param em
	 */
	public AOEAttackSystem(EntityManager em) {
		super(em);
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		
		// MANAGE COLLISIONS OF AOE ATTACKS
		List<Entity> aoeAttacks = this.entityManager.getAllEntitiesPosessingComponentOfClass(AOEAttackComponent.class);
		for (Entity aoeAttack : aoeAttacks) {
			AOEAttackComponent aoeAttackComponent = this.entityManager.getComponent(AOEAttackComponent.class, aoeAttack);
			if (aoeAttackComponent.isAttacking()){
				if (!aoeAttackComponent.getSprite().isAnimationRunning()) {
					aoeAttackComponent.finishAttack();
				}
			}
			if (!aoeAttackComponent.isReady()) {
				aoeAttackComponent.setReady(isCooldownOver(aoeAttackComponent));
			}
		}
		
	}


	/**
	 * @param aoeAttackComponent
	 * @return
	 */
	private boolean isCooldownOver(AOEAttackComponent aoeAttackComponent) {
		return GameSingleton.getInstance().getTotalTime() - aoeAttackComponent.getLastAttackTime() >= aoeAttackComponent.getCooldown();
	}

	
	
	
	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}