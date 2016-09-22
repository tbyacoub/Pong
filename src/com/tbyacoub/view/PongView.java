package com.tbyacoub.view;

import javax.swing.JFrame;

public class PongView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5522123021498546893L;

	private PongPanel pongPanel = new PongPanel();

	public PongView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Pong");
		add(pongPanel);
	}

	public int getHeight() {
		return pongPanel.getHeight();
	}

	public int getWidth() {
		return pongPanel.getWidth();
	}

	public void render(int p1x, int p1y, int p1w, int p1h, int p2x, int p2y, int p2w, int p2h, int bx, int by, int bd) {
		pongPanel.render(p1x, p1y, p1w, p1h, p2x, p2y, p2w, p2h, bx, by, bd);
	}

	public void render(int p1x, int p1y, int p1w, int p1h) {
		pongPanel.render(p1x, p1y, p1w, p1h);
	}
}
