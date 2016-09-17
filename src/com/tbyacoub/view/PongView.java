package com.tbyacoub.view;

import java.awt.Color;

import javax.swing.JFrame;

public class PongView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5522123021498546893L;

	public PongView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setResizable(false);
		setSize(600, 600);
		setTitle("Pong");
	}
}
