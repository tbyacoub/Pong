package com.tbyacoub.controller;

import java.io.IOException;
import java.net.ServerSocket;

import com.tbyacoub.model.GameModel;
import com.tbyacoub.view.ServerView;

public class ServerController {
	
	private ServerSocket serverSocket;
	
	private ServerView serverView;
	private GameModel gameModel;

	public ServerController(ServerView serverView, GameModel gameModel) {
		this.serverView = serverView;
		this.gameModel = gameModel;
	}
	

	public void runServer(int port) throws InterruptedException {
		try {
			serverView.setServerStatus("Starting server at port" + port);
			Thread.sleep(2000);
			serverSocket = new ServerSocket(port);
			serverView.setServerStatus("Server running at port " + port);
			serverView.setGameStatus("Waiting for players to connect...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
