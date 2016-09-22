package com.tbyacoub.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A Thread that monitors the incoming requests to connect to the server.
 * 
 * @author tbyacoub
 *
 */
public class MonitorRequests implements Runnable {

	private volatile boolean serverRunning = true;
	private volatile boolean pause = false;
	private ServerController serverController;
	private ObjectOutputStream streamOut;
	private ObjectInputStream streamIn;
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
		while (serverRunning) {
			try {
				synchronized (this) {
					while (pause) {
						wait();
					}
				}

				// Accept requests
				clientSocket = serverSocket.accept();

				// Send screen dimensions
				streamOut = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
				streamOut.writeInt(serverController.widht);
				streamOut.writeInt(serverController.height);
				streamOut.flush();

				// Receive panel dimensions
				streamIn = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
				int width = streamIn.readInt();
				int height = streamIn.readInt();
				serverController.panelHeight = height;

				// Calculate First player's paddle coordinates
				int paddleX = width / 60;
				int paddleY = height / 2;

				// Calculate Second player's paddle coordinates
				if (serverController.getClients().size() == 1) {
					paddleX = width - paddleX - 10;
				}

				// Send paddle coordinates
				streamOut = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
				streamOut.writeInt(paddleX);
				streamOut.writeInt(paddleY);
				streamOut.flush();

				// Add Client to list
				serverController.addClient(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void suspend() {
		pause = true;
	}

	protected synchronized void resume() {
		pause = false;
		notify();
	}

	protected boolean isSuspended() {
		return pause;
	}
}
