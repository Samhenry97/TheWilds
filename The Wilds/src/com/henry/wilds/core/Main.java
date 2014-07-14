package com.henry.wilds.core;

import java.awt.EventQueue;

import com.henry.wilds.ui.WildsUI;

/**
 * This is the entry point of the whole program.
 * @author Samuel Henry
 * @since June 20, 2014
 * @version 1.0
 */
public class Main {

	/**
	 * Main or entry point of program.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WildsUI frame = new WildsUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
