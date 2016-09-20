package com.tbyacoub.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tbyacoub.view.PongView;

public class PongController implements Runnable {

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

	public boolean isConnected() {
		return connected;
	}

	@Override
	public void run() {
		ObjectInputStream streamIn = null;
		while (true) {
			try {
				streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				if (streamIn.readChar() == 's') {
					byte[] status = new byte[streamIn.readInt()];
					streamIn.readFully(status, 0, status.length);
					pongView.setStatusOnPongStatus(new String(status));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
