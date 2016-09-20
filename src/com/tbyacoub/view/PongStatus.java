package com.tbyacoub.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PongStatus extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8916469809198359655L;
	
	private JLabel lblStatus = new JLabel("Not Connected...");
	
	public PongStatus() {
		add(lblStatus);
	}
	
	public void setStatus(String string){
		lblStatus.setText(string);
	}
}
