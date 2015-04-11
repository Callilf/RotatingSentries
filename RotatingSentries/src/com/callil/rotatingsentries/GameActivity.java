package com.callil.rotatingsentries;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

import com.callil.rotatingsentries.entityComponentSystem.components.SelfRotationComponent;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.entityComponentSystem.systems.DamageSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.MoveSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.RenderSystem;
import com.callil.rotatingsentries.util.SpriteLoader;

public class GameActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	public static final int CAMERA_WIDTH = 1920;
	public static final int CAMERA_HEIGHT = 1080;

	// ===========================================================
	// Fields
	// ===========================================================

	public Camera mCamera;
	protected Scene mScene;
	
	private EntityManager entityManager;
	private SpriteLoader spriteLoader;
	private EntityFactory entityFactory;
//	private EnemyGenerator enemyGenerator;
	
	//Systems
	private RenderSystem renderSystem;
	private MoveSystem moveSystem;
	private DamageSystem damageSystem;
	
	
	
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
		
		//EntityManager & systems
		this.entityManager = new EntityManager();
		this.entityFactory = new EntityFactory(this.entityManager, this.mCamera, this.spriteLoader, this.mEngine.getVertexBufferObjectManager());
//		this.enemyGenerator = new EnemyGenerator(this.entityManager, this.entityFactory, 3.0f);
		this.renderSystem = new RenderSystem(this.entityManager, this.mScene);
		this.moveSystem = new MoveSystem(this.entityManager, this.mScene);
		this.damageSystem = new DamageSystem(this.entityManager, this.mScene);
				
		//Set the game time in the singleton
		this.mScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				GameSingleton.getInstance().setTotalTime(GameSingleton.getInstance().getTotalTime() + pSecondsElapsed);
			}
		});

		pOnCreateSceneCallback.onCreateSceneFinished(this.mScene);
	}

	
	/**
	 * Called at the initialization of the scene.
	 */
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		Log.d("RS", "onPopulateScene");
		
		TextureRegion backgroundTexture = spriteLoader.getBackgroundRegion();
		final Sprite background = new Sprite((CAMERA_WIDTH-backgroundTexture.getWidth())/2, (CAMERA_HEIGHT-backgroundTexture.getHeight())/2, backgroundTexture, this.mEngine.getVertexBufferObjectManager());
		this.mScene.attachChild(background);

		// CREATE BUTTON
		final Sprite arrowLeft = new Sprite(0, 0, spriteLoader.getArrowLeftTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				Log.d("BUTTON", "Left : " + pSceneTouchEvent.getAction());
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
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
				Log.d("BUTTON", "Right : " + pSceneTouchEvent.getAction());
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_UP:
				case TouchEvent.ACTION_OUTSIDE:
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
		this.mScene.registerTouchArea(arrowLeft);
		this.mScene.registerTouchArea(arrowRight);
		this.mScene.attachChild(arrowLeft);
		this.mScene.attachChild(arrowRight);
		
		final Sprite diamond = new Sprite(0 , 0, this.spriteLoader.getDiamondTextureRegion(), this.mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				return true;
			}
		};
		diamond.setX(CAMERA_WIDTH/2 - diamond.getWidth()/2);
		diamond.setY(CAMERA_HEIGHT/2 - diamond.getHeight()/2);
		this.mScene.registerTouchArea(diamond);
		this.mScene.setTouchAreaBindingOnActionDownEnabled(true);
		this.mScene.attachChild(diamond);
		
		this.entityFactory.generateRobber(CAMERA_WIDTH/2 - 600, CAMERA_HEIGHT/2 - 400, 5, diamond);
		this.entityFactory.generateRobber(CAMERA_WIDTH/2 - 500, CAMERA_HEIGHT/2 + 200, 5, diamond);
		this.entityFactory.generateRobber(CAMERA_WIDTH/2 + 300, CAMERA_HEIGHT/2 - 900, 5, diamond);
		this.entityFactory.generateRobber(CAMERA_WIDTH/2 + 300, CAMERA_HEIGHT/2 + 300, 5, diamond);

		entityFactory.generateSentry(30);
		
//		this.entityFactory.generatePlayer(CAMERA_WIDTH/2 - 32 , CAMERA_HEIGHT/2 - 32);
//		this.entityFactory.generateItemRock(200, 200);
//		this.entityFactory.generateItemScissors(400, 450);
//		this.mScene.registerUpdateHandler(this.enemyGenerator);
		

		// Systems
		// /!\ the latest added system will be the first one to be updated
		this.renderSystem.onPopulateScene();
		this.mScene.registerUpdateHandler(this.renderSystem);
		this.damageSystem.onPopulateScene();
		this.mScene.registerUpdateHandler(this.damageSystem);
		this.moveSystem.onPopulateScene();
		this.mScene.registerUpdateHandler(this.moveSystem);





		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}


}
