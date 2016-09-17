package com.tbyacoub.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tbyacoub.view.PongView;

public class PongController {

	private PongView pongView;

	private Socket socket;

	private boolean connected = false;
	
	public PongController(PongView pongView) {
		this.pongView = pongView;
	}

	public void connectToServer(int port) {
		try {
			socket = new Socket("localhost", port);
			connected = true;
		} catch (ConnectException CONNE) {
			connected = false;
			JOptionPane.showMessageDialog(new JFrame(), "Game Server is not running on port " + port);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public boolean isConnected(){
		return connected;
	}

}
