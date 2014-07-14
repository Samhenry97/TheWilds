package com.henry.wilds.ui.menu;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.henry.wilds.ui.TablePopupMenu;

/**
 * The class for a JMenuItem inside a JPopupMenu
 * or a JMenu. It has easier constructors than the
 * actual java constructors.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class MenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a MenuItem with the specified name as
	 * part of a menu
	 * @param name The name of the MenuItem
	 * @param topMenu The menu to add the MenuItem to
	 */
	public MenuItem(String name, Object topMenu) {
		this(name, ' ', null, topMenu);
	}
	
	/**
	 * Creates a MenuItem with the specified name and mnemonic
	 * as part of a menu
	 * @param name The name of the MenuItem
	 * @param mnemonic The mnemonic of the MenuItem
	 * @param topMenu the menu to add the MenuItem to
	 */
	public MenuItem(String name, char mnemonic, Object topMenu) {
		this(name, mnemonic, null, topMenu);
	}
	
	/**
	 * Creates a MenuItem with the specified name and accelerator
	 * as part of a menu
	 * @param name The name of the MenuItem
	 * @param accelerator The accelerator of the MenuItem
	 * @param topMenu The menu to add the MenuItem to
	 */
	public MenuItem(String name, String accelerator, Object topMenu) {
		this(name, ' ', accelerator, topMenu);
	}
	
	/**
	 * Creates a MenuItem with the specified name, accelerator,
	 * and mnemonic as part of a menu.
	 * @param name The name of the MenuItem
	 * @param mnemonic The mnemonic of the MenuItem
	 * @param accelerator The accelerator of the MenuItem
	 * @param topMenu The menu to add the MenuItem to
	 */
	public MenuItem(String name, char mnemonic, String accelerator, Object topMenu) {
		super(name);
		if(mnemonic != ' ') {
			setMnemonic(mnemonic);
		}
		if(accelerator != null) {
			setAccelerator(KeyStroke.getKeyStroke(accelerator));
		}
		if(topMenu instanceof Menu) {
			((Menu) topMenu).add(this);
		} else if(topMenu instanceof TablePopupMenu) {
			((TablePopupMenu) topMenu).add(this);
		}
	}
}