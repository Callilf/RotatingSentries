package com.callil.rotatingsentries;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;

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
import com.callil.rotatingsentries.util.SpriteLoader;

public class GameActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	public static final boolean DEBUG_MODE = false;
	
	public static final int CAMERA_WIDTH = 1920;
	public static final int CAMERA_HEIGHT = 1080;

	// ===========================================================
	// Fields
	// ===========================================================

	public Camera mCamera;
	//protected Scene mScene;
	protected Scene mScene;
	protected Scene pauseScene;
	private RectangularShape gameArea;
	
	private EntityManager entityManager;
	private SpriteLoader spriteLoader;
	private EntityFactory entityFactory;
	
	//Systems
	private List<System> systems;
	
	/** Whether the game is paused or not. */
	private boolean paused;
	private Text pauseMenuTimeText;
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Log.d("RS", "onCreateEngineOptions");
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}
	

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		Log.d("RS", "onCreateResources");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		spriteLoader = new SpriteLoader(this); 
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		Log.d("RS", "onCreateScene");
		this.mScene = new Scene();
		this.mScene.setBackground(new Background(1.0f, 1.0f, 1.0f));
		
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
		
		//Set the game time in the singleton
		this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				GameSingleton.getInstance().setTotalTime(GameSingleton.getInstance().getTotalTime() + pSecondsElapsed);
			}
		});

		if (pOnCreateSceneCallback != null) {
			pOnCreateSceneCallback.onCreateSceneFinished(this.mScene);
		}
		
		
		// TEST
		// Create pause scene
		pauseScene = new CameraScene(mCamera);
	    
		Sprite pauseBackground = new Sprite(0, 0, spriteLoader.getMenuPauseBackgroundTextureRegion(), getVertexBufferObjectManager());
		pauseBackground.setX(background.getX() + background.getWidth()/2 - pauseBackground.getWidth()/2);
		pauseBackground.setY(background.getY() + background.getHeight()/2 - pauseBackground.getHeight()/2);
		pauseScene.attachChild(pauseBackground);
		
		pauseMenuTimeText = new Text(390, 220, spriteLoader.getMenuFont(), "01234567890123456789", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
	    pauseBackground.attachChild(pauseMenuTimeText);  

	    final Sprite unpauseButtonSprite = new Sprite(100, 416, spriteLoader.getMenuPauseButtonPlayTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	resumeGame();
	            }
	            return true;
	        }           
	    };
	    pauseScene.registerTouchArea(unpauseButtonSprite);
	    pauseBackground.attachChild(unpauseButtonSprite);
	    
	    final Sprite homeButtonSprite = new Sprite(452, 416, spriteLoader.getMenuPauseButtonHomeTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	finish();
	            }
	            return true;
	        }           
	    };
	    pauseScene.registerTouchArea(homeButtonSprite);
	    pauseBackground.attachChild(homeButtonSprite);
	     
	    pauseScene.setBackgroundEnabled(false);
	    // END OF TEST
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

	
	@Override
	public void onBackPressed()
	{
	    if (!paused) {
	    	pauseGame();
	    }
	}	
	
	private void pauseGame() {
		Log.i("RS", "Paused !!!");
		mScene.setIgnoreUpdate(true);
		mScene.setChildScene(pauseScene, false, true, true);
	    setCurrentTimeString();
		paused = true;
	}

	private void resumeGame() {
		Log.i("RS", "Resumed !!!");
		mScene.clearChildScene();
		mScene.setIgnoreUpdate(false);
		paused = false;
	}
	
	/**
	 * Set the current time string attribute to display it in the pause menu.
	 */
	private void setCurrentTimeString() {
		long millis = (long)GameSingleton.getInstance().getTotalTime();
	    long s = millis % 60;
	    long m = (millis / 60) % 60;
	    long h = (millis / (60 * 60)) % 24;
	    pauseMenuTimeText.setText(String.format("%d:%02d:%02d", h,m,s));
	}

	/**
	 * Recreate the complete scene
	 */
	public void recreateScene() {
		try {
			Thread.sleep(1500);
			onCreateScene(null);
			mEngine.setScene(mScene);
			onPopulateScene(mScene, null);
			
		}
		catch(Exception e) {
			Log.e("RS", "Error while reloading the scene");
		}
	}


}
