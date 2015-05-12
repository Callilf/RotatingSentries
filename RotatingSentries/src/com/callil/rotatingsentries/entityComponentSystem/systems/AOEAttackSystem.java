/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.AOEAttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.singleton.GameSingleton;

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
				if (isCooldownOver(aoeAttackComponent)) {
					//Reinit the size of the rect and hide it
					//TODO: play the animation showing that the cooldown is over
					Log.i("RS", "AOEAttack - Cooldown over !!!!");
					aoeAttackComponent.getCooldownRectangle().setHeight(aoeAttackComponent.getCooldownRectangeMaxHeight());
					aoeAttackComponent.setReady(true);
					aoeAttackComponent.getCooldownRectangle().setVisible(false);
				} else {
					//Cooldown not over, keep reducing the size of the rect
					float remainingTime = aoeAttackComponent.getCooldown() - (GameSingleton.getInstance().getTotalTime() - aoeAttackComponent.getLastAttackTime());
					float rectHeightToSet = ((aoeAttackComponent.getCooldownRectangeMaxHeight() * remainingTime) / aoeAttackComponent.getCooldown());
					aoeAttackComponent.getCooldownRectangle().setHeight(rectHeightToSet);
					
					aoeAttackComponent.setReady(false);
					aoeAttackComponent.getCooldownRectangle().setVisible(true);
				}
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
