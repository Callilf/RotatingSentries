package com.callil.rotatingsentries.menu;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.util.Log;

import com.callil.rotatingsentries.GameActivity;
import com.callil.rotatingsentries.ParentGameActivity;
import com.callil.rotatingsentries.util.PrefsUtil;
import com.callil.rotatingsentries.util.SpriteLoader;

public class MainMenuActivity extends BaseGameActivity {
	
	private SpriteLoader spriteLoader;
	
	private Scene mainScene;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Log.d("RS", "[MENU] onCreateEngineOptions");
		Camera mCamera = new Camera(0, 0, ParentGameActivity.CAMERA_WIDTH, ParentGameActivity.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(ParentGameActivity.CAMERA_WIDTH, ParentGameActivity.CAMERA_HEIGHT), mCamera);
		//engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		Log.d("RS", "[MENU] onCreateResources");
		PrefsUtil.initPreferences(this);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		spriteLoader = SpriteLoader.getInstance(); 
		spriteLoader.initMenuSprite(this);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		Log.d("RS", "[MENU] onCreateScene");
		mainScene = new Scene();
		mainScene.setBackground(new Background(1.0f, 1.0f, 1.0f));
		
		Sprite menuBackgroundSprite = new Sprite(0, 0, spriteLoader.getMmenuBackgroundTextureRegion(), getVertexBufferObjectManager());
		mainScene.attachChild(menuBackgroundSprite);
		
		Sprite menuTitleSprite = new Sprite(335, 50, spriteLoader.getMmenuTitleTextureRegion(), getVertexBufferObjectManager());
		mainScene.attachChild(menuTitleSprite);
				
	    final Sprite playButtonSprite = new Sprite(324, 400, spriteLoader.getMmenuPlayBtnTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	goToGame();
	            }
	            return true;
	        }           
	    };
	    mainScene.registerTouchArea(playButtonSprite);
	    mainScene.attachChild(playButtonSprite);
		
	    final Sprite optionsButtonSprite = new Sprite(1122, 400, spriteLoader.getMmenuOptionsBtnTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	goToOptions();
	            }
	            return true;
	        }           
	    };
	    mainScene.registerTouchArea(optionsButtonSprite);
	    mainScene.attachChild(optionsButtonSprite);
		
	    final Sprite howToPlayButtonSprite = new Sprite(324, 700, spriteLoader.getMmenuHowtopBtnTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	goToHowToPlay();
	            }
	            return true;
	        }           
	    };
	    mainScene.registerTouchArea(howToPlayButtonSprite);
	    mainScene.attachChild(howToPlayButtonSprite);
		
	    final Sprite creditsButtonSprite = new Sprite(1122, 700, spriteLoader.getMmenuEmptyBtnTextureRegion(), getVertexBufferObjectManager()) {
	        @Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            if (pSceneTouchEvent.isActionUp()) {
	            	goToCredits();
	            }
	            return true;
	        }           
	    };
	    mainScene.registerTouchArea(creditsButtonSprite);
	    mainScene.attachChild(creditsButtonSprite);
	    
	    final Sprite creditsTexteSprite = new Sprite(51, 64, spriteLoader.getMmenuCreditsBtnTextureRegion(), getVertexBufferObjectManager());
	    creditsButtonSprite.attachChild(creditsTexteSprite);
	    
	    pOnCreateSceneCallback.onCreateSceneFinished(mainScene);	    
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	public void goToGame() {
		Intent intent = new Intent(MainMenuActivity.this.getBaseContext(), GameActivity.class);
		startActivity(intent);
		
		//this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public void goToOptions() {
		Intent intent = new Intent(MainMenuActivity.this.getBaseContext(), OptionsActivity.class);
		startActivityForResult(intent, 1);
	}
	
	public void goToHowToPlay() {
		Intent intent = new Intent(MainMenuActivity.this.getBaseContext(), GameActivity.class);
		startActivity(intent);
	}
	
	public void goToCredits() {
		Intent intent = new Intent(MainMenuActivity.this.getBaseContext(), GameActivity.class);
		startActivity(intent);
	}
	
	
	@Override
	public void onBackPressed()
	{
		exitApplication();
	}

	/**
	 * Kill the application
	 */
	private void exitApplication() {
		finish();
		System.gc();
		android.os.Process.killProcess(android.os.Process.myPid());
	}	
		
}
