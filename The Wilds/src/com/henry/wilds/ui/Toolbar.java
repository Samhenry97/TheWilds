package com.henry.wilds.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;

/**
 * This class is the toolbar with buttons that is
 * on the top of the main frame
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class Toolbar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JToolBar file = new JToolBar();
		private JButton fileOpen = new JButton(new ImageIcon(Images.OPEN_ICON));
		private JButton fileNew = new JButton(new ImageIcon(Images.NEW_ICON));
		private JButton fileSave = new JButton(new ImageIcon(Images.SAVE_ICON));
		private JButton fileSaveAs = new JButton(new ImageIcon(Images.SAVE_AS_ICON));
	private JToolBar add = new JToolBar();
		private JButton addTeam = new JButton(new ImageIcon(Images.ADD_TEAM_ICON));
		private JButton addCouns = new JButton(new ImageIcon(Images.ADD_COUNS_ICON));
	private JToolBar edit = new JToolBar();
		private JButton editTeam = new JButton(new ImageIcon(Images.EDIT_TEAM_ICON));
		private JButton editCouns = new JButton(new ImageIcon(Images.EDIT_COUNS_ICON));
	private JToolBar zoom = new JToolBar();
		private JButton zoomOut = new JButton(new ImageIcon(Images.ZOOM_OUT_ICON));
		private JButton zoomIn = new JButton(new ImageIcon(Images.ZOOM_IN_ICON));
		private JButton zoomReset = new JButton(new ImageIcon(Images.ZOOM_RESET_ICON));
	private JToolBar clipboard = new JToolBar();
		private JButton cut = new JButton(new ImageIcon(Images.CUT_ICON));
		private JButton copy = new JButton(new ImageIcon(Images.COPY_ICON));
		private JButton paste = new JButton(new ImageIcon(Images.PASTE_ICON));
	private JToolBar rand = new JToolBar();
		private JButton randomize = new JButton(new ImageIcon(Images.RANDOMIZE));
		
	public Toolbar(final WildsUI frame) {
		
		//New tool bar
		{
			fileNew.setToolTipText("New Wilds Project");
			fileNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.newFile();
				}
			});
			file.add(fileNew);
			
			fileOpen.setToolTipText("Open file...");
			fileOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.openFile();
				}
			});
			file.add(fileOpen);
			
			fileSave.setToolTipText("Save Project");
			fileSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.saveChanges();
				}
			});
			file.add(fileSave);
			
			fileSaveAs.setToolTipText("Save As...");
			fileSaveAs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.saveAs();
				}
			});
			file.add(fileSaveAs);
		}
		
		//Add tool bar
		{
			addTeam.setToolTipText("Add team");
			addTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.addTeam.setVisible();
				}
			});
			add.add(addTeam);
			
			addCouns.setToolTipText("Add counselor");
			addCouns.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.addCouns.setVisible();
				}
			});
			add.add(addCouns);
		}
		
		//Edit tool bar
		{
			editTeam.setToolTipText("Edit teams");
			editTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.editTeam.setVisible();
				}
			});
			edit.add(editTeam);
			
			editCouns.setToolTipText("Edit counselors");
			editCouns.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.editCouns.setVisible();
				}
			});
			edit.add(editCouns);
		}
		
		//Zoom tool bar
		{
			zoomOut.setToolTipText("Zoom out");
			zoomOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectView pv = frame.getProjView();
					
					pv.zoomOut();
				}
			});
			zoom.add(zoomOut);
			
			zoomIn.setToolTipText("Zoom in");
			zoomIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectView pv = frame.getProjView();
					
					pv.zoomIn();
				}
			});
			zoom.add(zoomIn);
			
			zoomReset.setToolTipText("Reset zoom");
			zoomReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectView pv = frame.getProjView();
					
					pv.resetZoom();
				}
			});
			zoom.add(zoomReset);
		}
		
		//Clipboard tool bar
		{
			cut.setToolTipText("Cut");
			cut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getProjView().cut();
				}
			});
			clipboard.add(cut);
			
			copy.setToolTipText("Copy");
			copy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getProjView().copy();
				}
			});
			clipboard.add(copy);
			
			paste.setToolTipText("Paste");
			paste.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getProjView().paste();
				}
			});
			clipboard.add(paste);
		}
		
		//Randomize tool bar
		{
			randomize.setToolTipText("Shuffle Tables");
			randomize.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getProjView().randomizeCounselors();
				}
			});
			rand.add(randomize);
		}
		
		add(file);
		add(add);
		add(edit);
		add(zoom);
		add(clipboard);
		add(rand);
		
		setBackgroundColor(Constants.BACKGROUND);
		setFontColor(Constants.FONT_COLOR);
	}
	
	public void setBackgroundColor(Color color) {
		setBackground(color);
		
		file.setBackground(color);
		fileNew.setBackground(color);
		fileSave.setBackground(color);
		fileOpen.setBackground(color);
		fileSaveAs.setBackground(color);
		add.setBackground(color);
		addTeam.setBackground(color);
		addCouns.setBackground(color);
		edit.setBackground(color);
		editTeam.setBackground(color);
		editCouns.setBackground(color);
		zoom.setBackground(color);
		zoomOut.setBackground(color);
		zoomIn.setBackground(color);
		zoomReset.setBackground(color);
		clipboard.setBackground(color);
		cut.setBackground(color);
		copy.setBackground(color);
		paste.setBackground(color);
		randomize.setBackground(color);
		rand.setBackground(color);
	}
	
	public void setFontColor(Color color) {
		
	}
}