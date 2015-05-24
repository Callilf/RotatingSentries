package com.callil.rotatingsentries.entityComponentSystem.systems;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;

import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractPrimaryAttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractSecondaryAttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.skills.AbstractSkillComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;

/**
 * @author Callil
 *
 */
public class RenderSystem extends System {

	/** The gameArea on which we want to render. */
	private RectangularShape gameArea;
	
	/** The scene. */
	private Scene scene;
	
	/**
	 * @param em
	 */
	public RenderSystem(EntityManager em, RectangularShape gameArea, Scene scene) {
		super(em);
		this.gameArea = gameArea;
		this.setScene(scene);
	}

	@Override
	public void onPopulateScene() {
		
		//ATTACH SPRITES TO SCENE AND HEALTH TO SPRITES
		List<Entity> entities = 
				this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class);
	    for (Entity entity : entities) {
	    	
	    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
	    	if (spriteComponent != null) {
	    		this.gameArea.attachChild(spriteComponent.getSprite());
	    		spriteComponent.setAttached(true);
	    	}

	    }

	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		// the draw will sort the children with ZIndex just before the next drawing
		this.gameArea.sortChildren(false);
		
		// ATTACH SPRITES
		List<Entity> entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SpriteComponent.class);
		for (Entity entity : entities) {
			SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
			if (spriteComponent != null && !spriteComponent.isAttached()) {
				this.gameArea.attachChild(spriteComponent.getSprite());
				spriteComponent.setAttached(true);
			}
			
			//Attach diamond's life
			DiamondComponent diamondComponent = this.entityManager.getComponent(DiamondComponent.class, entity);
    		if (diamondComponent != null && diamondComponent.getLifeBar() != null && !diamondComponent.getLifeBar().hasParent()) {
    			this.getScene().attachChild(diamondComponent.getLifeBarMax());
    			diamondComponent.getLifeBarMax().setZIndex(8);
    			this.getScene().attachChild(diamondComponent.getLifeBar());
    			diamondComponent.getLifeBar().setZIndex(9);
    			this.getScene().sortChildren();
    		}
    		
			//Attach primary fire icon
    		AbstractPrimaryAttackComponent primFireComponent = this.entityManager.getComponent(AbstractPrimaryAttackComponent.class, entity);
    		if (primFireComponent != null && primFireComponent.getIcon() != null && !primFireComponent.getIcon().hasParent()) {
    			primFireComponent.getIcon().setX(1524);
    			primFireComponent.getIcon().setY(337);
    			this.getScene().attachChild(primFireComponent.getIcon());
    			primFireComponent.getIcon().setZIndex(9);
    			this.getScene().sortChildren();
    		}
    		
			//Attach secondary fire icon and ammo
    		AbstractSecondaryAttackComponent secFireComponent = this.entityManager.getComponent(AbstractSecondaryAttackComponent.class, entity);
    		if (secFireComponent != null && secFireComponent.getIcon() != null && !secFireComponent.getIcon().hasParent()) {
    			secFireComponent.getIcon().setX(1727);
    			secFireComponent.getIcon().setY(337);
    			this.getScene().attachChild(secFireComponent.getIcon());
    			secFireComponent.getIcon().setZIndex(9);
    			this.getScene().sortChildren();
    		}
    		if (secFireComponent != null && secFireComponent.getAmmoText() != null && !secFireComponent.getAmmoText().hasParent()) {
    			secFireComponent.getAmmoText().setX(1790);
    			secFireComponent.getAmmoText().setY(456);
    			this.getScene().attachChild(secFireComponent.getAmmoText());
    			secFireComponent.getAmmoText().setZIndex(11);
    			this.getScene().sortChildren();
    		}
		}
		//Attach skill icons
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(AbstractSkillComponent.class);
		for (Entity entity : entities) {
			AbstractSkillComponent skillComponent = this.entityManager.getComponent(AbstractSkillComponent.class, entity);
			if (skillComponent != null && !skillComponent.getIconSprite().hasParent()) {
				this.scene.attachChild(skillComponent.getIconSprite());
				this.scene.attachChild(skillComponent.getCooldownRectangle());
				this.scene.attachChild(skillComponent.getIconFrame());
				this.scene.sortChildren();
			}
		}
		
		
		// MANAGE SELF ROTATIONS
		entities = this.entityManager.getAllEntitiesPosessingComponentOfClass(SelfRotationComponent.class);
	    for (Entity entity : entities) {
	    	
	    	SelfRotationComponent rotationComponent = this.entityManager.getComponent(SelfRotationComponent.class, entity);
	    	if (rotationComponent.isActive()) {
	    		if (rotationComponent.isAffectedByButton()) {
	    			// define the direction of rotate if a button is active
	    			if (rotationComponent.isLastLeftPressed()) {
	    				rotationComponent.setClockwise(false);
	    			}
	    			else if (rotationComponent.isLastRightPressed()) {
	    				rotationComponent.setClockwise(true);
	    			}
	    			else {
	    				rotationComponent.setCurrentSpeed(0);
	    				continue;
	    			}
	    		}
		    	SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
		    	float speed = rotationComponent.increaseSpeed() * (rotationComponent.isClockwise()? 1 : -1);
		    	if (rotationComponent != null && spriteComponent != null) {
		    		rotationComponent.setCurrentRotation( (rotationComponent.getCurrentRotation() + speed) % 360);
		    		spriteComponent.getSprite().setRotation(rotationComponent.getCurrentRotation());
		    	}
	    	}
	    	
	    }
	    
	    
	    // MANAGER ENEMY RECOVERY BLINK
//	    entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(AttackComponent.class);
//		for (Entity entity : entities) {
//			
//			AttackComponent hitableComponent = this.entityManager.getComponent(AttackComponent.class, entity);
//			SpriteComponent spriteComponent = this.entityManager.getComponent(SpriteComponent.class, entity);
//			if (hitableComponent != null && hitableComponent.getLastHitTime() != null) {
//				//Check whether the enemy is in recovery
//				if (hitableComponent.isInRecovery()) {
//					if (!spriteComponent.isBlinking()) {
//						//Enemy enters recovery
//						spriteComponent.getSprite().registerEntityModifier(spriteComponent.getBlinkModifier());
//						spriteComponent.setBlinking(true);
//					}
//				} else {
//					if (spriteComponent.isBlinking()) {
//						//Deactivate blink
//						spriteComponent.getSprite().unregisterEntityModifier(spriteComponent.getBlinkModifier());
//						spriteComponent.getSprite().setAlpha(1.0f);
//						spriteComponent.setBlinking(false);
//					}
//				}
//			}
//		}
		
		// MANAGER PLAYER RECOVERY BLINK
//	    entities = 
//				this.entityManager.getAllEntitiesPosessingComponentOfClass(PlayerComponent.class);
//		for (Entity player : entities) {
//			PlayerComponent playerComponent = this.entityManager.getComponent(PlayerComponent.class, player);
//			SpriteComponent playerSprite = this.entityManager.getComponent(SpriteComponent.class, player);
//			if (playerComponent != null && playerComponent.getLastHitTime() != null) {
//				//Check whether the enemy is in recovery
//				if (playerComponent.isInRecovery()) {
//					if (!playerSprite.isBlinking()) {
//						//Enemy enters recovery
//						playerSprite.getSprite().registerEntityModifier(playerSprite.getBlinkModifier());
//						playerSprite.setBlinking(true);
//					}
//				} else {
//					if (playerSprite.isBlinking()) {
//						//Deactivate blink
//						playerSprite.getSprite().unregisterEntityModifier(playerSprite.getBlinkModifier());
//						playerSprite.getSprite().setAlpha(1.0f);
//						playerSprite.setBlinking(false);
//					}
//				}
//			}
//		}
		
	}

	
	@Override
	public void reset() {}

	
	//Getters & Setters
	
	public RectangularShape getGameArea() {
		return gameArea;
	}

	public void setGameArea(RectangularShape gameArea) {
		this.gameArea = gameArea;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
