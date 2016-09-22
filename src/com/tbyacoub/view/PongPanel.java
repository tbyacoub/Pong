package com.tbyacoub.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PongPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5381773497686979695L;

	private int paddleX;
	private int paddleY;
	private int paddleWidth;
	private int paddleHeight;

	private int paddle2X;
	private int paddle2Y;
	private int paddle2Width;
	private int paddle2Height;

	private int ballX;
	private int ballY;
	private int ballDiameter;

	public PongPanel() {
		setBackground(Color.BLACK);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
		g.fillRect(paddle2X, paddle2Y, paddle2Width, paddle2Height);
		g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
	}

	public void render(int p1x, int p1y, int p1w, int p1h, int p2x, int p2y, int p2w, int p2h, int bx, int by, int bd) {
		paddleX = p1x;
		paddleY = p1y;
		paddleWidth = p1w;
		paddleHeight = p1h;

		paddle2X = p2x;
		paddle2Y = p2y;
		paddle2Width = p2w;
		paddle2Height = p2h;
		
		ballX = bx;
		ballY = by;
		ballDiameter = bd;
		
		repaint();
	}

	public void render(int p1x, int p1y, int p1w, int p1h) {
		paddleX = p1x;
		paddleY = p1y;
		paddleWidth = p1w;
		paddleHeight = p1h;

		paddle2X = 0;
		paddle2Y = 0;
		paddle2Width = 0;
		paddle2Height = 0;
		
		ballX = 0;
		ballY = 0;
		ballDiameter = 0;
		
		repaint();	
	}

}
