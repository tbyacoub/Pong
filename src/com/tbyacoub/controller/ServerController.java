package com.tbyacoub.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.tbyacoub.model.GameModel;
import com.tbyacoub.view.ServerView;

public class ServerController {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Socket[] clients;

	private ServerView serverView;
	private GameModel gameModel;

	/**
	 * Initialize the serverController.
	 * 
	 * @param serverView
	 *            Server GUI instance.
	 * @param gameModel
	 *            Game model.
	 */
	public ServerController(ServerView serverView, GameModel gameModel) {
		this.serverView = serverView;
		this.gameModel = gameModel;
		this.clients = new Socket[2];
	}

	/**
	 * Runs the server.
	 * 
	 * @param port
	 *            binding port for server socket.
	 */
	public void runServer(int port) {
		createSocket(port);
		waitForConn();

	}

	/**
	 * Creates the server socket.
	 * 
	 * @param port
	 *            Port for socket to bind to.
	 */
	private void createSocket(int port) {
		try {
			serverView.setServerStatus("Starting server at port" + port);
			serverSocket = new ServerSocket(port);
			serverView.setServerStatus("Server running at port " + port);
			serverView.setGameStatus("No players connected...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A loop that waits for clients to connect to the game server.
	 */
	private void waitForConn() {
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				addToClients(clientSocket);
				notifyClient(clientSocket);
				notifyServer();
				if (clientsReady()) {
					// Run Game
					runGame();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adds client to the list of clients.
	 * 
	 * @param clientSocket
	 *            Client's socket
	 */
	private void addToClients(Socket clientSocket) {
		if (clients[0] == null) {
			clients[0] = clientSocket;
		} else if (clients[1] == null) {
			clients[1] = clientSocket;
		}
	}

	/**
	 * Notifies the client of the connection status.
	 * 
	 * @param clientSocket
	 *            Client to be notified.
	 */
	private void notifyClient(Socket clientSocket) {
		// TODO Auto-generated method stub

	}

	/**
	 * Checks if both client are connected.
	 * 
	 * @return true if two clients are connected, false otherwise.
	 */
	private boolean clientsReady() {
		return (clients[0] != null && clients[1] != null);
	}

	/**
	 * Notifies server about clients status.
	 */
	private void notifyServer() {
		serverView.setGameStatus(numberOfClients() + " players connected...");
	}

	private int numberOfClients(){
		int count = 0;
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] != null){
				count++;
			}
		}
		return count;
	}
	/**
	 * Runs the game loop.
	 */
	private void runGame() {
		while (true) {
			System.out.println("Game running");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
