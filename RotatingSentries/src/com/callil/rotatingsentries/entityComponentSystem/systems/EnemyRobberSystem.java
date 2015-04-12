/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.EnemyRobberComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.MoveTowardsComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.enums.SpriteAnimationEnum;
import com.callil.rotatingsentries.util.SpriteUtil;

/**
 * @author Callil
 * Handle the robbers cycle of life.
 */
public class EnemyRobberSystem extends System {

	/**
	 * @param em
	 */
	public EnemyRobberSystem(EntityManager em) {
		super(em);
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(EnemyRobberComponent.class.getName());
	    for (Entity entity : entities) {
	    	EnemyRobberComponent enemyRobberComponent = (EnemyRobberComponent) this.entityManager.getComponent(EnemyRobberComponent.class.getName(), entity);
	    	SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
	    	AnimatedSprite sprite = (AnimatedSprite)spriteComponent.getSprite();
	    	
	    	MoveTowardsComponent moveTowardsComponent = (MoveTowardsComponent) this.entityManager.getComponent(MoveTowardsComponent.class.getName(), entity);
	    	
	    	
	    	// Switch on the different states of a robber
	    	switch(enemyRobberComponent.getState()) {

	    	case EnemyRobberComponent.STATE_ARRIVING:
	    		//TODO animation ROPE CLIMBING
	    		// If animation finished
	    		if (true) {
	    			enemyRobberComponent.setState(EnemyRobberComponent.STATE_WALKING);
		    		//Add the move component
	    			this.entityManager.addComponentToEntity(new MoveTowardsComponent(enemyRobberComponent.getSpeed(), enemyRobberComponent.getTarget()), entity);
			    	//Start the walking animation
			    	sprite.animate(SpriteAnimationEnum.ENEMY_ROBBER_WALK.getFrameDurations(), SpriteAnimationEnum.ENEMY_ROBBER_WALK.getFrames(), true);
	    		}
	    		break;


	    	case EnemyRobberComponent.STATE_WALKING:
	    		
		    	//If the robber is close enough to it's target, switch state
		    	Sprite target = moveTowardsComponent.getTarget();
		    	//Log.i("RS", "Distance between sprites : " + SpriteUtil.distanceBetweenCenters(sprite, target));
		    	if (SpriteUtil.distanceBetweenCenters(sprite, target) <= 100) {
		    		enemyRobberComponent.setState(EnemyRobberComponent.STATE_ATTACKING);
		    		sprite.animate(new long[]{100}, new int[]{0}, false);
		    		this.entityManager.removeComponentFromEntity(MoveTowardsComponent.class.getName(), entity);
		    	}
		    	
	    		break;


	    	case EnemyRobberComponent.STATE_ATTACKING:
	    		//TODO : animation ATTACK
	    		break;


	    	default:
	    	}

	    }
	}

	@Override
	public void reset() {}

	@Override
	public void onPopulateScene() {}

}
