package com.callil.rotatingsentries;

import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityManager;
import com.callil.rotatingsentries.entityComponentSystem.systems.DamageSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.MoveSystem;
import com.callil.rotatingsentries.entityComponentSystem.systems.RenderSystem;
import com.callil.rotatingsentries.util.SpriteLoader;

import android.util.Log;

public class GameActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	public static int CAMERA_WIDTH = 1080;
	public static int CAMERA_HEIGHT = 1920;

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
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
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
		
		final Sprite background = new Sprite(CAMERA_WIDTH/2 - 540 , CAMERA_HEIGHT/2 - 540, this.spriteLoader.getBackgroundRegion(), this.mEngine.getVertexBufferObjectManager());
		this.mScene.attachChild(background);
		
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
