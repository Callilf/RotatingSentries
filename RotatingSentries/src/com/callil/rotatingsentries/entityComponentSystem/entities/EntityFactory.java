/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.entities;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.callil.rotatingsentries.entityComponentSystem.components.AOEAttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.AttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.DefenseComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.EnemyRobberComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.MoveTowardsComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.ShootingComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.ShootingComponent.ProjectileType;
import com.callil.rotatingsentries.entityComponentSystem.components.SolidComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.util.SpriteLoader;

/**
 * @author Callil
 * The entity factory used to generate the different entities in the game.
 */
public class EntityFactory {

	/** The entity manager. */
	private EntityManager em;
	
	/** The game area */
	private RectangularShape gameArea;
	
	/** The sprite loader. */
	private SpriteLoader spriteLoader;
	
	/** Vertex buffer object manager. */
	private VertexBufferObjectManager vertextBufferObjectManager;
	
	/**
	 * Constructor.
	 */
	public EntityFactory(EntityManager em, RectangularShape gameArea, SpriteLoader spriteLoader, VertexBufferObjectManager vertextBufferObjectManager) {
		this.em = em;
		this.gameArea = gameArea;
		this.spriteLoader = spriteLoader;
		this.vertextBufferObjectManager = vertextBufferObjectManager;
	}
	
	
	
	/**
	 * Generate a wall at the desired position.
	 * @param x the x position
	 * @param y the y position
	 * @param rotation the rotation of the sprite
	 * @return The entity corresponding to the created wall.
	 */
	public Entity generateWall(float x, float y, float rotation) {
		final Sprite sWall = new Sprite(x, y , this.spriteLoader.getWallRegion(), this.vertextBufferObjectManager);
		sWall.setRotation(rotation);
		Entity wall = this.em.createEntity();
		this.em.addComponentToEntity(new SpriteComponent(sWall, true), wall);
		this.em.addComponentToEntity(new SolidComponent(), wall);

		return wall;
	}
	
	
	/**
	 * Generate a diamond centered on the desired position.
	 * @param x the x position
	 * @param y the y position
	 * @return The entity corresponding to the created diamond.
	 */
	public Entity generateDiamond(float x, float y, float frequency) {
		Sprite sDiamond = new Sprite(x , y, this.spriteLoader.getDiamondTextureRegion(), this.vertextBufferObjectManager);
		sDiamond.setX(sDiamond.getX() - sDiamond.getWidth()/2);
		sDiamond.setY(sDiamond.getY() - sDiamond.getHeight()/2);
		Entity diamond = this.em.createEntity();
		this.em.addComponentToEntity(new SpriteComponent(sDiamond, true), diamond);
		
		Rectangle currLifeRect = new Rectangle(26, 242, 370, 80, this.vertextBufferObjectManager);
		currLifeRect.setColor(0.8f, 0.2f, 0.2f);
		Rectangle maxLifeRect = new Rectangle(26, 242, 370, 80, this.vertextBufferObjectManager);
		maxLifeRect.setColor(0.79f, 0.79f, 0.79f);
		this.em.addComponentToEntity(new DiamondComponent(frequency, 20, currLifeRect, maxLifeRect), diamond);
		
		return diamond;
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
	public Entity generateRobber(float x, float y, float speed, Sprite target, float ropeRotation) {
		final AnimatedSprite sRobber = new AnimatedSprite(x, y, this.spriteLoader.getEnemyRobberTextureRegion(), this.vertextBufferObjectManager);
		Entity robber = this.em.createEntity();
		
		this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitboxDiff(10, 20, 10, 30), robber);
		// BOARD MODE (the two following call are the same
		//this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitboxDiff(-40, 40, -40, 40), robber);
		//this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitbox(-40, 40, 176, 16), robber);
		
		final AnimatedSprite sRope = new AnimatedSprite(x, y, this.spriteLoader.getEnemyRobberRopeTextureRegion(), this.vertextBufferObjectManager);
		this.em.addComponentToEntity(new EnemyRobberComponent(speed, target, sRope, ropeRotation), robber);
		this.em.addComponentToEntity(new AttackComponent(1, 1), robber);
		
		return robber;
	}
	
	/**
	 * Generate a sentry at the middle of the screen.
	 * @param rotation the start rotation of the sentry
	 * @return The entity corresponding to the created sentry.
	 */
	public Entity generateSentry(int rotation) {
		TextureRegion sentryTexture = spriteLoader.getSentryTextureRegion();
		final Sprite sSentry = new Sprite(gameArea.getWidth()/2 - sentryTexture.getWidth()/2, gameArea.getHeight()/2 - sentryTexture.getHeight()/2, 
				sentryTexture, this.vertextBufferObjectManager);
		sSentry.setRotation(rotation);
		Entity sentry = this.em.createEntity();
		SpriteComponent spriteComponent = new SpriteComponent(sSentry, true).defineRectangularHitbox(108, 10, 55, 70);
		this.em.addComponentToEntity(spriteComponent, sentry);
		this.em.addComponentToEntity(new SelfRotationComponent(3, 0.2f, rotation, true, true), sentry);
		this.em.addComponentToEntity(new ShootingComponent(ProjectileType.STANDARD, 0.5f), sentry);
		
		Rectangle hitbox = (Rectangle)spriteComponent.getHitbox();
		final AnimatedSprite sElectricAttack = new AnimatedSprite(0, 0, this.spriteLoader.getSentryElectricAttackTextureRegion(), this.vertextBufferObjectManager);
		sElectricAttack.setX(hitbox.getX() + hitbox.getWidth()/2 - sElectricAttack.getWidth()/2);
		sElectricAttack.setY(hitbox.getY() + hitbox.getHeight()/2 - sElectricAttack.getHeight()/2);
		sElectricAttack.setVisible(false);
		sSentry.attachChild(sElectricAttack);
		final Sprite sElectricAttackIcon = new Sprite(0, 0, this.spriteLoader.getSkillElectricityTextureRegion(), this.vertextBufferObjectManager);
		final Sprite sElectricAttackIconFrame = new Sprite(0, 0, this.spriteLoader.getSkillFrameTextureRegion(), this.vertextBufferObjectManager);
		//Temp :
		sElectricAttackIconFrame.setX(1508);
		sElectricAttackIconFrame.setY(10);
		sElectricAttackIcon.setX(1508);
		sElectricAttackIcon.setY(10);
		this.em.addComponentToEntity(new AOEAttackComponent(sElectricAttack, sElectricAttackIcon, sElectricAttackIconFrame, hitbox, 5, 2, this.vertextBufferObjectManager), sentry);
		
		return sentry;
	}
	
	/**
	 * Generate a projectile
	 * @param projectileType the type of projectile
	 * @param sentry the sprite of the sentry which generate the projectile
	 */
	public Entity generateProjectile(ProjectileType projectileType, Sprite sentry) {
		float rotationDegre = sentry.getRotation();
		double rotationRad = rotationDegre * Math.PI / 180d;
		float rayonSentry = sentry.getWidth() / 2;
		float startX = (float) Math.sin(rotationRad);
		float startY = (float) Math.cos(rotationRad) * -1;
		
		switch (projectileType) {
		case STANDARD:
			TextureRegion projectileTexture = spriteLoader.getProjStdTextureRegion();
			final Sprite sProjectile = new Sprite(gameArea.getWidth()/2 + startX * rayonSentry - projectileTexture.getWidth()/2f, 
					gameArea.getHeight()/2 + startY * rayonSentry - projectileTexture.getHeight()/2f, projectileTexture, this.vertextBufferObjectManager);
			sProjectile.setRotation(rotationDegre);
			Entity projectile = this.em.createEntity();
			SpriteComponent scProj = new SpriteComponent(sProjectile, true);
			this.em.addComponentToEntity(scProj, projectile);
			this.em.addComponentToEntity(new MoveTowardsComponent(15, startX, startY), projectile);
			this.em.addComponentToEntity(new DefenseComponent(1, 2, false), projectile);
			gameArea.attachChild(sProjectile);
			scProj.setAttached(true);
			return projectile;
		default:
			throw new IllegalArgumentException("Undefined projectile " + projectileType);
		}
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
//		this.em.addComponentToEntity(new AttackComponent(1), panda);
//		this.em.addComponentToEntity(new StraightMoveComponent(4.0f, directionX, directionY), panda);
//		return panda;
//	}

}
