package com.henry.wilds.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class contains basic functions for use of the program.
 * Some of these are setting the look of the windows, 
 * and sleeping threads.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class Util {
	
	/**
	 * This method sets the look and feel of all the windows
	 * to the operating system's default look and feel.
	 */
	public static void setLookAndFeelToSystem() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * This method loads an image from a specified path,
	 * then returns the image to the function caller.
	 * @param path The path where the image is located
	 * @return The image to get
	 */
	public static BufferedImage getImage(String path) {
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(Util.class.getResourceAsStream(Constants.IMAGE_DIR + path));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Successfully loaded image: " + Constants.IMAGE_DIR + path);
		
		return img;
	}
	
	/**
	 * Loads an image directly from a file
	 * @param file The file to load from
	 * @return The images from the file
	 */
	public static BufferedImage getImage(File file) {
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		BufferedImage resized = new BufferedImage(Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT, img.getType());
		Graphics2D g = resized.createGraphics();
		g.drawImage(img, 0, 0, Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT, null);
		g.dispose();
		
		System.out.println("Successfully loaded image: " + file.getAbsolutePath());
		
		return resized;
	}
	
	/**
	 * Sleeps the thread that this function is called in for
	 * a specified amount of time
	 * @param millis Time to sleep in milliseconds
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
