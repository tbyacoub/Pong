package com.tbyacoub.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tbyacoub.model.PongBall;
import com.tbyacoub.model.PongPaddle;
import com.tbyacoub.view.PongView;

public class PongController {

	private ObjectOutputStream streamOut = null;
	private ObjectInputStream streamIn = null;
	private PongPaddle myPongPaddle;
	private PongView pongView;
	private Socket socket;

	private Thread pongControllerThread;

	public PongController(PongView pongView, PongPaddle pongPaddle) {
		this.pongView = pongView;
		this.pongView.addWindowListener(new WindowCloseListener());
		this.pongView.addKeyListener(new MovmentListener());
		this.myPongPaddle = pongPaddle;
	}

	public void init(int port) throws IOException {
		socket = new Socket("localhost", port);
		streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		pongView.setSize(streamIn.readInt(), streamIn.readInt());
		pongView.setVisible(true);
		streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		myPongPaddle.setPanelHeight(pongView.getHeight());
		streamOut.writeInt(pongView.getWidth());
		streamOut.writeInt(pongView.getHeight());
		streamOut.flush();
		streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		myPongPaddle.setPaddleX(streamIn.readInt());
		myPongPaddle.setPaddleY(streamIn.readInt());
		pongView.render(myPongPaddle.getX(), myPongPaddle.getY(), myPongPaddle.getWidth(), myPongPaddle.getHeight());
		pongControllerThread = new Thread(new PongThread());
		pongControllerThread.start();
	}

	private class PongThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
					switch (streamIn.readChar()) {
					case 'p':
						streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
						streamOut.writeObject(myPongPaddle);
						streamOut.flush();
						break;
					case 'r':
						renderPaddles(streamIn.readObject(), streamIn.readObject());
						break;
					case 'c':
						streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
						streamOut.writeChar('c');
						streamOut.flush();
					}
				} catch (IOException e) {

				} catch (ClassNotFoundException e) {
					System.out.println("casting failed...");
				}
			}
		}

		private void renderPaddles(Object paddle, Object ball) {
			PongPaddle otherPaddle = (PongPaddle) paddle;
			PongBall pongBall = (PongBall) ball;
			pongView.render(myPongPaddle.getX(), myPongPaddle.getY(), myPongPaddle.getWidth(), myPongPaddle.getHeight(),
					otherPaddle.getX(), otherPaddle.getY(), otherPaddle.getWidth(), otherPaddle.getHeight(),
					pongBall.getBallX(), pongBall.getBallY(), pongBall.getDiameter());
		}
	}

	private class WindowCloseListener extends WindowAdapter {

		public void windowClosing(WindowEvent e) {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(new JFrame(), "Could not close socket " + socket);
				}
			}
		}
	}

	private class MovmentListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				myPongPaddle.stepUp();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				myPongPaddle.stepDown();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

}
