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

	/** unique instance */
	private static SpriteLoader instance = null;
	
	public static SpriteLoader getInstance() {
		if (instance == null) {
			instance = new SpriteLoader();
		}
		return instance;
	}

	/**
	 * Constructor.
	 */
	private SpriteLoader() {
	}
	
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
	
	/** SIDE GRAY*/
	protected TextureRegion mSideGrayTextureRegion;
	public TextureRegion getSideGrayTextureRegion() {
		return mSideGrayTextureRegion;
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
	
	/** PAUSE MENU restart button. */
	protected TextureRegion mMenuPauseButtonRestartTextureRegion;
	public TextureRegion getMenuPauseButtonRestartTextureRegion() {
		return mMenuPauseButtonRestartTextureRegion;
	}
	
	/** PAUSE MENU quit button. */
	protected TextureRegion mMenuPauseButtonHomeTextureRegion;
	public TextureRegion getMenuPauseButtonHomeTextureRegion() {
		return mMenuPauseButtonHomeTextureRegion;
	}
	
	//##############
	// MAIN MENUS
	
	/** MAIN MENU background. */
	protected TextureRegion mMmenuBackgroundTextureRegion;
	protected TextureRegion mMmenuTitleTextureRegion;
	protected TextureRegion mMmenuPlayBtnTextureRegion;
	protected TextureRegion mMmenuOptionsBtnTextureRegion;
	protected TextureRegion mMmenuLeaderboardBtnTextureRegion;
	protected TextureRegion mMmenuCreditsBtnTextureRegion;
	protected TextureRegion mMmenuEmptyBtnTextureRegion;

	public TextureRegion getMmenuBackgroundTextureRegion() {
		return mMmenuBackgroundTextureRegion;
	}

	public TextureRegion getMmenuTitleTextureRegion() {
		return mMmenuTitleTextureRegion;
	}

	public TextureRegion getMmenuPlayBtnTextureRegion() {
		return mMmenuPlayBtnTextureRegion;
	}

	public TextureRegion getMmenuOptionsBtnTextureRegion() {
		return mMmenuOptionsBtnTextureRegion;
	}

	public TextureRegion getMmenuLeaderboardBtnTextureRegion() {
		return mMmenuLeaderboardBtnTextureRegion;
	}

	public TextureRegion getMmenuCreditsBtnTextureRegion() {
		return mMmenuCreditsBtnTextureRegion;
	}

	public TextureRegion getMmenuEmptyBtnTextureRegion() {
		return mMmenuEmptyBtnTextureRegion;
	}
	
	//##############
	// FONTS
	


	/** The hp font for the player. */
	private Font mPlayerHPFont;
	public Font getPlayerHPFont() {
		return mPlayerHPFont;
	}
	
	/** The font for the pause menu. */
	private Font menuFont;
	public Font getMenuFont() {
		return menuFont;
	}
	
	/**
	 * @param context
	 */
	public void initGameSprite(BaseGameActivity context) {
		
//		if (mBackgroundRegion != null) {
//			return;
//		}
		
		TextureManager tm = context.getTextureManager();
		FontManager fm = context.getFontManager();
		
		//Background
		BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(tm, 1080, 1080);
		mBackgroundRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, context, "background.png", 0, 0);
		backgroundTexture.load();
		
		//Wall
		BitmapTextureAtlas wallTexture = new BitmapTextureAtlas(tm, 1080, 40);
		mWall =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(wallTexture, context, "wall.png", 0, 0);
		wallTexture.load();
		
		//Side gray
		BitmapTextureAtlas sideGrayButtonTexture = new BitmapTextureAtlas(tm, 420, 1080);
		mSideGrayTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(sideGrayButtonTexture, context, "side_gray.png", 0, 0);
		sideGrayButtonTexture.load();
		
		//Left button
		BitmapTextureAtlas leftButtonTexture = new BitmapTextureAtlas(tm, 401, 533);
		mArrowLeftTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(leftButtonTexture, context, "left_button2.png", 0, 0);
		leftButtonTexture.load();
		
		//Right button
		BitmapTextureAtlas rightButtonTexture = new BitmapTextureAtlas(tm, 401, 533);
		mArrowRightTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(rightButtonTexture, context, "right_button2.png", 0, 0);
		rightButtonTexture.load();
				
		//Diamond
		BitmapTextureAtlas diamondTexture = new BitmapTextureAtlas(tm, 100, 100);
		mdiamondTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(diamondTexture, context, "diamond.png", 0, 0);
		diamondTexture.load();
		
		//Player
		BitmapTextureAtlas playerTexture = new BitmapTextureAtlas(tm, 64, 64);
		mPlayerTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTexture, context, "player.jpg", 0, 0);
		playerTexture.load();
		
		//Enemy Robber
		BitmapTextureAtlas enemyRobberTexture = new BitmapTextureAtlas(tm, 384, 576, TextureOptions.NEAREST);
		mEnemyRobberTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyRobberTexture, context, "enemy_robber_standard.png", 0, 0, 4, 6);
		enemyRobberTexture.load();
		
		//Enemy Robber Rope
		BitmapTextureAtlas enemyRobberRopeTexture = new BitmapTextureAtlas(tm, 512, 276, TextureOptions.NEAREST);
		mEnemyRobberRopeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyRobberRopeTexture, context, "enemy_robber_rope.png", 0, 0, 5, 3);
		enemyRobberRopeTexture.load();
		
		//Sentry
		BitmapTextureAtlas sentryTexture = new BitmapTextureAtlas(tm, 275, 275, TextureOptions.NEAREST);
		mSentryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sentryTexture, context, "sentry_standard.png", 0, 0);
		sentryTexture.load();
		
		//Sentry electric attack
		BitmapTextureAtlas sentryElectricAttackTexture = new BitmapTextureAtlas(tm, 756, 108, TextureOptions.NEAREST);
		mSentryElectricAttackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sentryElectricAttackTexture, context, "sentry_electric_attack.png", 0, 0, 7, 1);
		sentryElectricAttackTexture.load();

		//Standard projectile
		BitmapTextureAtlas projStdTexture = new BitmapTextureAtlas(tm, 4, 12, TextureOptions.NEAREST);
		mProjStdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(projStdTexture, context, "projectile_std.png", 0, 0);
		projStdTexture.load();


		//############
		// Menus (pause, end)
		
		//Pause background
		BitmapTextureAtlas menuPauseBackground = new BitmapTextureAtlas(tm, 854, 672, TextureOptions.NEAREST);
		mMenuPauseBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPauseBackground, context, "menu_pause_background.png", 0, 0);
		menuPauseBackground.load();
		
		//Pause - Play button
		BitmapTextureAtlas menuPauseButtonPlayTexture = new BitmapTextureAtlas(tm, 302, 171);
		mMenuPauseButtonPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPauseButtonPlayTexture, context, "menu_pause_button_play.png", 0, 0);
		menuPauseButtonPlayTexture.load();
		
		//Pause - restart button
		BitmapTextureAtlas menuPauseButtonRestartTexture = new BitmapTextureAtlas(tm, 302, 171);
		mMenuPauseButtonRestartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPauseButtonRestartTexture, context, "menu_pause_button_restart.png", 0, 0);
		menuPauseButtonRestartTexture.load();
		
		//Pause - Home button
		BitmapTextureAtlas menuPauseButtonHomeTexture = new BitmapTextureAtlas(tm, 302, 171);
		mMenuPauseButtonHomeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPauseButtonHomeTexture, context, "menu_pause_button_home.png", 0, 0);
		menuPauseButtonHomeTexture.load();
		
		
		//############
		// Fonts
		
		//Font for player HPs
		this.mPlayerHPFont = FontFactory.create(fm, tm, 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 64);
		this.mPlayerHPFont.load();
		
		//Font for enemy HPs
		this.menuFont = FontFactory.create(fm, tm, 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 72, Color.BLACK);
		this.menuFont.load();
		
	}
	
	public void initMenuSprite(BaseGameActivity context) {
		
//		if (mMmenuBackgroundTextureRegion != null) {
//			return;
//		}

		//############
		// Main menu
		TextureManager tm = context.getTextureManager();
		
		//Background
		BitmapTextureAtlas mmenuBackgroundTexture = new BitmapTextureAtlas(tm, 1920, 1080);
		mMmenuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuBackgroundTexture, context, "mainmenu/menu_background.png", 0, 0);
		mmenuBackgroundTexture.load();
		
		//Title
		BitmapTextureAtlas mmenuTitleTexture = new BitmapTextureAtlas(tm, 1250, 360);
		mMmenuTitleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuTitleTexture, context, "mainmenu/menu_main_title.png", 0, 0);
		mmenuTitleTexture.load();
		
		//Play button
		BitmapTextureAtlas mmenuPlayBtnTexture = new BitmapTextureAtlas(tm, 1920, 1080);
		mMmenuPlayBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuPlayBtnTexture, context, "mainmenu/menu_play_btn.png", 0, 0);
		mmenuPlayBtnTexture.load();
		
		//Options button
		BitmapTextureAtlas mmenuOptionsBtnTexture = new BitmapTextureAtlas(tm, 1920, 1080);
		mMmenuOptionsBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuOptionsBtnTexture, context, "mainmenu/menu_options_btn.png", 0, 0);
		mmenuOptionsBtnTexture.load();
		
		//Hot to play button
		BitmapTextureAtlas mmenuLeaderboadBtnTexture = new BitmapTextureAtlas(tm, 1920, 1080);
		mMmenuLeaderboardBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuLeaderboadBtnTexture, context, "mainmenu/menu_leaderboard_btn.png", 0, 0);
		mmenuLeaderboadBtnTexture.load();
		
		//Credits button
		BitmapTextureAtlas mmenuCreditsBtnTexture = new BitmapTextureAtlas(tm, 1920, 1080);
		mMmenuCreditsBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuCreditsBtnTexture, context, "mainmenu/menu_credits_btn.png", 0, 0);
		mmenuCreditsBtnTexture.load();
		
		//Empty button
		BitmapTextureAtlas mmenuEmptyBtnTexture = new BitmapTextureAtlas(tm, 1920, 1080);
		mMmenuEmptyBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mmenuEmptyBtnTexture, context, "mainmenu/menu_empty_btn.png", 0, 0);
		mmenuEmptyBtnTexture.load();
	}
	

}
