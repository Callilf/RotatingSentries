/**
 * 
 */
package com.callil.rotatingsentries.util;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;

/**
 * @author Callil
 * Useful class for loading sprites.
 */
public class SpriteLoader {

	/** The texture manager. */
	private TextureManager tm;
	
	/** The font manager. */
	private FontManager fm;
	
	/** The context. */
	private BaseGameActivity context;
	
	
	//##############
	// TEXTURES
	
	/** BACROUND. */
	protected TextureRegion mBackgroundRegion;
	public TextureRegion getBackgroundRegion() {
		return mBackgroundRegion;
	}
	
	/** WALLS. */
	protected TextureRegion mWall;
	public TextureRegion getWallRegion() {
		return mWall;
	}
	
	/** ARROW : left */
	protected TextureRegion mArrowLeftTextureRegion;
	public TextureRegion getArrowLeftTextureRegion() {
		return mArrowLeftTextureRegion;
	}
	
	/** ARROW : right */
	protected TextureRegion mArrowRightTextureRegion;
	public TextureRegion getArrowRightTextureRegion() {
		return mArrowRightTextureRegion;
	}
	
	/** DIAMOND. */
	protected TextureRegion mdiamondTextureRegion;
	public TextureRegion getDiamondTextureRegion() {
		return mdiamondTextureRegion;
	}
	
	/** PLAYER. */
	protected TextureRegion mPlayerTextureRegion;
	public TextureRegion getPlayerTextureRegion() {
		return mPlayerTextureRegion;
	}
	
	/** Enemy : ROBBER. */
	protected TiledTextureRegion mEnemyRobberTextureRegion;
	public TiledTextureRegion getEnemyRobberTextureRegion() {
		return mEnemyRobberTextureRegion;
	}
	/** Enemy : ROBBER rope. */
	protected TiledTextureRegion mEnemyRobberRopeTextureRegion;
	public TiledTextureRegion getEnemyRobberRopeTextureRegion() {
		return mEnemyRobberRopeTextureRegion;
	}
	
	/** SENTRY. */
	protected TextureRegion mSentryTextureRegion;
	public TextureRegion getSentryTextureRegion() {
		return mSentryTextureRegion;
	}
	
	/** SENTRY electric attack. */
	protected TiledTextureRegion mSentryElectricAttackTextureRegion;
	public TiledTextureRegion getSentryElectricAttackTextureRegion() {
		return mSentryElectricAttackTextureRegion;
	}
	
	/** PROJECTILE : Standard */
	protected TextureRegion mProjStdTextureRegion;
	public TextureRegion getProjStdTextureRegion() {
		return mProjStdTextureRegion;
	}
	
	
	//##############
	// MENUS
	
	/** PAUSE MENU Background. */
	protected TextureRegion mMenuPauseBackgroundTextureRegion;
	public TextureRegion getMenuPauseBackgroundTextureRegion() {
		return mMenuPauseBackgroundTextureRegion;
	}
	
	/** PAUSE MENU play button. */
	protected TextureRegion mMenuPauseButtonPlayTextureRegion;
	public TextureRegion getMenuPauseButtonPlayTextureRegion() {
		return mMenuPauseButtonPlayTextureRegion;
	}
	
	
	
	//##############
	// FONTS
	
	/** The hp font for the player. */
	private Font mPlayerHPFont;
	public Font getPlayerHPFont() {
		return mPlayerHPFont;
	}
	
	/** The hp font for enemies. */
	private Font mHPFont;
	public Font getHPFont() {
		return mHPFont;
	}
	
	

	
	/**
	 * Constructor.
	 */
	public SpriteLoader(BaseGameActivity context) {
		this.tm = context.getTextureManager();
		this.fm = context.getFontManager();
		this.context = context;
		
		init();
	}

	/**
	 * @param context
	 */
	private void init() {
		
		//Background
		BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(this.tm, 1080, 1080);
		mBackgroundRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, this.context, "background.png", 0, 0);
		backgroundTexture.load();
		
		//Wall
		BitmapTextureAtlas wallTexture = new BitmapTextureAtlas(this.tm, 1080, 40);
		mWall =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(wallTexture, this.context, "wall.png", 0, 0);
		wallTexture.load();
		
		//Left button
		BitmapTextureAtlas leftButtonTexture = new BitmapTextureAtlas(this.tm, 420, 1080);
		mArrowLeftTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(leftButtonTexture, this.context, "left_button.png", 0, 0);
		leftButtonTexture.load();
		
		//Right button
		BitmapTextureAtlas rightButtonTexture = new BitmapTextureAtlas(this.tm, 420, 1080);
		mArrowRightTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(rightButtonTexture, this.context, "right_button.png", 0, 0);
		rightButtonTexture.load();
				
		//Diamond
		BitmapTextureAtlas diamondTexture = new BitmapTextureAtlas(this.tm, 100, 100);
		mdiamondTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(diamondTexture, this.context, "diamond.png", 0, 0);
		diamondTexture.load();
		
		//Player
		BitmapTextureAtlas playerTexture = new BitmapTextureAtlas(this.tm, 64, 64);
		mPlayerTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTexture, this.context, "player.jpg", 0, 0);
		playerTexture.load();
		
		//Enemy Robber
		BitmapTextureAtlas enemyRobberTexture = new BitmapTextureAtlas(this.tm, 384, 576, TextureOptions.NEAREST);
		mEnemyRobberTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyRobberTexture, this.context, "enemy_robber_standard.png", 0, 0, 4, 6);
		enemyRobberTexture.load();
		
		//Enemy Robber Rope
		BitmapTextureAtlas enemyRobberRopeTexture = new BitmapTextureAtlas(this.tm, 512, 276, TextureOptions.NEAREST);
		mEnemyRobberRopeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyRobberRopeTexture, this.context, "enemy_robber_rope.png", 0, 0, 5, 3);
		enemyRobberRopeTexture.load();
		
		//Sentry
		BitmapTextureAtlas sentryTexture = new BitmapTextureAtlas(this.tm, 275, 275, TextureOptions.NEAREST);
		mSentryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sentryTexture, this.context, "sentry_standard.png", 0, 0);
		sentryTexture.load();
		
		//Sentry electric attack
		BitmapTextureAtlas sentryElectricAttackTexture = new BitmapTextureAtlas(this.tm, 756, 108, TextureOptions.NEAREST);
		mSentryElectricAttackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sentryElectricAttackTexture, this.context, "sentry_electric_attack.png", 0, 0, 7, 1);
		sentryElectricAttackTexture.load();

		//Standard projectile
		BitmapTextureAtlas projStdTexture = new BitmapTextureAtlas(this.tm, 4, 12, TextureOptions.NEAREST);
		mProjStdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(projStdTexture, this.context, "projectile_std.png", 0, 0);
		projStdTexture.load();

		
		
		
		//############
		// Menus
		
		//Sentry
		BitmapTextureAtlas menuPauseBackground = new BitmapTextureAtlas(this.tm, 854, 672, TextureOptions.NEAREST);
		mMenuPauseBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPauseBackground, this.context, "menu_pause_background.png", 0, 0);
		menuPauseBackground.load();
		
		//Player
		BitmapTextureAtlas menuPauseButtonPlayTexture = new BitmapTextureAtlas(this.tm, 302, 171);
		mMenuPauseButtonPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPauseButtonPlayTexture, this.context, "menu_pause_button_play.png", 0, 0);
		menuPauseButtonPlayTexture.load();
		
		
		//############
		// Fonts
		
		//Font for player HPs
		this.mPlayerHPFont = FontFactory.create(this.fm, this.tm, 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);
		this.mPlayerHPFont.load();
		
		//Font for enemy HPs
		this.mHPFont = FontFactory.create(this.fm, this.tm, 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.RED);
		this.mHPFont.load();
	}

	/**
	 * @return the context
	 */
	public BaseGameActivity getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(BaseGameActivity context) {
		this.context = context;
	}
	
	

}
