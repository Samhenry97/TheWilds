package com.henry.wilds.ui.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;
import com.henry.wilds.util.Util;

/**
 * This is the dialog for editing the global preferences of
 * program. 
 * @author Samuel Henry
 * @version 1.0
 * @since June 24, 2014
 */
public class GlobalPreferences extends WildsDialog {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblProjectViewWidth = new JLabel("Project View Width:");
	private JLabel lblProjectViewHeight = new JLabel("Project View Height:");
	private JLabel lblProjectViewFont = new JLabel("Project View Font:");
	private JLabel lblDefaultTableColor = new JLabel("Table Color:");
	private JLabel lblProjectViewBackground = new JLabel("Project View Background Color:");
	private JLabel lblTableSpeed = new JLabel("Table speed:");
	private JLabel lblTableWidth = new JLabel("Table Width:");
	private JLabel lblTableHeight = new JLabel("Table Height:");
	private JLabel lblMainFontColor = new JLabel("Main Font Color:");
	private JLabel lblMainBackgroundColor = new JLabel("Main Background Color:");
	private JLabel lblTableImage = new JLabel("Table Image:");
	private JLabel lblImage = new JLabel(new ImageIcon(Images.TABLE_IMAGE));
	private JSpinner pvWidthSpinner = new JSpinner();
	private JSpinner pvHeightSpinner = new JSpinner();
	private JSpinner tSpeedSpinner = new JSpinner();
	private JSpinner tWidthSpinner = new JSpinner();
	private JSpinner tHeightSpinner = new JSpinner();
	private JButton btnApply = new JButton("Apply");
	private JButton btnChooseTableColor = new JButton("Choose Color...");
	private JButton btnBackgroundColor = new JButton("Choose Color...");
	private JButton btnFontColor = new JButton("Choose Color...");
	private JButton btnBackColor = new JButton("Choose Color...");
	private JButton btnChooseImage = new JButton("Choose Image...");
	private JButton btnUseDefault = new JButton("Use Default");
	private JEditorPane tableColor = new JEditorPane();
	private JEditorPane backgroundColor = new JEditorPane();
	private JEditorPane fontColor = new JEditorPane();
	private JEditorPane backColor = new JEditorPane();
	private JCheckBox boxShowInfo = new JCheckBox("Show Additional Info (i.e. fps)");
	
	private Color curTableColor = Color.WHITE;
	private Color curPVBackColor = Color.WHITE;
	private Color curBackColor = Color.WHITE;
	private Color curFontColor = Color.WHITE;
	
	private Font curPVFont;
	
	private JFileChooser imgChooser = new JFileChooser();

	/**
	 * Creates the dialog in relation to the WildsUI main frame.
	 * @param frame The main program frame
	 */
	public GlobalPreferences(WildsUI frame, ProjectView pv) {
		super(frame, pv);
		
		createPanel();
	}
	
	/**
	 * Apply the changes made in the dialog
	 */
	public void applyChanges() {
		Constants.PROJ_VIEW_WIDTH = (Integer) pvWidthSpinner.getValue();
		Constants.PROJ_VIEW_HEIGHT = (Integer) pvHeightSpinner.getValue();
		Constants.TABLE_COLOR = curTableColor;
		Constants.PROJ_VIEW_BACK_COLOR = curPVBackColor;
		Constants.TABLE_SPEED = (Integer) tSpeedSpinner.getValue();
		Constants.TABLE_WIDTH = (Integer) tWidthSpinner.getValue();
		Constants.TABLE_HEIGHT = (Integer) tHeightSpinner.getValue();
		Constants.SHOW_ADDITIONAL_INFO = boxShowInfo.isSelected();
		Constants.PROJ_VIEW_FONT = curPVFont;
		
		if(Constants.FONT_COLOR != curFontColor) {
			Constants.FONT_COLOR = curFontColor;
			
			mainFrame.setFontColor(Constants.FONT_COLOR);
		}
		if(Constants.BACKGROUND != curBackColor && !(curBackColor.getRed() < 50 && curBackColor.getBlue() < 50 && curBackColor.getGreen() < 50)) {
			Constants.BACKGROUND = curBackColor;
			
			Constants.DARK_BACKGROUND = curBackColor.darker().darker();			
			
			mainFrame.setBackgroundColor(Constants.BACKGROUND);
		}
	}
	
	/**
	 * Sets the dialog visible, setting the
	 * values of the components to what they
	 * need to be
	 */
	public void setVisible() {
		pvWidthSpinner.setValue(Constants.PROJ_VIEW_WIDTH);
		pvHeightSpinner.setValue(Constants.PROJ_VIEW_HEIGHT);
		tSpeedSpinner.setValue(Constants.TABLE_SPEED);
		tHeightSpinner.setValue(Constants.TABLE_HEIGHT);
		tWidthSpinner.setValue(Constants.TABLE_WIDTH);
		curTableColor = Constants.TABLE_COLOR;
		tableColor.setBackground(curTableColor);
		curPVBackColor = Constants.PROJ_VIEW_BACK_COLOR;
		backgroundColor.setBackground(curPVBackColor);
		curBackColor = Constants.BACKGROUND;
		backColor.setBackground(curBackColor);
		curFontColor = Constants.FONT_COLOR;
		fontColor.setBackground(curFontColor);
		boxShowInfo.setSelected(Constants.SHOW_ADDITIONAL_INFO);
		
		super.setVisible();
	}
	
	/**
	 * Creates the dialog and all its components
	 */
	public void createPanel() {
		setTitle("Global Preferences");
		setSize(500, 600);
		setLocationRelativeTo(getParent());
		setIconImage(Images.G_PREFS_ICON);
		contentPanel.setLayout(null);
		
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Image files *(.jpg, .jpeg, .gif, .png)", "jpg", "jpeg", "gif", "png");
		imgChooser.setFileFilter(ff);		
		
		lblProjectViewWidth.setBounds(32, 34, 94, 14);
		contentPanel.add(lblProjectViewWidth);
		
		SpinnerModel widthModel = new SpinnerNumberModel(Constants.PROJ_VIEW_MIN_WIDTH, Constants.PROJ_VIEW_MIN_WIDTH, Constants.PROJ_VIEW_MAX_WIDTH, 1);
		pvWidthSpinner.setModel(widthModel);
		pvWidthSpinner.setBounds(32, 52, Constants.SPINNER_WIDTH, 20);
		contentPanel.add(pvWidthSpinner);
		
		lblProjectViewHeight.setBounds(32, 83, 109, 14);
		contentPanel.add(lblProjectViewHeight);
		
		SpinnerModel heightModel = new SpinnerNumberModel(Constants.PROJ_VIEW_MIN_HEIGHT, Constants.PROJ_VIEW_MIN_HEIGHT, Constants.PROJ_VIEW_MAX_HEIGHT, 1);
		pvHeightSpinner.setModel(heightModel);
		pvHeightSpinner.setBounds(32, 98, Constants.SPINNER_WIDTH, 20);
		contentPanel.add(pvHeightSpinner);
		
		lblDefaultTableColor.setBounds(32, 129, 109, 14);
		contentPanel.add(lblDefaultTableColor);
		
		tableColor.setBounds(32, 148, 20, 20);
		tableColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		tableColor.setEditable(false);
		contentPanel.add(tableColor);
		
		btnChooseTableColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curTableColor = JColorChooser.showDialog(GlobalPreferences.this, "Choose Table Color", curTableColor);
				tableColor.setBackground(curTableColor);
			}
		});
		btnChooseTableColor.setBounds(62, 148, 114, 23);
		contentPanel.add(btnChooseTableColor);
		
		lblProjectViewBackground.setBounds(32, 179, 162, 14);
		contentPanel.add(lblProjectViewBackground);
		
		backgroundColor.setBounds(32, 201, 20, 20);
		backgroundColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		backgroundColor.setEditable(false);
		contentPanel.add(backgroundColor);
		
		btnBackgroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curPVBackColor = JColorChooser.showDialog(GlobalPreferences.this, "Chooser Background Color", curPVBackColor);
				backgroundColor.setBackground(curPVBackColor);
			}
		});
		btnBackgroundColor.setBounds(62, 198, 114, 23);
		contentPanel.add(btnBackgroundColor);
		
		btnApply.setBounds(370, 490, 89, 23);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyChanges();
			}
		});
		contentPanel.add(btnApply);
		
		lblTableSpeed.setBounds(225, 129, 75, 14);
		contentPanel.add(lblTableSpeed);
		
		lblTableWidth.setBounds(225, 34, 61, 14);
		contentPanel.add(lblTableWidth);
		
		lblTableHeight.setBounds(225, 83, 75, 14);
		contentPanel.add(lblTableHeight);
		
		SpinnerModel speedModel = new SpinnerNumberModel(1, 1, Constants.TABLE_MAX_SPEED, 1);
		tSpeedSpinner.setModel(speedModel);
		tSpeedSpinner.setBounds(225, 148, Constants.SPINNER_WIDTH, 20);
		contentPanel.add(tSpeedSpinner);

		SpinnerModel tHeightModel = new SpinnerNumberModel(0, 0, Constants.TABLE_MAX_HEIGHT, 1);
		tHeightSpinner.setBounds(225, 98, 75, 20);
		tHeightSpinner.setModel(tHeightModel);
		contentPanel.add(tHeightSpinner);
		
		SpinnerModel tWidthModel = new SpinnerNumberModel(0, 0, Constants.TABLE_MAX_WIDTH, 1);
		tWidthSpinner.setBounds(225, 52, Constants.SPINNER_WIDTH, 20);
		tWidthSpinner.setModel(tWidthModel);
		contentPanel.add(tWidthSpinner);
		
		lblMainFontColor.setBounds(32, 281, 94, 14);
		contentPanel.add(lblMainFontColor);
		
		fontColor.setEditable(false);
		fontColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fontColor.setBounds(32, 301, 20, 20);
		contentPanel.add(fontColor);
		
		btnFontColor.setBackground(new Color(50, 50, 50));
		btnFontColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curFontColor = JColorChooser.showDialog(GlobalPreferences.this, "Choose Table Color", curTableColor);
				fontColor.setBackground(curFontColor);
			}
		});
		btnFontColor.setBounds(62, 298, 114, 23);
		contentPanel.add(btnFontColor);
		
		backColor.setEditable(false);
		backColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		backColor.setBounds(32, 351, 20, 20);
		contentPanel.add(backColor);
		
		btnBackColor.setBackground(new Color(50, 50, 50));
		btnBackColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curBackColor = JColorChooser.showDialog(GlobalPreferences.this, "Choose Table Color", curTableColor);
				backColor.setBackground(curBackColor);
			}
		});
		btnBackColor.setBounds(62, 348, 114, 23);
		contentPanel.add(btnBackColor);
		
		lblMainBackgroundColor.setBounds(32, 332, 120, 14);
		contentPanel.add(lblMainBackgroundColor);
		
		boxShowInfo.setBounds(29, 378, 188, 23);
		contentPanel.add(boxShowInfo);
		
		lblTableImage.setBounds(225, 281, 75, 14);
		contentPanel.add(lblTableImage);
		
		lblImage.setBounds(225, 336, 229, 128);
		contentPanel.add(lblImage);
		
		btnChooseImage.setBounds(297, 277, 128, 23);
		btnChooseImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imgChooser.showDialog(GlobalPreferences.this, "Open") == JFileChooser.APPROVE_OPTION) {
					File imgFile = imgChooser.getSelectedFile();
					String f = imgFile.getName();
					
					if(!(f.endsWith(".jpg") || f.endsWith(".jpeg") || f.endsWith(".png") || f.endsWith(".gif"))) {
						JOptionPane.showMessageDialog(GlobalPreferences.this, "File must be valid image file.\n*(.jpg, .jpeg, .png, .gif)");
					} else {
						ProjectView.TABLE_IMAGE = Util.getImage(imgFile);
						ProjectView.TABLE_IMAGE_LOC = imgFile.getAbsolutePath();
						lblImage.setIcon(new ImageIcon(ProjectView.TABLE_IMAGE));
					}
				}
			}
		});
		contentPanel.add(btnChooseImage);
		
		btnUseDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectView.TABLE_IMAGE_LOC = "Default";
				ProjectView.TABLE_IMAGE = Images.TABLE_IMAGE;
				lblImage.setIcon(new ImageIcon(ProjectView.TABLE_IMAGE));
			}
		});
		btnUseDefault.setBounds(297, 302, 128, 23);
		
		contentPanel.add(btnUseDefault);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyChanges();
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		setBackgroundColor(Constants.BACKGROUND);
		setFontColor(Constants.FONT_COLOR);
	}
	
	/**
	 * Sets the background color to a specified color
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		super.setBackgroundColor(color);
		setBackground(color);
		
		btnBackgroundColor.setBackground(color);
		btnChooseTableColor.setBackground(color);
		btnFontColor.setBackground(color);
		btnBackColor.setBackground(color);
		btnApply.setBackground(color);
		btnChooseImage.setBackground(color);
		btnUseDefault.setBackground(color);
		tHeightSpinner.setBackground(color);
		tWidthSpinner.setBackground(color);
		tSpeedSpinner.setBackground(color);
		pvHeightSpinner.setBackground(color);
		pvWidthSpinner.setBackground(color);
		boxShowInfo.setBackground(color);
		
		fontColor.setBackground(curFontColor);
	}
	
	/**
	 * Sets the font to a specified color
	 * @param color The color to set the font to
	 */
	public void setFontColor(Color color) {
		setForeground(color);
		
		lblTableHeight.setForeground(color);
		lblTableWidth.setForeground(color);
		lblTableSpeed.setForeground(color);
		lblProjectViewBackground.setForeground(color);
		lblDefaultTableColor.setForeground(color);
		lblProjectViewHeight.setForeground(color);
		lblProjectViewWidth.setForeground(color);
		lblMainFontColor.setForeground(color);
		lblProjectViewFont.setForeground(color);
		lblTableImage.setForeground(color);
		boxShowInfo.setForeground(color);
	}
}