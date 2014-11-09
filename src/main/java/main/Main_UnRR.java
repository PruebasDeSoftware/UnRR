package main;

import gui.UnRRGUI;

import java.awt.EventQueue;

public class Main_UnRR {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnRRGUI frame = new UnRRGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
