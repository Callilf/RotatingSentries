/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;

import com.callil.rotatingsentries.entityComponentSystem.components.EnemyNinjaComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.EnemyNinjaComponent.EnemyNinjaStateType;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.AttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractPrimaryAttackComponent.ProjectileType;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.enums.SpriteAnimationEnum;

/**
 * @author Callil
 * Handle the robbers cycle of life.
 */
public class EnemyNinjaSystem extends System {
	
	private EntityFactory entityFactory;
	private RectangularShape gameArea;

	/**
	 * @param em
	 */
	public EnemyNinjaSystem(EntityManager em, EntityFactory entityFactory, RectangularShape gameArea) {
		super(em);
		this.entityFactory = entityFactory;
		this.gameArea = gameArea;
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(EnemyNinjaComponent.class);
	    for (Entity entity : entities) {
	    	EnemyNinjaComponent enemyNinjaComponent = this.entityManager.getComponent(EnemyNinjaComponent.class, entity);
	    	AttackComponent attackComponent = this.entityManager.getComponent(AttackComponent.class, entity);
	    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	AnimatedSprite sprite = (AnimatedSprite)spriteComponent.getSprite();	    	
	    	
	    	// Switch on the different states of a robber
	    	switch(enemyNinjaComponent.getState()) {
	    	
	    	case INITIALIZING:
	    		enemyNinjaComponent.setState(EnemyNinjaStateType.ARRIVING);
	    		sprite.animate(SpriteAnimationEnum.ENEMY_NINJA_APPEAR.getFrameDurations(), SpriteAnimationEnum.ENEMY_NINJA_APPEAR.getFrames(), false);
	    		break;
	    		
	    	case ARRIVING:
	    		if (!sprite.isAnimationRunning()) {
	    			enemyNinjaComponent.recover();
		    		enemyNinjaComponent.setState(EnemyNinjaStateType.ATTACKING);
	    			sprite.animate(SpriteAnimationEnum.ENEMY_NINJA_APPEAR_END.getFrameDurations(), SpriteAnimationEnum.ENEMY_NINJA_APPEAR_END.getFrames(), false);
	    		}
	    		break;

	    	case ATTACKING:
	    		if (!sprite.isAnimationRunning() && enemyNinjaComponent.canAttack()) {
	    			enemyNinjaComponent.attack();
	    			enemyNinjaComponent.setState(EnemyNinjaStateType.ATTACK_RECOVERING);
	    			sprite.animate(SpriteAnimationEnum.ENEMY_NINJA_THROW.getFrameDurations(), SpriteAnimationEnum.ENEMY_NINJA_THROW.getFrames(), false);
	    			this.entityFactory.generateEnemyShuriken(sprite);
	    		}
	    		break;

	    	case ATTACK_RECOVERING:
	    		if (!sprite.isAnimationRunning() && enemyNinjaComponent.canRecover()) {
	    			enemyNinjaComponent.recover();
	    			enemyNinjaComponent.setState(EnemyNinjaStateType.ATTACKING);
	    			sprite.animate(SpriteAnimationEnum.ENEMY_NINJA_THROW_RECOVERY.getFrameDurations(), SpriteAnimationEnum.ENEMY_NINJA_THROW_RECOVERY.getFrames(), false);
	    		}
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
