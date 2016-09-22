package com.tbyacoub.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class MonitorClients implements Runnable {

	private volatile boolean serverRunning = true;
	private ServerController serverController;
	private volatile boolean pause = false;
	private ObjectOutputStream streamOut;
	private ObjectInputStream streamIn;
	private int playerID;

	public MonitorClients(ServerController serverController) {
		this.serverController = serverController;
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
				for (playerID = 0; playerID < serverController.getClients().size(); playerID++) {
					Socket clientSocket = serverController.getClients().get(playerID);
					streamOut = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
					streamOut.writeChar('c');
					streamOut.flush();

					streamIn = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
					streamIn.readChar();
				}

			} catch (SocketException | EOFException sc) {
				serverController.deleteClient(playerID);
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
