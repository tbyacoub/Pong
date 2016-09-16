package com.tbyacoub.pong;

import com.tbyacoub.controller.ServerController;
import com.tbyacoub.model.GameModel;
import com.tbyacoub.view.ServerView;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		ServerView serverView = new ServerView();
		GameModel gameModel = new GameModel();
		ServerController serverController = new ServerController(serverView, gameModel);
		
		serverView.setVisible(true);
		serverController.runServer(5555);
	}
	

}
