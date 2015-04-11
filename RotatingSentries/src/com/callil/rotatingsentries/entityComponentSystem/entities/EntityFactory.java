/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.entities;

import static com.callil.rotatingsentries.GameActivity.CAMERA_HEIGHT;
import static com.callil.rotatingsentries.GameActivity.CAMERA_WIDTH;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.callil.rotatingsentries.entityComponentSystem.components.MoveTowardsComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.util.SpriteLoader;

/**
 * @author Callil
 * The entity factory used to generate the different entities in the game.
 */
public class EntityFactory {

	/** The entity manager. */
	private EntityManager em;
	
	/** The camera. */
	private Camera camera;
	
	/** The sprite loader. */
	private SpriteLoader spriteLoader;
	
	/** Vertex buffer object manager. */
	private VertexBufferObjectManager vertextBufferObjectManager;
	
	/**
	 * Constructor.
	 */
	public EntityFactory(EntityManager em, Camera camera, SpriteLoader spriteLoader, VertexBufferObjectManager vertextBufferObjectManager) {
		this.em = em;
		this.camera = camera;
		this.spriteLoader = spriteLoader;
		this.vertextBufferObjectManager = vertextBufferObjectManager;
	}
	
	/**
	 * Generate a player at the desired position.
	 * @param x the x position
	 * @param y the y position
	 * @return The entity corresponding to the created player.
	 */
	public Entity generatePlayer(float x, float y) {
		final Sprite sPlayer = new Sprite(x, y , this.spriteLoader.getPlayerTextureRegion(), this.vertextBufferObjectManager);
		Entity player = this.em.createEntity();
		this.em.addComponentToEntity(new SpriteComponent(sPlayer, true), player);
		
		return player;
	}
	
	/**
	 * Generate an standard robber at the desired position.
	 * @param x the x position
	 * @param y the y position
	 * @param speed the speed of the movement
	 * @param target the target of the movement (the robber will go towards the center of it's target)
	 * @return The entity corresponding to the created robber.
	 */
	public Entity generateRobber(float x, float y, float speed, Sprite target) {
		final AnimatedSprite sRobber = new AnimatedSprite(x, y, this.spriteLoader.getEnemyRobberTextureRegion(), this.vertextBufferObjectManager);
		sRobber.animate(100, true);
		
		Entity robber = this.em.createEntity();
		this.em.addComponentToEntity(new SpriteComponent(sRobber, true), robber);
		// Component used to make the enemy move towards the center of it's target
		this.em.addComponentToEntity(new MoveTowardsComponent(speed, target), robber);
		
		
		return robber;
	}
	
	/**
	 * Generate a sentry at the middle of the screen.
	 * @param rotation the start rotation of the sentry
	 * @return The entity corresponding to the created sentry.
	 */
	public Entity generateSentry(int rotation) {
		TextureRegion sentryTexture = spriteLoader.getSentryTextureRegion();
		final Sprite sSentry = new Sprite((CAMERA_WIDTH - sentryTexture.getWidth())/2, (CAMERA_HEIGHT - sentryTexture.getHeight())/2, 
				sentryTexture, this.vertextBufferObjectManager);
		Entity sentry = this.em.createEntity();
		this.em.addComponentToEntity(new SpriteComponent(sSentry, true), sentry);
		this.em.addComponentToEntity(new SelfRotationComponent(5, rotation, true), sentry);
		
		return sentry;
	}
//	
//	/**
//	 * Generate a rock at the desired position.
//	 * @param x x position
//	 * @param y y position
//	 * @return the entity corresponding to the created rock.
//	 */
//	public Entity generateItemRock(float x, float y) {
//		final Sprite sRock = new Sprite(x , y, this.spriteLoader.getRockTextureRegion(), this.vertextBufferObjectManager);
//		Entity rock = this.em.createEntity();
//		SpriteComponent rockSP = new SpriteComponent(sRock, false);
//		rockSP.setName("RockSpriteComponent");
//		LevitatingItemComponent rockLC = new LevitatingItemComponent(3f, 150f, new SelfRotationComponent(5), 1, 1);
//		rockLC.setName("RockLevitatedComponent");
//		this.em.addComponentToEntity(rockSP, rock);
//		this.em.addComponentToEntity(rockLC, rock);
//		return rock;
//	}
//	
//	/**
//	 * Generate scissors at the desired position.
//	 * @param x x position
//	 * @param y y position
//	 * @return the entity corresponding to the created scissors.
//	 */
//	public Entity generateItemScissors(float x, float y) {
//		final Sprite sScissors = new Sprite(x, y, this.spriteLoader.getScissorsTextureRegion(), this.vertextBufferObjectManager);
//		Entity scissors = this.em.createEntity();
//		SpriteComponent scissorsSP = new SpriteComponent(sScissors, false);
//		scissorsSP.setName("ScissorsSpriteComponent");
//		LevitatingItemComponent scissorsLC = new LevitatingItemComponent(-5f, 180f, new SelfRotationComponent(-8), 2, 1);
//		scissorsLC.setName("ScissorsLevitatedComponent");
//		this.em.addComponentToEntity(scissorsSP, scissors);
//		this.em.addComponentToEntity(scissorsLC, scissors);
//		return scissors;
//	}
//	
//	/**
//	 * Generate a panda at the desired position.
//	 * @param x x position
//	 * @param y y position
//	 * @return the entity corresponding to the created panda.
//	 */
//	public Entity generateEnemyPanda(float x, float y, float directionX, float directionY) {
//		final Sprite sPanda = new Sprite(x , y, this.spriteLoader.getPandaTextureRegion(), this.vertextBufferObjectManager);
//		Entity panda = this.em.createEntity();
//		SpriteComponent pandaSC = new SpriteComponent(sPanda, true);
//		pandaSC.setName("PandaSpriteComponent");
//		this.em.addComponentToEntity(pandaSC, panda);
//		this.em.addComponentToEntity(new EnemyComponent(1), panda);
//		this.em.addComponentToEntity(new HealthComponent(2, new Text(0, 0, this.spriteLoader.getHPFont(), "Placeholder", this.vertextBufferObjectManager), true), panda);
//		this.em.addComponentToEntity(new HitableComponent(1), panda);
//		this.em.addComponentToEntity(new StraightMoveComponent(4.0f, directionX, directionY), panda);
//		return panda;
//	}

}
