/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.entityComponentSystem.components.MoveTowardsComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.util.Couple;
import com.callil.rotatingsentries.util.MathUtil;


/**
 * @author Callil
 *
 */
public class MoveSystem extends System {
	
	/** The gameArea. */
	private RectangularShape gameArea;
		
	/**
	 * @param em
	 */
	public MoveSystem(EntityManager em, RectangularShape gameArea) {
		super(em);
		this.gameArea = gameArea;
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class);
	    for (Entity entity : entities) {
	    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	
	    	if (spriteComponent != null) {
	    		Sprite sprite = spriteComponent.getSprite();
	    		
	    		
		    	//--Handle the straight movements
		    	MoveTowardsComponent moveTowardsComponent = this.entityManager.getComponent(MoveTowardsComponent.class, entity);
		    	if (moveTowardsComponent != null && moveTowardsComponent.getSpeed() > 0) {
		    		
		    		if (moveTowardsComponent.getTarget() != null) {
		    			// Move towards a TARGET
		    			Sprite target = moveTowardsComponent.getTarget();
		    			Couple<Float> targetCenter = new Couple<Float>(target.getX() + target.getWidth()/2, target.getY() + target.getHeight()/2);
		    			Couple<Float> spriteCenter = new Couple<Float>(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
		    			
		    			Couple<Float> directionalVector = MathUtil.computeDirectionalVector(spriteCenter, targetCenter);
		    			if (directionalVector.getX() != 0 && directionalVector.getY() != 0) {
		    				sprite.setX(sprite.getX() + (directionalVector.getX() * moveTowardsComponent.getSpeed()));
			    			sprite.setY(sprite.getY() + (directionalVector.getY() * moveTowardsComponent.getSpeed()));
			    			
			    			float angle = MathUtil.computeOrientationAngleFromDirectionalVector(directionalVector);
			    			sprite.setRotation(angle);
		    			}
		    		} else {
		    			// Move straight in a specific DIRECTION
			    		sprite.setX(sprite.getX() + (moveTowardsComponent.getDirectionX() * moveTowardsComponent.getSpeed()));
			    		sprite.setY(sprite.getY() + (moveTowardsComponent.getDirectionY() * moveTowardsComponent.getSpeed()));
			    		
			    		// test if a straight forward entity is out of screen
			    		if (sprite.getX() < 0 || sprite.getX() > gameArea.getWidth() + sprite.getWidth() ||
			    				sprite.getY() < 0 || sprite.getY() > gameArea.getHeight() + sprite.getHeight()) {
			    			//Log.d("MoveSystem", "Removing entity out of screen : x=" + sprite.getX() + ", Y=" + sprite.getY());
			    			entityManager.removeEntity(entity);
			    		}
		    		}
		    	}
		    	
//		    	
//	    		
//	    		
//		    	//-- Handle the bounded sprites
//		    	if (spriteComponent.isBounded()) {
//		    		
//					if (sprite.getX() < 0) {
//						sprite.setX(0);
//					}
//					if (sprite.getY() < 0) {
//						sprite.setY(0);
//					}
//					if (sprite.getX() > GameActivity.CAMERA_WIDTH - sprite.getWidth()) {
//						sprite.setX(GameActivity.CAMERA_WIDTH - sprite.getWidth());
//					}
//					if (sprite.getY() > GameActivity.CAMERA_HEIGHT - sprite.getHeight()) {
//						sprite.setY(GameActivity.CAMERA_HEIGHT -sprite.getHeight());
//					}
//		    	}
//		    	
//		    	
//		    	
//		    	//-- Handle circular levitation
//		    	LevitatingItemComponent levitatedComponent = (LevitatingItemComponent) this.entityManager.getComponent(LevitatingItemComponent.class.getName(), entity);
//		    	if (levitatedComponent != null && levitatedComponent.isActive()) {
//		    		float speed = levitatedComponent.getSpeed();
//		    		Sprite parent = levitatedComponent.getParent();
//		    		
//		    		//get the angle in radians
//		            double angle = Math.toRadians(speed);           
//		            
//		            sprite.setX( sprite.getX() - parent.getWidth()/2 + sprite.getWidth()/2);
//            		sprite.setY( sprite.getY() - parent.getHeight()/2 + sprite.getHeight()/2);
//
//            		float newx = (float) (sprite.getX() * Math.cos(angle) - sprite.getY() * Math.sin(angle));
//            		float newy = (float) (sprite.getX() * Math.sin(angle) + sprite.getY() * Math.cos(angle));
//
//            		sprite.setX(newx + parent.getWidth()/2 - sprite.getWidth()/2);
//            		sprite.setY(newy + parent.getHeight()/2 - sprite.getHeight()/2);
//		    	}
//		    	
//		    	
//		    	
//		    	//--Handle the straight movements
//		    	StraightMoveComponent straightMoveComponent = (StraightMoveComponent) this.entityManager.getComponent(StraightMoveComponent.class.getName(), entity);
//		    	if (straightMoveComponent != null && straightMoveComponent.getSpeed() > 0) {
//		    		if (sprite.getX() == 0 || sprite.getX() == GameActivity.CAMERA_WIDTH - sprite.getWidth()) {
//		    			//invert x
//		    			straightMoveComponent.setDirectionX(-straightMoveComponent.getDirectionX());
//		    		}
//		    		if (sprite.getY() == 0 || sprite.getY() == GameActivity.CAMERA_HEIGHT -sprite.getHeight()) {
//		    			//invert y
//		    			straightMoveComponent.setDirectionY(-straightMoveComponent.getDirectionY());
//		    		}
//		    		sprite.setX(sprite.getX() + (straightMoveComponent.getDirectionX() * straightMoveComponent.getSpeed()));
//		    		sprite.setY(sprite.getY() + (straightMoveComponent.getDirectionY() * straightMoveComponent.getSpeed()));
//		    	}
//		    	
		    	
	    	}
	    }
	}


	@Override
	public void reset() {}


	@Override
	public void onPopulateScene() {}

}
