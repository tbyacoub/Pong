package com.tbyacoub.pong;

import com.tbyacoub.controller.PongController;
import com.tbyacoub.view.PongView;

public class PongGame {

	public static void main(String[] args) {
		PongView pongView = new PongView();
//		PongController pongController = new PongController(pongView);
//		pongController.connectToServer(5555);
//		if (pongController.isConnected()) {
//			pongView.setVisible(true);
//			Thread connThread = new Thread(pongController);
//			connThread.start();
//			try {
//				connThread.join();
//				System.exit(0);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.exit(1);
//		}
		pongView.setVisible(true);
	}
}
