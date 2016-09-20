package com.tbyacoub.controller;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.tbyacoub.model.GameModel;
import com.tbyacoub.view.ServerView;

public class ServerController implements Runnable {

	private ServerSocket serverSocket;

	private ServerView serverView;
	private GameModel gameModel;

	private Thread monitorRequests;
	private Thread monitorClients;
	private Thread serverThread;

	private ArrayList<Socket> clients;
	protected volatile boolean clientAdded = false;

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
		clients = new ArrayList<>();
	}

	private void notifyViewOfCount() {
		serverView.setGameStatus(clients.size() + " Players connected...");
	}

	/**
	 * Creates the server.
	 * 
	 * @param port
	 *            binding port for server socket.
	 * @throws BindException
	 *             Another Server is running on the same port.
	 * @throws InterruptedException
	 */
	public void init(int port) throws BindException, InterruptedException {
		createSocket(port);
		monitorClients = new Thread(new MonitorClients(this));
		monitorClients.start();
		monitorRequests = new Thread(new MonitorRequests(this, serverSocket));
		monitorRequests.start();
		serverThread = new Thread(this);
		serverThread.start();
	}

	/**
	 * Creates the server socket.
	 * 
	 * @param port
	 *            Port for socket to bind to.
	 */
	private void createSocket(int port) throws BindException {
		try {
			serverView.setServerStatus("Starting server at port" + port);
			serverSocket = new ServerSocket(port);
			serverView.setServerStatus("Server running at port " + port);
			serverView.setGameStatus("No players connected...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected synchronized ArrayList<Socket> getClients() {
		return clients;
	}

	protected synchronized void addClient(Socket clientSocket) {
		if (clients.size() <= 2) {
			clients.add(clientSocket);
			clientAdded = true;
			notifyViewOfCount();
		}
	}

	protected synchronized void deleteClient(Socket clientSocket) {
		clients.remove(clientSocket);
		notifyViewOfCount();
	}

	private boolean ready() {
		while (clients.size() < 2) {
			System.out.println("Waiting for players to connect...");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void run() {
		while (ready() && gameModel.continueGame()) {
			System.out.println("game running...");
		}
	}

}
