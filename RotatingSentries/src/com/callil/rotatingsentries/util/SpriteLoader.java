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
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
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
		
		//Player
		BitmapTextureAtlas playerTexture = new BitmapTextureAtlas(this.tm, 64, 64);
		mPlayerTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTexture, this.context, "player.jpg", 0, 0);
		playerTexture.load();
		
		//Enemy Robber
		BitmapTextureAtlas enemyRobberTexture = new BitmapTextureAtlas(this.tm, 384, 192, TextureOptions.NEAREST);
		mEnemyRobberTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyRobberTexture, this.context, "enemy_robber_standard.png", 0, 0, 4, 2);
		//mEnemyRobberTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(enemyRobberTexture, this.context, "enemy_robber_standard.png", 0, 0);
		enemyRobberTexture.load();
		
		
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
