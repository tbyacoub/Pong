package com.tbyacoub.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A Thread that monitors the incoming requests to connect to the server.
 * 
 * @author tbyacoub
 *
 */
public class MonitorRequests implements Runnable {

	private ServerController serverController;
	private ServerSocket serverSocket;
	private Socket clientSocket;

	public MonitorRequests(ServerController serverController, ServerSocket serverSocket) {
		this.serverController = serverController;
		this.serverSocket = serverSocket;
	}

	/**
	 * A loop that waits for clients to connect to the game server.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				serverController.addClient(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
