/**
 * 
 */
package com.callil.rotatingsentries.generators;

import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;

import com.callil.rotatingsentries.GameActivity;
import com.callil.rotatingsentries.GameSingleton;
import com.callil.rotatingsentries.entityComponentSystem.entities.EntityFactory;

/**
 * @author Callil
 * The generator of enemies.
 */
public class EnemyGenerator implements IUpdateHandler{

	/**
	 * The game activity.
	 */
	private GameActivity gameActivity;
	/**
	 * The entity factory.
	 */
	private EntityFactory entityFactory;
	
	/** The period of generation. */
	private float generationPeriod;
	/** The time at which the last enemy was generated. */
	private float lastGenerationTime;
	
	/**
	 * 
	 */
	public EnemyGenerator(GameActivity ga, EntityFactory ef, float generationPeriod) {
		this.gameActivity = ga;
		this.entityFactory = ef;
		this.generationPeriod = generationPeriod;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		
		float totalTime = GameSingleton.getInstance().getTotalTime();
		if (totalTime > lastGenerationTime + generationPeriod) {
			lastGenerationTime = totalTime;
			
			// Generate a new enemy
			Random rand = new Random();
			int wallInt = rand.nextInt(4);
			float generatedX = 0;
			float generatedY = 0;
			float ropeAngle = 0;
			
			switch (wallInt) {
			case 0:
				 generatedY = 0;
				 ropeAngle = 0;
				 generatedX = gameActivity.background.getX() + rand.nextInt((int) (gameActivity.background.getWidth() - 96.0 - 30.0)) + 30; 
				break;
			case 1:
				generatedX = gameActivity.background.getX() + gameActivity.background.getWidth() - 96;
				ropeAngle = 90;
				generatedY = gameActivity.background.getY() + rand.nextInt((int) (gameActivity.background.getHeight() - 96.0 - 30.0)) + 30; 
				break;
			case 2:
				generatedY = gameActivity.background.getY() + gameActivity.background.getHeight() - 96;
				ropeAngle = 180;
				generatedX = gameActivity.background.getX() + rand.nextInt((int) (gameActivity.background.getWidth() - 96.0 - 30.0)) + 30; 
				break;
			case 3:
				generatedX = gameActivity.background.getX();
				ropeAngle = 270;
				generatedY = gameActivity.background.getY() + rand.nextInt((int) (gameActivity.background.getHeight() - 96.0 - 30.0)) + 30; 
				break;
				default:
			}
			
			
			this.entityFactory.generateRobber(generatedX, generatedY, 2, gameActivity.diamond, ropeAngle);

			
			
		}
		
	}
	
	

	@Override
	public void reset() {}

	
	
	
	public float getGenerationPeriod() {
		return generationPeriod;
	}

	public void setGenerationPeriod(float generationPeriod) {
		this.generationPeriod = generationPeriod;
	}

}
