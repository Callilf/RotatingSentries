/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.graphics.Point;

import com.callil.rotatingsentries.entityComponentSystem.components.MoveTowardsComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;


/**
 * @author Callil
 *
 */
public class MoveSystem extends System {
	
	/** The scene. */
	private Scene scene;
		
	/**
	 * @param em
	 */
	public MoveSystem(EntityManager em, Scene scene) {
		super(em);
		this.scene = scene;
	}


	@Override
	public void onUpdate(float pSecondsElapsed) {
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class.getName());
	    for (Entity entity : entities) {
	    	SpriteComponent spriteComponent = (SpriteComponent) this.entityManager.getComponent(SpriteComponent.class.getName(), entity);
	    	
	    	if (spriteComponent != null) {
	    		Sprite sprite = spriteComponent.getSprite();
	    		
	    		
		    	//--Handle the straight movements
		    	MoveTowardsComponent moveTowardsComponent = (MoveTowardsComponent) this.entityManager.getComponent(MoveTowardsComponent.class.getName(), entity);
		    	if (moveTowardsComponent != null && moveTowardsComponent.getSpeed() > 0) {
		    		
		    		if (moveTowardsComponent.getTarget() != null) {
		    			// Move towards a TARGET
		    			Sprite target = moveTowardsComponent.getTarget();
		    			Point targetCenter = new Point((int)(target.getX() + target.getWidth()/2), (int)(target.getY() + target.getHeight()/2));
		    			Point spriteCenter = new Point((int)(sprite.getX() + sprite.getWidth()/2), (int)(sprite.getY() + sprite.getHeight()/2));
		    			float vx = targetCenter.x - spriteCenter.x;
		    			float vy = targetCenter.y - spriteCenter.y;
		    			if (vx != 0 && vy != 0) {
		    				float xMove = vx/(Math.abs(vx + vy));
		    				float yMove = vy/(Math.abs(vx + vy));
		    			
		    				sprite.setX(sprite.getX() + (xMove * moveTowardsComponent.getSpeed()));
			    			sprite.setY(sprite.getY() + (yMove * moveTowardsComponent.getSpeed()));
			    			
			    			double angle = Math.atan2(yMove, xMove);
			    			double angleInDegree = ((angle > 0 ? angle : (2*Math.PI + angle)) * 360 / (2*Math.PI) + 270) % 360;
			    			sprite.setRotation((float) angleInDegree);
		    			}
		    		} else {
		    			// Move straight in a specific DIRECTION
//		    			//Bounce against wall
//			    		if (sprite.getX() == 0 || sprite.getX() == GameActivity.CAMERA_WIDTH - sprite.getWidth()) {
//			    			//invert x
//			    			straightMoveComponent.setDirectionX(-straightMoveComponent.getDirectionX());
//			    		}
//			    		if (sprite.getY() == 0 || sprite.getY() == GameActivity.CAMERA_HEIGHT -sprite.getHeight()) {
//			    			//invert y
//			    			straightMoveComponent.setDirectionY(-straightMoveComponent.getDirectionY());
//			    		}
			    		sprite.setX(sprite.getX() + (moveTowardsComponent.getDirectionX() * moveTowardsComponent.getSpeed()));
			    		sprite.setY(sprite.getY() + (moveTowardsComponent.getDirectionY() * moveTowardsComponent.getSpeed()));
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
