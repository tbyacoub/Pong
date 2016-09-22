package com.tbyacoub.pong;

import java.io.IOException;
import java.net.BindException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tbyacoub.controller.ServerController;
import com.tbyacoub.model.GameModel;
import com.tbyacoub.model.PongBall;
import com.tbyacoub.view.ServerView;

public class Server {

	private static final int port = 5555;
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;

	public static void main(String[] args) {
		ServerView serverView = new ServerView();
		GameModel gameModel = new GameModel();
		PongBall pongBall = new PongBall(WIDTH/2, HEIGHT/2);
		ServerController serverController = new ServerController(serverView, gameModel, pongBall, WIDTH, HEIGHT);
		try {
			serverController.init(port);
			serverView.setVisible(true);
		} catch (BindException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Game Server is already running on port " + port);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Unexpected Error has occured exiting game");
			System.exit(1);
		}
	}

}
