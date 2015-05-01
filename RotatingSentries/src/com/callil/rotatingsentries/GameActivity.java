package com.callil.rotatingsentries;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
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
	
	//Systems
	private List<System> systems;
	
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
		RenderSystem renderSystem = new RenderSystem(this.entityManager, background);
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
		
		// CREATE BUTTON
		final Sprite arrowLeft = new Sprite(0, 0, spriteLoader.getArrowLeftTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
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
		// CREATE BUTTON
		TextureRegion trArrowRight = spriteLoader.getArrowRightTextureRegion();
		final Sprite arrowRight = new Sprite(CAMERA_WIDTH - trArrowRight.getWidth(), 0, trArrowRight, this.mEngine.getVertexBufferObjectManager()) {
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
}
