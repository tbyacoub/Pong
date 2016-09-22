package com.tbyacoub.pong;

import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JOptionPane;

import com.tbyacoub.controller.PongController;
import com.tbyacoub.model.PongPaddle;
import com.tbyacoub.view.PongView;

public class PongGame {

	private static final int port = 5555;

	public static void main(String[] args) {
		PongView pongView = new PongView();
		PongPaddle pongPaddle = new PongPaddle();
		PongController pongController = new PongController(pongView, pongPaddle);
		try {
			pongController.init(port);
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(pongView, "Game Server is not running on port " + port);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(pongView, "Unexpected error has occured");
			System.exit(1);
		}
	}
}
