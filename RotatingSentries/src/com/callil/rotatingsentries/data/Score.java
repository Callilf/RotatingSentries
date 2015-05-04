package com.callil.rotatingsentries.data;

import java.io.Serializable;

/**
 * Contain a score
 * 
 * @author Thomas
 */
public class Score implements Comparable<Score>, Serializable {

	
	private static final long serialVersionUID = 5043136296174058314L;

	/** Identify a HighScore. At each new HighScore, this id is increased*/
	private long scoreId;
	
	/** The time score of the HighScore */
	private float timeScore;
	
	/** Name of the player of the HighScore*/
	private String player;
	
	// constructor by default for serialization
	public Score() {
	}
	
	/**
	 * Constructor
	 * 
	 * @param scoreId the score id. Unique id, incremented at each new score
	 * @param player name associated with the score
	 * @param timeScore the time score
	 */
	public Score(long scoreId, String player, float timeScore) {
		this.scoreId = scoreId;
		this.player = player;
		this.timeScore = timeScore;
	}	
	
	
	/**
	 * Compare the object by another one. First compare the highest timeScore, and next the lowest scoreId
	 * 
	 * @param another
	 */
	public int compareTo(Score another) {
		if (timeScore < another.timeScore) return -1; // lower
		if (timeScore > another.timeScore || scoreId < another.scoreId) return 1; // higher 
		if (scoreId == another.scoreId) return 0; // the score itself
		
		// else timeScore == another.timeScore && numberId > another.numberId
		return -1; // lower
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Score n°").append(scoreId).append("(player=").append(player).append(", time=").append(timeScore/1000f).append("s)");
		return sb.toString();
	}

	
	// GETTER AND SETTER
	
	public long getScoreId() {
		return scoreId;
	}

	public void setScoreId(long scoreId) {
		this.scoreId = scoreId;
	}

	public float getTimeScore() {
		return timeScore;
	}

	public void setTimeScore(float timeScore) {
		this.timeScore = timeScore;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
}
