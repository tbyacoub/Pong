package com.tbyacoub.model;

import java.io.Serializable;

public class PongPaddle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1483346423256970011L;

	private int paddleX;
	private int paddleY;

	private int paddleWidth = 10;
	private int paddleHeight = 50;
	private int paddleSpeed = 10;

	private int panelHeight;
	
	public int getX() {
		return paddleX;
	}

	public int getY() {
		return paddleY;
	}

	public int getWidth() {
		return paddleWidth;
	}

	public int getHeight() {
		return paddleHeight;
	}

	public void setPaddleX(int x) {
		paddleX = x;
	}

	public void setPaddleY(int y) {
		paddleY = y;
	}

	public void stepUp() {
		if (paddleY - paddleSpeed > 0) {
			paddleY -= paddleSpeed;
		}
	}

	public void stepDown() {
		if (paddleY + paddleSpeed + paddleHeight < panelHeight) {
			paddleY += paddleSpeed;
		}
	}

	public void setPanelHeight(int height) {
		panelHeight = height;
	}

}
