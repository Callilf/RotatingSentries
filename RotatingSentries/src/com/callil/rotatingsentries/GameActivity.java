package com.callil.rotatingsentries;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.entityComponentSystem.systems.AOEAttackSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.DamageSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.EnemyNinjaSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.EnemyRobberSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.GameSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.GenerationSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.MineSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.MoveSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.PowerUpSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.RenderSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.System;
import com.callil.rotatingsentries.enums.SpriteAnimationEnum;
import com.callil.rotatingsentries.singleton.GameSingleton;

public class GameActivity extends ParentGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	public static final boolean DEBUG_MODE = false;
	
	public static final int CAMERA_WIDTH = 1920;
	public static final int CAMERA_HEIGHT = 1080;

	// ===========================================================
	// Fields
	// ===========================================================

	private RectangularShape gameArea;
	private EntityManager entityManager;
	private EntityFactory entityFactory;
	private List<System> systems;
	
	public Text timerText;
	

	// TEST Fields
	/** DEBUG frame duration */
	private float dureeTenFrame = 0;
	private long cptFrame = 0;
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		return super.onCreateEngineOptions();
	}
	

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		super.onCreateResources(pOnCreateResourcesCallback);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
		GameSingleton.getInstance().reinit();
		
		// Background : game area
		TextureRegion backgroundTexture = spriteLoader.getBackgroundRegion();
		Sprite background = new Sprite((CAMERA_WIDTH-backgroundTexture.getWidth())/2, (CAMERA_HEIGHT-backgroundTexture.getHeight())/2, backgroundTexture, this.mEngine.getVertexBufferObjectManager()) {
			//Handle touches on the game area
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				GameSingleton singleton = GameSingleton.getInstance();
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
				case TouchEvent.ACTION_CANCEL:
					singleton.isTouchingArea = false;
					singleton.hasReleasedArea = true;
					break;
				case TouchEvent.ACTION_MOVE:
					singleton.areaTouchX = pTouchAreaLocalX;
					singleton.areaTouchY = pTouchAreaLocalY;
					break;
				case TouchEvent.ACTION_DOWN:
					singleton.isTouchingArea = true;
					singleton.areaTouchX = pTouchAreaLocalX;
					singleton.areaTouchY = pTouchAreaLocalY;
					break;
				default:
					break;
				}
				return true;
			}
		};
		this.mScene.registerTouchArea(background);
		this.gameArea = background;
		
		//EntityManager & systems
		this.entityManager = new EntityManager();
		this.entityFactory = new EntityFactory(this.entityManager, this.gameArea, this.spriteLoader, this.mEngine.getVertexBufferObjectManager());
			
		GameSystem gameSystem = new GameSystem(this.entityManager, this);
		RenderSystem renderSystem = new RenderSystem(this.entityManager, background, mScene);
		MoveSystem moveSystem = new MoveSystem(this.entityManager, background);
		DamageSystem damageSystem = new DamageSystem(this.entityManager);
		MineSystem mineSystem = new MineSystem(this.entityManager, entityFactory, background);
		EnemyRobberSystem enemyRobberSystem = new EnemyRobberSystem(this.entityManager, background);
		EnemyNinjaSystem enemyNinjaSystem = new EnemyNinjaSystem(this.entityManager, entityFactory, background);
		GenerationSystem generationSystem = new GenerationSystem(entityManager, entityFactory, background);
		AOEAttackSystem aoeAttackSystem = new AOEAttackSystem(this.entityManager);
		PowerUpSystem powerUpSystem = new PowerUpSystem(this.entityManager, entityFactory, background);

		// /!\ System : the latest added system will be the first one to be updated
		systems = new ArrayList<System>();
		systems.add(gameSystem);
		systems.add(renderSystem);
		systems.add(moveSystem);
		systems.add(damageSystem);
		systems.add(mineSystem);
		systems.add(enemyNinjaSystem);
		systems.add(enemyRobberSystem);
		systems.add(generationSystem);
		systems.add(aoeAttackSystem);
		systems.add(powerUpSystem);
		
		//Set the game time in the singleton
		this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				
				//######################
				// TESTS
//				if (DEBUG_MODE || true) {
//					dureeTenFrame += pSecondsElapsed;
//					if (++cptFrame % 10 == 0) {
//						dureeTenFrame /= 10;
//						Log.d("RS", "Frame : " + dureeTenFrame + "ms, fps : " + (1/dureeTenFrame));
//						dureeTenFrame = 0;
//					}
//				}
				// END OF TESTS
				//######################
				
				pSecondsElapsed = 1/FPS;
				
				GameSingleton instance = GameSingleton.getInstance();
				instance.setTotalTime(GameSingleton.getInstance().getTotalTime() + pSecondsElapsed);
				setTimerText(instance.getTotalTime());			
			}
		});

		if (pOnCreateSceneCallback != null) {
			pOnCreateSceneCallback.onCreateSceneFinished(this.mScene);
		}
	}

	
	/**
	 * Called at the initialization of the scene.
	 */
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		Log.d("RS", "onPopulateScene");

		//Attach the game
		this.mScene.registerTouchArea(gameArea);
		this.mScene.attachChild(gameArea);
		
		//Place walls
		entityFactory.generateWall(0, 0, 0);
		entityFactory.generateWall(gameArea.getWidth()/2 - 20, gameArea.getHeight()/2 - 20, 90);
		entityFactory.generateWall(0, gameArea.getHeight() - 40, 180);
		entityFactory.generateWall( -gameArea.getWidth()/2 + 20, gameArea.getHeight()/2 - 20, 270);
		
		//Place diamond and sentry
		entityFactory.generateDiamond(gameArea.getWidth()/2, gameArea.getHeight()/2, 3.0f);
		entityFactory.generateSentry(30);
		
		createHUD();
		
//		AnimatedSprite ninja = new AnimatedSprite(gameArea.getX() + 200, gameArea.getY() + 200, spriteLoader.getEnemyNinjaTextureRegion(), this.getVertexBufferObjectManager());
//		this.mScene.attachChild(ninja);
//		ninja.animate(SpriteAnimationEnum.ENEMY_NINJA_APPEAR.getFrameDurations(), SpriteAnimationEnum.ENEMY_NINJA_APPEAR.getFrames());
		
		for (System system : systems) {
			system.onPopulateScene();
			mScene.registerUpdateHandler(system);
		}

		GameSingleton.getInstance().setTotalTime(0);
		if (pOnPopulateSceneCallback != null) {
			pOnPopulateSceneCallback.onPopulateSceneFinished();
		}
	}


	/**
	 * Create all the HUD elements.
	 */
	private void createHUD() {
		// Create gray vertical sidebars
		TextureRegion trGraySideBar = spriteLoader.getSideGrayTextureRegion();
		final Sprite grayLeft = new Sprite(0, 0, trGraySideBar, this.mEngine.getVertexBufferObjectManager());
		grayLeft.setZIndex(5);
		this.mScene.attachChild(grayLeft);
		final Sprite grayRight = new Sprite(CAMERA_WIDTH - trGraySideBar.getWidth(), 0, trGraySideBar, this.mEngine.getVertexBufferObjectManager());
		grayRight.setZIndex(5);
		this.mScene.attachChild(grayRight);
		
		// Create Timer Panel
		final Sprite timerPanel = new Sprite(8, 8, spriteLoader.getHUDPanelTimerTextureRegion(), this.mEngine.getVertexBufferObjectManager());
		timerPanel.setZIndex(10);
		this.mScene.attachChild(timerPanel);
		timerText = new Text(140, 75, spriteLoader.getTimerFont(), "0123456789", new TextOptions(HorizontalAlign.CENTER), getVertexBufferObjectManager());
		timerText.setZIndex(11);
		this.mScene.attachChild(timerText);
		
		// Create Health Panel
		final Sprite healthPanel = new Sprite(8, 174, spriteLoader.getHUDPanelHealthTextureRegion(), this.mEngine.getVertexBufferObjectManager());
		healthPanel.setZIndex(10);
		this.mScene.attachChild(healthPanel);
		
		// Create Primary Fire Panel
		final AnimatedSprite primaryFirePanel = new AnimatedSprite(1508, 274, spriteLoader.getHUDPrimaryFireTextureRegion(), this.mEngine.getVertexBufferObjectManager());
		primaryFirePanel.setZIndex(10);
		primaryFirePanel.stopAnimation(1);
		this.mScene.attachChild(primaryFirePanel);
		
		// Create Secondary Fire Panel
		final AnimatedSprite secondaryFirePanel = new AnimatedSprite(1711, 274, spriteLoader.getHUDSecondaryFireTextureRegion(), this.mEngine.getVertexBufferObjectManager());
		secondaryFirePanel.setZIndex(10);
		secondaryFirePanel.stopAnimation(0);
		this.mScene.attachChild(secondaryFirePanel);
		
		// Create Switch Fire Panel
//		final AnimatedSprite switchFirePanel = new AnimatedSprite(8, 342, spriteLoader.getHUDPanelSwitchFireTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
//			@Override
//			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//				switch (pSceneTouchEvent.getAction()) {
//				case TouchEvent.ACTION_UP:
//				case TouchEvent.ACTION_OUTSIDE:
//				case TouchEvent.ACTION_CANCEL:
//					GenerationSystem.isPrimaryFireActive = !GenerationSystem.isPrimaryFireActive;
//					GenerationSystem.hasFireBeenSwitched = true;
//					this.stopAnimation(GenerationSystem.isPrimaryFireActive ? 0 : 2);
//					primaryFirePanel.stopAnimation(GenerationSystem.isPrimaryFireActive ? 1 : 0);
//					secondaryFirePanel.stopAnimation(GenerationSystem.isPrimaryFireActive ? 0 : 1);
//					break;
//				case TouchEvent.ACTION_MOVE:
//					break;
//				case TouchEvent.ACTION_DOWN:
//					this.stopAnimation(GenerationSystem.isPrimaryFireActive ? 1 : 3);
//					break;
//				default:
//					break;
//				}
//				return true;
//			}
//		};
//		switchFirePanel.setZIndex(10);
//		switchFirePanel.stopAnimation(0);
//		this.mScene.registerTouchArea(switchFirePanel);
//		this.mScene.attachChild(switchFirePanel);
		
		// CREATE ARROW BUTTONS
		final AnimatedSprite arrowLeft = new AnimatedSprite(8, CAMERA_HEIGHT/2, spriteLoader.getArrowLeftTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
				case TouchEvent.ACTION_CANCEL:
					SelfRotationComponent.leftPressed = 0;
					this.stopAnimation(0);
					break;
				case TouchEvent.ACTION_MOVE:
					if (SelfRotationComponent.leftPressed > 0) break;
				case TouchEvent.ACTION_DOWN:
					SelfRotationComponent.leftPressed = SelfRotationComponent.rightPressed + 1;
					this.stopAnimation(1);
					break;
				default:
					break;
				}
				return true;
			}
		};
		TiledTextureRegion trArrowRight = spriteLoader.getArrowRightTextureRegion();
		final AnimatedSprite arrowRight = new AnimatedSprite(CAMERA_WIDTH - trArrowRight.getWidth(0) - 8, CAMERA_HEIGHT/2, trArrowRight, this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
				case TouchEvent.ACTION_CANCEL:
					SelfRotationComponent.rightPressed = 0;
					this.stopAnimation(0);
					break;
				case TouchEvent.ACTION_MOVE:
					if (SelfRotationComponent.rightPressed > 0) break;
				case TouchEvent.ACTION_DOWN:
					SelfRotationComponent.rightPressed = SelfRotationComponent.leftPressed + 1;
					this.stopAnimation(1);
					break;
				default:
					break;
				}
				return true;
			}
		};
		arrowLeft.stopAnimation(0);
		arrowLeft.setZIndex(10);
		arrowRight.stopAnimation(0);
		arrowRight.setZIndex(10);
		this.mScene.registerTouchArea(arrowLeft);
		this.mScene.registerTouchArea(arrowRight);
		this.mScene.attachChild(arrowLeft);
		this.mScene.attachChild(arrowRight);
	}
	
	/**
	 * Set the current time string attribute to display it in the pause menu.
	 */
	private void setTimerText(float time) {
		String seconds = String.format("%.3f", time);
	    timerText.setText(seconds);
	}
}
