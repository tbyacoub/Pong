package com.tbyacoub.model;

public class GameModel {
	
	private final int scoreLimit = 5;
	
	private int playerOneScore = 0;
	private int playerTwoScore = 0;
	
	public int getPlayerOneScore(){
		return playerOneScore;
	}
	
	public int getPLayerTwoScore(){
		return playerTwoScore;
	}
	
	public boolean continueGame(){
		return !(playerOneScore == scoreLimit || playerTwoScore == scoreLimit);
	}
}
