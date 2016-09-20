package com.tbyacoub.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class PongView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5522123021498546893L;
	
	private PongStatus pongStatus = new PongStatus();

	public PongView() {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setResizable(false);
		setSize(600, 600);
		setTitle("Pong");
		
		add(pongStatus, BorderLayout.NORTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.out.println(1);
			  }
		});
	}
	
	public void setStatusOnPongStatus(String string) {
		pongStatus.setStatus(string);
	}

	
}
