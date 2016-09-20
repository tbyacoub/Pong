package com.tbyacoub.controller;

import java.net.Socket;
import java.util.ArrayList;

public class MonitorClients implements Runnable{
	
	ServerController serverController;
	ArrayList<Socket> clients;
	
	public MonitorClients(ServerController serverController) {
		this.serverController = serverController;
		clients = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			if (serverController.clientAdded) {
				clients = serverController.getClients();
				serverController.clientAdded = false;
			} else {
				for (int i = 0; i < clients.size(); i++) {
					Socket currentSocket = clients.get(i);
					if (currentSocket.isClosed()) {
						serverController.deleteClient(currentSocket);
					}
				}
			}
		}
	}

}
