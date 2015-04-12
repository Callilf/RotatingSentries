/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.EnemyRobberComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.EnemyRobberComponent.EnemyRobberStateType;
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
	
	private Scene scene;

	/**
	 * @param em
	 */
	public EnemyRobberSystem(EntityManager em, Scene scene) {
		super(em);
		this.scene = scene;
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(EnemyRobberComponent.class.getName());
	    for (Entity entity : entities) {
	    	EnemyRobberComponent enemyRobberComponent = (EnemyRobberComponent) this.entityManager.getComponent(EnemyRobberComponent.class.getName(), entity);
	    	AnimatedSprite rope = (AnimatedSprite)enemyRobberComponent.getRope();
	    	SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
	    	AnimatedSprite sprite = (AnimatedSprite)spriteComponent.getSprite();
	    	
	    	MoveTowardsComponent moveTowardsComponent = (MoveTowardsComponent) this.entityManager.getComponent(MoveTowardsComponent.class.getName(), entity);
	    	
	    	
	    	// Switch on the different states of a robber
	    	switch(enemyRobberComponent.getState()) {
	    	
	    	case INITIALIZING:
	    		rope = (AnimatedSprite)enemyRobberComponent.getRope();
	    		rope.animate(SpriteAnimationEnum.ENEMY_ROBBER_ROPE.getFrameDurations(), SpriteAnimationEnum.ENEMY_ROBBER_ROPE.getFrames(), false);
	    		rope.setRotation(enemyRobberComponent.getRopeRotation());
	    		scene.attachChild(rope);
	    		enemyRobberComponent.setState(EnemyRobberStateType.ARRIVING);
	    		sprite.setVisible(false);
	    		break;

	    	case ARRIVING:
	    		// If animation finished
	    		if (!rope.isAnimationRunning()) {
	    			sprite.setVisible(true);
	    			enemyRobberComponent.setState(EnemyRobberStateType.WALKING);
		    		//Add the move component
	    			this.entityManager.addComponentToEntity(new MoveTowardsComponent(enemyRobberComponent.getSpeed(), enemyRobberComponent.getTarget()), entity);
			    	//Start the walking animation
			    	sprite.animate(SpriteAnimationEnum.ENEMY_ROBBER_WALK.getFrameDurations(), SpriteAnimationEnum.ENEMY_ROBBER_WALK.getFrames(), true);
	    		}
	    		break;


	    	case WALKING:
	    		
		    	//If the robber is close enough to it's target, switch state
		    	Sprite target = moveTowardsComponent.getTarget();
		    	//Log.i("RS", "Distance between sprites : " + SpriteUtil.distanceBetweenCenters(sprite, target));
		    	if (SpriteUtil.distanceBetweenCenters(sprite, target) <= 100) {
		    		enemyRobberComponent.setState(EnemyRobberStateType.ATTACKING);
		    		sprite.animate(SpriteAnimationEnum.ENEMY_ROBBER_ATTACK.getFrameDurations(), SpriteAnimationEnum.ENEMY_ROBBER_ATTACK.getFrames(), true);
		    		this.entityManager.removeComponentFromEntity(MoveTowardsComponent.class.getName(), entity);
		    	}
		    	
	    		break;


	    	case ATTACKING:
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
