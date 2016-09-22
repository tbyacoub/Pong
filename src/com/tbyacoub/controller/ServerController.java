package com.tbyacoub.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;

import com.tbyacoub.model.GameModel;
import com.tbyacoub.model.PongBall;
import com.tbyacoub.model.PongPaddle;
import com.tbyacoub.view.ServerView;

public class ServerController {

	private java.util.Timer timer;

	private int playerID;

	private ServerSocket serverSocket;

	private ServerView serverView;
	private GameModel gameModel;
	private PongBall pongBall;

	private MonitorRequests monitorRequests;
	private MonitorClients monitorClients;
	private Thread monitorRequestsThread;
	private Thread monitorClientsThread;

	private ArrayList<Socket> clients;

	protected int panelHeight;
	protected int height;
	protected int widht;

	/**
	 * Initialize the serverController.
	 * 
	 * @param serverView
	 *            Server GUI instance.
	 * @param gameModel
	 *            Game model.
	 * @param pongBall
	 * @param height2
	 * @param width2
	 */
	public ServerController(ServerView serverView, GameModel gameModel, PongBall pongBall, int width, int height) {
		this.clients = new ArrayList<>();
		this.serverView = serverView;
		this.gameModel = gameModel;
		this.pongBall = pongBall;
		this.height = height;
		this.widht = width;

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
	public void init(int port) throws BindException, IOException {
		createSocket(port);
		monitorRequests = new MonitorRequests(this, serverSocket);
		monitorClients = new MonitorClients(this);
		monitorClientsThread = new Thread(monitorClients);
		monitorClientsThread.start();
		monitorRequestsThread = new Thread(monitorRequests);
		monitorRequestsThread.start();
	}

	/**
	 * Creates the server socket.
	 * 
	 * @param port
	 *            Port for socket to bind to.
	 */
	private void createSocket(int port) throws BindException, IOException {
		serverView.setServerStatus("Starting server at port" + port);
		serverSocket = new ServerSocket(port);
		serverView.setServerStatus("Server running at port " + port);
		serverView.setGameStatus("No players connected...");
	}

	protected synchronized ArrayList<Socket> getClients() {
		return clients;
	}

	protected void addClient(Socket clientSocket) {
		clients.add(clientSocket);
		notifyViewOfCount();
		if (clients.size() == 2) {
			monitorRequests.suspend();
			monitorClients.suspend();
			timer = new Timer();
			timer.schedule(new GameLoop(), 0, 1000 / 60);
		}
	}

	public synchronized void deleteClient(int id) {
		try {
			clients.remove(id).close();
			notifyViewOfCount();
			if (monitorClients.isSuspended()) {
				monitorClients.resume();
			}
			if (monitorRequests.isSuspended()) {
				monitorRequests.resume();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class GameLoop extends java.util.TimerTask {

		private ObjectOutputStream streamOut;
		private ObjectInputStream streamIn;

		private PongPaddle[] paddles = new PongPaddle[2];

		@Override
		public void run() {
			try {
				updateGame();
				render();
			} catch (ClassNotFoundException e) {
			} catch (SocketException | EOFException sc) {
				timer.cancel();
				deleteClient(playerID);
			} catch (IOException e) {
				e.printStackTrace();
				timer.cancel();
			}

			if (!gameModel.continueGame())
				timer.cancel();
		}

		private void updateGame() throws IOException, ClassNotFoundException {
			for (playerID = 0; playerID < clients.size(); playerID++) {
				streamOut = new ObjectOutputStream(new BufferedOutputStream(clients.get(playerID).getOutputStream()));
				streamOut.writeChar('p');
				streamOut.flush();
				streamIn = new ObjectInputStream(new BufferedInputStream(clients.get(playerID).getInputStream()));
				// SocketException when player is disconnected
				PongPaddle pongPaddle = (PongPaddle) streamIn.readObject();
				paddles[playerID] = pongPaddle;
			}
			pongBall.step(paddles[0], paddles[1], panelHeight);

		}

		private void render() throws IOException {
			for (playerID = 0; playerID < clients.size(); playerID++) {
				streamOut = new ObjectOutputStream(new BufferedOutputStream(clients.get(playerID).getOutputStream()));
				streamOut.writeChar('r');
				streamOut.writeObject(paddles[(playerID + 1) % 2]);
				streamOut.writeObject(pongBall);
				streamOut.flush();
			}
		}

	}

}
