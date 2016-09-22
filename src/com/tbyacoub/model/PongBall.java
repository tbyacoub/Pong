package com.tbyacoub.model;

import java.io.Serializable;

public class PongBall implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1245911693156830265L;

	private int diameter = 20;

	private int ballXreset;
	private int ballYreset;

	private int ballX;
	private int ballY;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	public PongBall(int ballX, int ballY) {
		ballXreset = ballX;
		ballYreset = ballY;
		resetBall();
	}

	private void resetBall() {
		ballX = ballXreset;
		ballY = ballYreset;
	}

	public void step(PongPaddle paddle1, PongPaddle paddle2, int windowHeight) {
		int nextBallLeft = ballX + ballDeltaX;
		int nextBallRight = ballX + diameter + ballDeltaY;
		int nextBallTop = ballY + ballDeltaY;
		int nextBallBottom = ballY + diameter + ballDeltaY;

		int playerOneRight = paddle1.getX() + paddle1.getWidth();
		int playerOneTop = paddle1.getY();
		int playerOneBottom = paddle1.getX() + paddle1.getHeight();

		float playerTwoLeft = paddle2.getX();
		float playerTwoTop = paddle2.getY();
		float playerTwoBottom = paddle2.getY() + paddle2.getHeight();

		if (nextBallTop < 0 || nextBallBottom > windowHeight) {
			ballDeltaY *= -1;
		}

		if (nextBallLeft < playerOneRight) {
			if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
				// TODO increment player two score;
				resetBall();
			} else {
				ballDeltaX *= -1;
			}

		}

		if (nextBallRight > playerTwoLeft) {
			if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
				// TODO increment player one score
				resetBall();
			} else {
				ballDeltaX *= -1;
			}
		}

		ballX += ballDeltaX;
		ballY += ballDeltaY;
	}

	public int getBallX() {
		return ballX;
	}

	public int getBallY() {
		return ballY;
	}

	public int getDiameter() {
		return diameter;
	}
}
