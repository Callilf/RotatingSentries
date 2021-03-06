/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.entities;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import com.callil.rotatingsentries.entityComponentSystem.components.DiamondComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.EnemyNinjaComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.EnemyRobberComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.MoveTowardsComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SolidComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.SpriteComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.AttackComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.DefenseComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.DetectableComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.attackDefense.ExplosiveComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.powerups.PowerUpComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractPrimaryAttackComponent.ProjectileType;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.AbstractSecondaryAttackComponent.ExplosiveType;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.PrimaryShootingComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.SecondaryMineComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.skills.AOEAttackComponent;
import com.callil.rotatingsentries.enums.PowerUpTypeEnum;
import com.callil.rotatingsentries.enums.SpriteAnimationEnum;
import com.callil.rotatingsentries.util.Couple;
import com.callil.rotatingsentries.util.SpriteLoader;
import com.callil.rotatingsentries.util.SpriteUtil;

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
		sRobber.setZIndex(10);
		Entity robber = this.em.createEntity();
		
		this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitboxDiff(10, 20, 10, 30), robber);
		// BOARD MODE (the two following call are the same
		//this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitboxDiff(-40, 40, -40, 40), robber);
		//this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitbox(-40, 40, 176, 16), robber);
		
		final AnimatedSprite sRope = new AnimatedSprite(x, y, this.spriteLoader.getEnemyRobberRopeTextureRegion(), this.vertextBufferObjectManager);
		this.em.addComponentToEntity(new EnemyRobberComponent(speed, target, sRope, ropeRotation), robber);
		this.em.addComponentToEntity(new AttackComponent(1, 1), robber);
		
		//Detectable compo
		this.em.addComponentToEntity(new DetectableComponent(), robber);
		return robber;
	}
	
	/**
	 * Generate an strong red robber at the desired position.
	 * @param x the x position
	 * @param y the y position
	 * @param speed the speed of the movement
	 * @param target the target of the movement (the robber will go towards the center of it's target)
	 * @return The entity corresponding to the created robber.
	 */
	public Entity generateRobberRed(float x, float y, float speed, Sprite target, float ropeRotation) {
		final AnimatedSprite sRobber = new AnimatedSprite(x, y, this.spriteLoader.getEnemyRobberRedTextureRegion(), this.vertextBufferObjectManager);
		sRobber.setZIndex(10);
		Entity robber = this.em.createEntity();
		
		this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitboxDiff(10, 20, 10, 30), robber);
		// BOARD MODE (the two following call are the same
		//this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitboxDiff(-40, 40, -40, 40), robber);
		//this.em.addComponentToEntity(new SpriteComponent(sRobber, true).defineRectangularHitbox(-40, 40, 176, 16), robber);
		
		final AnimatedSprite sRope = new AnimatedSprite(x, y, this.spriteLoader.getEnemyRobberRopeTextureRegion(), this.vertextBufferObjectManager);
		this.em.addComponentToEntity(new EnemyRobberComponent(speed, target, sRope, ropeRotation), robber);
		this.em.addComponentToEntity(new AttackComponent(2, 1), robber);
		
		//Detectable compo
		this.em.addComponentToEntity(new DetectableComponent(), robber);
		return robber;
	}
	
	
	/**
	 * Generate an strong red robber at the desired position.
	 * @param x the x position
	 * @param y the y position
	 * @param speed the speed of the movement
	 * @param target the target of the movement (the robber will go towards the center of it's target)
	 * @return The entity corresponding to the created robber.
	 */
	public Entity generateNinja(float x, float y, Sprite target, float rotation) {
		final AnimatedSprite sNinja = new AnimatedSprite(x, y, this.spriteLoader.getEnemyNinjaTextureRegion(), this.vertextBufferObjectManager);
		sNinja.setZIndex(10);
		SpriteUtil.setOrientation(sNinja, target);
		Entity ninja = this.em.createEntity();
		
		this.em.addComponentToEntity(new SpriteComponent(sNinja, true).defineRectangularHitboxDiff(25, 25, 30, 40), ninja);		
		this.em.addComponentToEntity(new EnemyNinjaComponent(target, 1.0f, 2.0f), ninja);
		this.em.addComponentToEntity(new AttackComponent(2, 1), ninja);
		this.em.addComponentToEntity(new DetectableComponent(), ninja);
		return ninja;
	}
	
	/**
	 * Generate a sentry at the middle of the screen.
	 * @param rotation the start rotation of the sentry
	 * @return The entity corresponding to the created sentry.
	 */
	public Entity generateSentry(int rotation) {
		// Sprite compo
		TiledTextureRegion sentryTexture = spriteLoader.getSentryTextureRegion();
		final AnimatedSprite sSentry = new AnimatedSprite(gameArea.getWidth()/2 - sentryTexture.getWidth()/2, gameArea.getHeight()/2 - sentryTexture.getHeight()/2, 
				sentryTexture, this.vertextBufferObjectManager);
		sSentry.setZIndex(11);
		sSentry.setRotation(rotation);
		sSentry.stopAnimation(0);
		Entity sentry = this.em.createEntity();
		SpriteComponent spriteComponent = new SpriteComponent(sSentry, true).defineRectangularHitbox(108, 10, 55, 70);
		this.em.addComponentToEntity(spriteComponent, sentry);
		this.em.addComponentToEntity(new SelfRotationComponent(3, 0.8f, 0.2f, rotation, true, true), sentry);
		
		//Primary fire compo
		Sprite primaryFireIcon = new Sprite(0, 0, spriteLoader.getFireIconPrimaryStandardTextureRegion(), this.vertextBufferObjectManager);
		this.em.addComponentToEntity(new PrimaryShootingComponent(primaryFireIcon, ProjectileType.STANDARD, 0.5f, 
				SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrames(), 
				SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrameDurations()), sentry);
		
		
//		//Secondary fire compo
//		//Secondary shooting test :
//		Sprite secondaryFireIcon = new Sprite(0, 0, spriteLoader.getFireIconSecondaryPiercingTextureRegion(), this.vertextBufferObjectManager);
//		Text ammoText = new Text(0, 0, spriteLoader.getSecondaryAmmoFont(), "999", new TextOptions(HorizontalAlign.RIGHT), this.vertextBufferObjectManager);
//		this.em.addComponentToEntity(new SecondaryShootingComponent(secondaryFireIcon, 50, ammoText, ProjectileType.PIERCING, 0.1f, 
//				SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrames(), 
//				SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrameDurations()), sentry);
		
		//Secondary mine test :
		Sprite secondaryMineIcon = new Sprite(0, 0, spriteLoader.getFireIconSecondaryMineTextureRegion(), this.vertextBufferObjectManager);
		Sprite targetSprite = new Sprite(0, 0, spriteLoader.getTargetTextureRegion(), this.vertextBufferObjectManager);
		Text ammoText = new Text(0, 0, spriteLoader.getSecondaryAmmoFont(), "999", new TextOptions(HorizontalAlign.RIGHT), this.vertextBufferObjectManager);
		this.em.addComponentToEntity(new SecondaryMineComponent(secondaryMineIcon, 10, ammoText, targetSprite), sentry);
		
		//AOE attack compo
		Rectangle hitbox = (Rectangle)spriteComponent.getHitbox();
		final AnimatedSprite sElectricAttack = new AnimatedSprite(0, 0, this.spriteLoader.getSentryElectricAttackTextureRegion(), this.vertextBufferObjectManager);
		sElectricAttack.setX(hitbox.getX() + hitbox.getWidth()/2 - sElectricAttack.getWidth()/2);
		sElectricAttack.setY(hitbox.getY() + hitbox.getHeight()/2 - sElectricAttack.getHeight()/2);
		sElectricAttack.setVisible(false);
		sSentry.attachChild(sElectricAttack);
		final Sprite sElectricAttackIcon = new Sprite(0, 0, this.spriteLoader.getSkillElectricityTextureRegion(), this.vertextBufferObjectManager);
		final Sprite sElectricAttackIconFrame = new Sprite(0, 0, this.spriteLoader.getSkillFrameTextureRegion(), this.vertextBufferObjectManager);
		//Temp : placing the icon on the top right of the HUD
		sElectricAttackIconFrame.setX(1508);
		sElectricAttackIconFrame.setY(10);
		sElectricAttackIcon.setX(1508);
		sElectricAttackIcon.setY(10);
		this.em.addComponentToEntity(new AOEAttackComponent(sElectricAttack, sElectricAttackIcon, sElectricAttackIconFrame, hitbox, 5, 2, this.vertextBufferObjectManager), sentry);
		
		//Detectable compo
		this.em.addComponentToEntity(new DetectableComponent(), sentry);
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
			TextureRegion standardTexture = spriteLoader.getProjStdTextureRegion();
			final Sprite sStandardProjectile = new Sprite(gameArea.getWidth()/2 + startX * rayonSentry - standardTexture.getWidth()/2f, 
					gameArea.getHeight()/2 + startY * rayonSentry - standardTexture.getHeight()/2f, standardTexture, this.vertextBufferObjectManager);
			sStandardProjectile.setZIndex(13);
			Entity standardProjectile = this.em.createEntity();
			SpriteComponent scStdProj = new SpriteComponent(sStandardProjectile, true);
			this.em.addComponentToEntity(scStdProj, standardProjectile);
			this.em.addComponentToEntity(new MoveTowardsComponent(15, startX, startY), standardProjectile);
			this.em.addComponentToEntity(new DefenseComponent(1, 1, false), standardProjectile);
			gameArea.attachChild(sStandardProjectile);
			scStdProj.setAttached(true);
			return standardProjectile;
		case PIERCING:
			TextureRegion piercingProjectileTexture = spriteLoader.getProjPiercingTextureRegion();
			final Sprite sPiercingProjectile = new Sprite(gameArea.getWidth()/2 + startX * rayonSentry - piercingProjectileTexture.getWidth()/2f, 
					gameArea.getHeight()/2 + startY * rayonSentry - piercingProjectileTexture.getHeight()/2f, piercingProjectileTexture, this.vertextBufferObjectManager);
			sPiercingProjectile.setZIndex(13);
			sPiercingProjectile.setRotation(rotationDegre);
			Entity piercingProjectile = this.em.createEntity();
			SpriteComponent scPiercingProj = new SpriteComponent(sPiercingProjectile, true);
			this.em.addComponentToEntity(scPiercingProj, piercingProjectile);
			this.em.addComponentToEntity(new MoveTowardsComponent(25, startX, startY), piercingProjectile);
			this.em.addComponentToEntity(new DefenseComponent(5, 2, false), piercingProjectile);
			gameArea.attachChild(sPiercingProjectile);
			scPiercingProj.setAttached(true);
			return piercingProjectile;
		case SHURIKEN:
			TextureRegion shurikenProjectileTexture = spriteLoader.getProjShurikenTextureRegion();
			final Sprite sShurikenProjectile = new Sprite(gameArea.getWidth()/2 + startX * rayonSentry - shurikenProjectileTexture.getWidth()/2f, 
					gameArea.getHeight()/2 + startY * rayonSentry - shurikenProjectileTexture.getHeight()/2f, shurikenProjectileTexture, this.vertextBufferObjectManager);
			sShurikenProjectile.setZIndex(13);
			sShurikenProjectile.setRotation(rotationDegre);
			Entity shurikenProjectile = this.em.createEntity();
			SpriteComponent scShurikenProj = new SpriteComponent(sShurikenProjectile, true);
			this.em.addComponentToEntity(scShurikenProj, shurikenProjectile);
			this.em.addComponentToEntity(new SelfRotationComponent(10), shurikenProjectile);
			this.em.addComponentToEntity(new MoveTowardsComponent(10, startX, startY), shurikenProjectile);
			this.em.addComponentToEntity(new DefenseComponent(7, 2, false), shurikenProjectile);
			gameArea.attachChild(sShurikenProjectile);
			scShurikenProj.setAttached(true);
			return shurikenProjectile;
		default:
			throw new IllegalArgumentException("Undefined projectile " + projectileType);
		}
	}
	
	/**
	 * Generate a shuriken sent by an enemy.
	 * @param enemySprite
	 * @return
	 */
	public Entity generateEnemyShuriken(Sprite enemySprite) {
		float rotationDegre = enemySprite.getRotation();
		//Handle the fact the enemy sprites are oriented facing south.
		rotationDegre = (rotationDegre + 180)%360;
		double rotationRad = rotationDegre * Math.PI / 180d;
		float startX = (float) Math.sin(rotationRad);
		float startY = (float) Math.cos(rotationRad) * -1;
		
		TextureRegion shurikenProjectileTexture = spriteLoader.getProjShurikenTextureRegion();
		Couple<Float> enemyCenter = SpriteUtil.getCenter(enemySprite);
		final Sprite sShurikenProjectile = new Sprite(enemyCenter.getX() - shurikenProjectileTexture.getWidth() / 2f, 
				enemyCenter.getY() - shurikenProjectileTexture.getHeight() / 2f,
				shurikenProjectileTexture, this.vertextBufferObjectManager);
		sShurikenProjectile.setZIndex(13);
		sShurikenProjectile.setRotation(rotationDegre);
		Entity shurikenProjectile = this.em.createEntity();
		SpriteComponent scShurikenProj = new SpriteComponent(sShurikenProjectile, true);
		scShurikenProj.defineRectangularHitboxDiff(15, 5, 15, 20);
		this.em.addComponentToEntity(scShurikenProj, shurikenProjectile);
		this.em.addComponentToEntity(new SelfRotationComponent(15), shurikenProjectile);
		this.em.addComponentToEntity(new MoveTowardsComponent(10, startX, startY), shurikenProjectile);
		this.em.addComponentToEntity(new AttackComponent(1, 2), shurikenProjectile);
		gameArea.attachChild(sShurikenProjectile);
		scShurikenProj.setAttached(true);
		return shurikenProjectile;
	}
	
	/**
	 * Generate a projectile
	 * @param explosiveType the type of projectile
	 * @param sentry the sprite of the sentry which generate the projectile
	 */
	public Entity generateExplosive(ExplosiveType explosiveType, float x, float y) {
		switch (explosiveType) {
		case GRENADE:
		case MINE:
			//Sprite
			final AnimatedSprite mineSprite = new AnimatedSprite(0, 0, spriteLoader.getProjMineTextureRegion(), this.vertextBufferObjectManager);
			SpriteUtil.setCenter(mineSprite, x, y);
			Entity mine = this.em.createEntity();
			SpriteComponent mineSpriteCompo = new SpriteComponent(mineSprite, true);
			this.em.addComponentToEntity(mineSpriteCompo, mine);
			gameArea.attachChild(mineSprite);
			mineSprite.setZIndex(1);
			mineSpriteCompo.setAttached(true);
			mineSprite.animate(SpriteAnimationEnum.MINE_BLINK.getFrameDurations(), SpriteAnimationEnum.MINE_BLINK.getFrames(), true);
			
			//Explosive
			Rectangle blastArea = new Rectangle(0, 0, 200, 200, this.vertextBufferObjectManager);
			SpriteUtil.setCenter(blastArea, SpriteUtil.getCenter(mineSprite));
			gameArea.attachChild(blastArea);
			Rectangle triggerArea = new Rectangle(0, 0, 150, 150, this.vertextBufferObjectManager);
			SpriteUtil.setCenter(triggerArea, SpriteUtil.getCenter(mineSprite));
			gameArea.attachChild(triggerArea);
			AnimatedSprite explosion = new AnimatedSprite(0, 0, spriteLoader.getExplosionTextureRegion(), this.vertextBufferObjectManager);
			SpriteUtil.setCenter(explosion,SpriteUtil.getCenter(mineSprite));
			gameArea.attachChild(explosion);
			this.em.addComponentToEntity(new ExplosiveComponent(5, blastArea, triggerArea, 1.5f, explosion), mine);
			
			return mine;
		default:
			throw new IllegalArgumentException("Undefined explosive " + explosiveType);
		}
	}

	/**
	 * Generate a power up that can be collected by shooting it.
	 * @param x the x pos
	 * @param y the y pos
	 * @return the generated power up
	 */
	public Entity generatePowerUp(float centerX, float centerY, PowerUpTypeEnum type) {
		final Sprite sPowerUp = new Sprite(0, 0, spriteLoader.getPowerUpTextureRegion(), this.vertextBufferObjectManager);
		sPowerUp.setZIndex(9);
		SpriteUtil.setCenter(sPowerUp, centerX, centerY);
		Entity powerUp = this.em.createEntity();
		this.em.addComponentToEntity(new SpriteComponent(sPowerUp, false), powerUp);
		
		Text puText = new Text(0, 0, spriteLoader.getPowerUpFont(), "azertyuiop azertyuiop azertyuiop", new TextOptions(AutoWrap.WORDS, 74, HorizontalAlign.CENTER), this.vertextBufferObjectManager);
		puText.setPosition(43 - puText.getWidth()/2f, 43 - puText.getHeight()/2f);

		sPowerUp.attachChild(puText);
		this.em.addComponentToEntity(new PowerUpComponent(type, 4, puText), powerUp);
		return powerUp;
	}

}
