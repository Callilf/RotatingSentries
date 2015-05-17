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
import org.andengine.util.HorizontalAlign;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.PrimaryShootingComponent;
import com.callil.rotatingsentries.entityComponentSystem.components.shooting.PrimaryShootingComponent.ProjectileType;
import com.callil.rotatingsentries.entityComponentSystem.entities.Entity;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.entityComponentSystem.systems.AOEAttackSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.DamageSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.EnemyRobberSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.GameSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.GenerationSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.MoveSystem;
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
	private float lastSentryChange = -5.0f;
	private boolean testSentry1 = true;
	
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
		
		// Background : game area
		TextureRegion backgroundTexture = spriteLoader.getBackgroundRegion();
		Sprite background = new Sprite((CAMERA_WIDTH-backgroundTexture.getWidth())/2, (CAMERA_HEIGHT-backgroundTexture.getHeight())/2, backgroundTexture, this.mEngine.getVertexBufferObjectManager());
		this.gameArea = background;
		
		//EntityManager & systems
		this.entityManager = new EntityManager();
		this.entityFactory = new EntityFactory(this.entityManager, this.gameArea, this.spriteLoader, this.mEngine.getVertexBufferObjectManager());
			
		GameSystem gameSystem = new GameSystem(this.entityManager, this);
		RenderSystem renderSystem = new RenderSystem(this.entityManager, background, mScene);
		MoveSystem moveSystem = new MoveSystem(this.entityManager, background);
		DamageSystem damageSystem = new DamageSystem(this.entityManager);
		EnemyRobberSystem enemyRobberSystem = new EnemyRobberSystem(this.entityManager, background);
		GenerationSystem generationSystem = new GenerationSystem(entityManager, entityFactory, background);
		AOEAttackSystem aoeAttackSystem = new AOEAttackSystem(this.entityManager);

		// /!\ System : the latest added system will be the first one to be updated
		systems = new ArrayList<System>();
		systems.add(gameSystem);
		systems.add(renderSystem);
		systems.add(moveSystem);
		systems.add(damageSystem);
		systems.add(enemyRobberSystem);
		systems.add(generationSystem);
		systems.add(aoeAttackSystem);
		
		//Set the game time in the singleton
		final EntityManager entityMgr = this.entityManager;
		this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				GameSingleton instance = GameSingleton.getInstance();
				instance.setTotalTime(GameSingleton.getInstance().getTotalTime() + pSecondsElapsed);
				setTimerText(instance.getTotalTime());
				
				
				//######################
				// TESTS
				
//				PrimaryShootingComponent shooter1 = new PrimaryShootingComponent(ProjectileType.STANDARD, 0.9f, 
//						SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrames(), 
//						SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrameDurations());
//				PrimaryShootingComponent shooter2 = new PrimaryShootingComponent(ProjectileType.STANDARD, 0.05f, 
//				SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrames(), 
//				SpriteAnimationEnum.SENTRY_STANDARD_SHOOT.getFrameDurations());
//				
//				//Change sentry every 5 seconds
//				if (instance.getTotalTime() - lastSentryChange > 5.0f) {
//					Log.w("RS", "5 seconds");
//					lastSentryChange = instance.getTotalTime();
//					List<Entity> sentries = entityMgr.getAllEntitiesPosessingComponentOfClass(PrimaryShootingComponent.class);
//					for (Entity sentry : sentries) {
//						if (testSentry1) {
//							Log.w("RS", "Set shooter 1");
//							entityMgr.replaceComponentForEntity(shooter1, sentry);
//							testSentry1 = false;
//						} else {
//							Log.w("RS", "Set shooter 2");
//							entityMgr.replaceComponentForEntity(shooter2, sentry);
//							testSentry1 = true;
//						}
//					}
//				}
//				
				
				
				
				
				
				// END OF TESTS
				//######################
				
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
		
//		AnimatedSprite electric = new AnimatedSprite(background.getX() + 200, background.getY() + 200, spriteLoader.getSentryElectricAttackTextureRegion(), this.getVertexBufferObjectManager());
//		this.mScene.attachChild(electric);
		
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
		timerText = new Text(140, 75, spriteLoader.getPlayerHPFont(), "0123456789", new TextOptions(HorizontalAlign.CENTER), getVertexBufferObjectManager());
		timerText.setZIndex(11);
		this.mScene.attachChild(timerText);  
		
		// Create Health Panel
		final Sprite healthPanel = new Sprite(8, 174, spriteLoader.getHUDPanelHealthTextureRegion(), this.mEngine.getVertexBufferObjectManager());
		healthPanel.setZIndex(10);
		this.mScene.attachChild(healthPanel);
		
		// Create Switch Fire Panel
		final AnimatedSprite switchFirePanel = new AnimatedSprite(8, 342, spriteLoader.getHUDPanelSwitchFireTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
				case TouchEvent.ACTION_CANCEL:
					//TODO
					GenerationSystem.isPrimaryFireActive = !GenerationSystem.isPrimaryFireActive;
					GenerationSystem.hasFireBeenSwitched = true;
					this.stopAnimation(GenerationSystem.isPrimaryFireActive ? 0 : 1);
					break;
				case TouchEvent.ACTION_MOVE:
				case TouchEvent.ACTION_DOWN:
				default:
					break;
				}
				return true;
			}
		};
		switchFirePanel.setZIndex(10);
		switchFirePanel.stopAnimation(0);
		this.mScene.registerTouchArea(switchFirePanel);
		this.mScene.attachChild(switchFirePanel);
		
		// CREATE ARROW BUTTONS
		final Sprite arrowLeft = new Sprite(8, CAMERA_HEIGHT/2, spriteLoader.getArrowLeftTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
				case TouchEvent.ACTION_CANCEL:
					SelfRotationComponent.leftPressed = 0;
					break;
				case TouchEvent.ACTION_MOVE:
					if (SelfRotationComponent.leftPressed > 0) break;
				case TouchEvent.ACTION_DOWN:
					SelfRotationComponent.leftPressed = SelfRotationComponent.rightPressed + 1;
					break;
				default:
					break;
				}
				return true;
			}
		};
		TextureRegion trArrowRight = spriteLoader.getArrowRightTextureRegion();
		final Sprite arrowRight = new Sprite(CAMERA_WIDTH - trArrowRight.getWidth() - 8, CAMERA_HEIGHT/2, trArrowRight, this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
				case TouchEvent.ACTION_CANCEL:
					SelfRotationComponent.rightPressed = 0;
					break;
				case TouchEvent.ACTION_MOVE:
					if (SelfRotationComponent.rightPressed > 0) break;
				case TouchEvent.ACTION_DOWN:
					SelfRotationComponent.rightPressed = SelfRotationComponent.leftPressed + 1;
					break;
				default:
					break;
				}
				return true;
			}
		};
		arrowLeft.setZIndex(10);
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
