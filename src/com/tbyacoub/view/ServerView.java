package com.tbyacoub.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3893443982928741527L;
	
	private JLabel serverStatus;
	private JLabel gameStatus;

	public ServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 1));
		setTitle("Pong Server");
		setResizable(false);
		setSize(300, 100);
		
		serverStatus = new JLabel();
		gameStatus = new JLabel("-");
		add(serverStatus);
		add(gameStatus);
	}

	public void setServerStatus(String string) {
		serverStatus.setText(string);		
	}
	
	public void setGameStatus(String string) {
		gameStatus.setText(string);
	}
}
