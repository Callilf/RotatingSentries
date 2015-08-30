/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.sprite.Sprite;

import com.callil.rotatingsentries.singleton.GameSingleton;
import com.callil.rotatingsentries.util.Couple;

/**
 * @author Callil
 * Defines that this entity is a ninja that throws shurikens. This component is used to manage the workflow of a ninja.
 */
public class EnemyNinjaComponent extends Component {

	public enum EnemyNinjaStateType {
		INITIALIZING,
		ARRIVING,
		ATTACKING,
		ATTACK_RECOVERING;
	}
	
	/**
	 * The state of the robber.
	 */
	private EnemyNinjaStateType state;
	
	/**
	 * The target towards which the robber will move.
	 */
	private Sprite target;
	
	/** The position at which the robber will arrive in climbing and start walking. */
	private Couple<Float> arrivingPosition;
	
	/** The duration of the wait after an attack. */
	private float afterAttackDuration;
	/** The duration of the wait after a recovery. */
	private float afterRecoveryDuration;
	
	private float lastAttackTime;
	private float lastRecoverTime;

	
	/**
	 * Constructor.
	 */
	public EnemyNinjaComponent(Sprite target, float afterAttackDuration, float afterRecoverDuration) {
		this.state = EnemyNinjaStateType.INITIALIZING;
		this.target = target;
		this.afterAttackDuration = afterAttackDuration;
		this.afterRecoveryDuration = afterRecoverDuration;
	}

	
	public void attack() {
		lastAttackTime = GameSingleton.getInstance().getTotalTime();
	}
	public boolean canAttack() {
		return GameSingleton.getInstance().getTotalTime() > lastAttackTime + afterAttackDuration;
	}
	public void recover() {
		lastRecoverTime = GameSingleton.getInstance().getTotalTime();
	}
	public boolean canRecover() {
		return GameSingleton.getInstance().getTotalTime() > lastRecoverTime + afterRecoveryDuration;
	}
	
	// Get Set
	
	public EnemyNinjaStateType getState() {
		return state;
	}

	public void setState(EnemyNinjaStateType state) {
		this.state = state;
	}

	public Sprite getTarget() {
		return target;
	}

	public void setTarget(Sprite target) {
		this.target = target;
	}

	public Couple<Float> getArrivingPosition() {
		return arrivingPosition;
	}

	public void setArrivingPosition(Couple<Float> arrivingPosition) {
		this.arrivingPosition = arrivingPosition;
	}

	public float getAfterAttackDuration() {
		return afterAttackDuration;
	}

	public void setAfterAttackDuration(float afterAttackDuration) {
		this.afterAttackDuration = afterAttackDuration;
	}

	public float getAfterRecoveryDuration() {
		return afterRecoveryDuration;
	}

	public void setAfterRecoveryDuration(float afterRecoveryDuration) {
		this.afterRecoveryDuration = afterRecoveryDuration;
	}

	public float getLastAttackTime() {
		return lastAttackTime;
	}

	public void setLastAttackTime(float lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}

	public float getLastRecoverTime() {
		return lastRecoverTime;
	}
	
	public void setLastRecoverTime(float lastRecoverTime) {
		this.lastRecoverTime = lastRecoverTime;
	}
}
