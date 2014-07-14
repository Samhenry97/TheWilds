package com.henry.wilds.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Menu extends JMenu {
	private static final long serialVersionUID = 1L;

	public Menu(String name, Object topMenuBar) {
		this(name, ' ', topMenuBar);
	}

	public Menu(String name, char mnemonic, Object topMenuBar) {
		super(name);
		if(mnemonic != ' ') {
			setMnemonic(mnemonic);
		}
		if(topMenuBar instanceof JMenuBar) {
			((JMenuBar) topMenuBar).add(this);
		} else if(topMenuBar instanceof JMenu) {
			((JMenu) topMenuBar).add(this);
		}
	}
}