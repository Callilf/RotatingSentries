package com.callil.rotatingsentries;

import java.util.Locale;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;

import android.util.Log;

import com.callil.rotatingsentries.data.DataSave;
import com.callil.rotatingsentries.singleton.GameSingleton;
import com.callil.rotatingsentries.util.SpriteLoader;

public abstract class ParentGameActivity extends BaseGameActivity {
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
	protected Scene mScene;
	protected Scene pauseScene;
	protected Scene endScene;
	protected SpriteLoader spriteLoader;

	
	/** Whether the game is paused or not. */
	private boolean paused;
	private Text pauseMenuTimeText;
	private Text endMenuTimeText;
	
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
		spriteLoader = SpriteLoader.getInstance(); 
		spriteLoader.initGameSprite(this);
	}
	

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		Log.d("RS", "onCreateScene");
		paused = false;
		this.mScene = new Scene();
		this.mScene.setBackground(new Background(0.23f, 0.23f, 0.23f));
		
		// Create pause scene
		pauseScene = new CameraScene(mCamera);
	    
		Sprite pauseBackground = new Sprite(0, 0, spriteLoader.getMenuPauseBackgroundTextureRegion(), getVertexBufferObjectManager());
		pauseBackground.setX(mCamera.getCenterX() - pauseBackground.getWidth()/2);
		pauseBackground.setY(mCamera.getCenterY() - pauseBackground.getHeight()/2);
		pauseScene.attachChild(pauseBackground);
		
		pauseMenuTimeText = new Text(480, 260, spriteLoader.getMenuFont(), "01234567890123456789", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
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
	    
	    
	    //Create end scene
	    endScene = new CameraScene(mCamera);
	    
		Sprite endBackground = new Sprite(0, 0, spriteLoader.getMenuPauseBackgroundTextureRegion(), getVertexBufferObjectManager());
		endBackground.setX(mCamera.getCenterX() - endBackground.getWidth()/2);
		endBackground.setY(mCamera.getCenterY() - endBackground.getHeight()/2);
		endScene.attachChild(endBackground);
		
		endMenuTimeText = new Text(480, 260, spriteLoader.getMenuFont(), "01234567890123456789", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
		endBackground.attachChild(endMenuTimeText);  
	    
	    final Sprite restartButtonSprite = new Sprite(100, 416, spriteLoader.getMenuPauseButtonRestartTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	recreateScene();
	            }
	            return true;
	        }           
	    };
	    endScene.registerTouchArea(restartButtonSprite);
	    endBackground.attachChild(restartButtonSprite);
	    
	    final Sprite homeButtonSprite2 = new Sprite(452, 416, spriteLoader.getMenuPauseButtonHomeTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	finish();
	            }
	            return true;
	        }           
	    };
	    endScene.registerTouchArea(homeButtonSprite2);
	    endBackground.attachChild(homeButtonSprite2);
	     
	    endScene.setBackgroundEnabled(false);   
	    // END OF TEST
	}

	
	@Override
	public void onBackPressed()
	{
	    if (!paused) {
	    	pauseGame();
	    }
	}	
	
	public void pauseGame() {
		Log.i("RS", "Paused !!!");
		mScene.setIgnoreUpdate(true);
		mScene.setChildScene(pauseScene, false, true, true);
	    setCurrentTimeString();
		paused = true;
	}

	public void resumeGame() {
		Log.i("RS", "Resumed !!!");
		mScene.clearChildScene();
		mScene.setIgnoreUpdate(false);
		paused = false;
	}
	
	public void endGame() {
		Log.i("RS", "Paused !!!");
		mScene.setIgnoreUpdate(true);
		mScene.setChildScene(endScene, false, true, true);
	    setCurrentTimeString();
	    
		// save the score
		DataSave data = DataSave.getSaveData(this);
		float timePassed = GameSingleton.getInstance().getTotalTime();
		if (data != null) {
			// if it's a new highscore, store the file
			if (data.addScore(timePassed)) {
				data.saveData(this);
				Log.d("RS", "New highscore > " + timePassed);
				// TODO tell the people it's a new highscore
			}
		}
		
		paused = true;
	}
	
	/**
	 * Set the current time string attribute to display it in the pause menu.
	 */
	private void setCurrentTimeString() {
		String seconds = String.format(Locale.FRANCE, "%.3f", GameSingleton.getInstance().getTotalTime());
	    pauseMenuTimeText.setText(seconds);
	    endMenuTimeText.setText(seconds);
	}

	/**
	 * Recreate the complete scene
	 */
	public void recreateScene() {
		try {
//			Thread.sleep(1500);
			onCreateScene(null);
			mEngine.setScene(mScene);
			onPopulateScene(mScene, null);
		}
		catch(Exception e) {
			Log.e("RS", "Error while reloading the scene");
		}
	}

}
