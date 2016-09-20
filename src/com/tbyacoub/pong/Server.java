package com.tbyacoub.pong;

import java.net.BindException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tbyacoub.controller.ServerController;
import com.tbyacoub.model.GameModel;
import com.tbyacoub.view.ServerView;

public class Server {

	private static final int port = 5555;

	public static void main(String[] args) {
		ServerView serverView = new ServerView();
		GameModel gameModel = new GameModel();
		ServerController serverController = new ServerController(serverView, gameModel);
		try {
			serverController.init(port);
			serverView.setVisible(true);
		} catch (BindException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Game Server is already running on port " + port);
		} catch (InterruptedException e) {
			System.exit(1);
		}

	}

}
