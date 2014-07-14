package com.henry.wilds.ui.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;

/**
 * This is the dialog that allows the user to 
 * edit the preferences of the project.
 * @author Samuel Henry
 * @version 1.0
 * @since June 22, 2014
 */
public class Preferences extends WildsDialog {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblProjectViewWidth = new JLabel("Project View Width:");
	private JLabel lblProjectViewHeight = new JLabel("Project View Height:");
	private JLabel lblProjectViewBackground = new JLabel("Project View Background Color:");
	private JLabel lblTableSpeed = new JLabel("Table speed:");
	private JLabel lblTableWidth = new JLabel("Table Width:");
	private JLabel lblTableHeight = new JLabel("Table Height:");
	private JSpinner pvWidthSpinner = new JSpinner();
	private JSpinner pvHeightSpinner = new JSpinner();
	private JSpinner tSpeedSpinner = new JSpinner();
	private JSpinner tWidthSpinner = new JSpinner();
	private JSpinner tHeightSpinner = new JSpinner();
	private JButton btnApply = new JButton("Apply");
	private JButton btnBackgroundColor = new JButton("Choose Color...");
	private JEditorPane backgroundColor = new JEditorPane();
	
	private Color curBackColor = Color.WHITE;
	
	public Preferences(WildsUI mainFrame, ProjectView pv) {
		super(mainFrame, pv);
		
		createPanel();
	}
	
	/**
	 * Sets the dialog visible, setting the
	 * values of the components to what they
	 * need to be
	 */
	public void setVisible() {
		pvWidthSpinner.setValue(ProjectView.PROJ_VIEW_WIDTH);
		pvHeightSpinner.setValue(ProjectView.PROJ_VIEW_HEIGHT);
		tSpeedSpinner.setValue(ProjectView.TABLE_SPEED);
		tHeightSpinner.setValue(ProjectView.TABLE_HEIGHT);
		tWidthSpinner.setValue(ProjectView.TABLE_WIDTH);
		curBackColor = ProjectView.PROJ_VIEW_BACK_COLOR;
		backgroundColor.setBackground(curBackColor);
		
		super.setVisible();
	}
	
	/**
	 * Applies the changes made in the dialog.
	 */
	public void applyChanges() {
		ProjectView.PROJ_VIEW_WIDTH = (Integer) pvWidthSpinner.getValue();
		ProjectView.PROJ_VIEW_HEIGHT = (Integer) pvHeightSpinner.getValue();
		
		int r = curBackColor.getRed();
		int g = curBackColor.getGreen();
		int b = curBackColor.getBlue();
		
		if(r > 220 && b > 220 && g > 220) {
			curBackColor = new Color(220, 220, 220);
		} else if( r < 50 && b < 50 && g < 50) {
			curBackColor = new Color(50, 50, 50);
		}
		
		ProjectView.PROJ_VIEW_BACK_COLOR = curBackColor;
		ProjectView.TABLE_SPEED = (Integer) tSpeedSpinner.getValue();
		ProjectView.TABLE_WIDTH = (Integer) tWidthSpinner.getValue();
		ProjectView.TABLE_HEIGHT = (Integer) tHeightSpinner.getValue();
	}
	
	/**
	 * Creates the dialog with all its components
	 */
	public void createPanel() {
		setTitle("Preferences");
		setSize(500, 600);
		setLocationRelativeTo(getParent());
		setIconImage(Images.PREFS_ICON);
		contentPanel.setLayout(null);
		
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
		
		lblProjectViewBackground.setBounds(32, 129, 162, 14);
		contentPanel.add(lblProjectViewBackground);
		
		backgroundColor.setBounds(32, 151, 20, 20);
		backgroundColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		backgroundColor.setEditable(false);
		contentPanel.add(backgroundColor);
		
		btnBackgroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curBackColor = JColorChooser.showDialog(Preferences.this, "Chooser Background Color", curBackColor);
				backgroundColor.setBackground(curBackColor);
			}
		});
		btnBackgroundColor.setBounds(62, 148, 114, 23);
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
	 * Sets the background of all the components to a specific color
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		super.setBackgroundColor(color);
		setBackground(color);
		
		pvWidthSpinner.setBackground(color);
		pvHeightSpinner.setBackground(color);
		tSpeedSpinner.setBackground(color);
		tWidthSpinner.setBackground(color);
		tHeightSpinner.setBackground(color);
		btnApply.setBackground(color);
		btnBackgroundColor.setBackground(color);
	}
	
	/**
	 * Sets the font color of all components to the specified color
	 * @param color The color to set the font to
	 */
	public void setFontColor(Color color) {
		setForeground(color);
		
		lblTableHeight.setForeground(color);
		lblTableWidth.setForeground(color);
		lblTableSpeed.setForeground(color);
		lblProjectViewBackground.setForeground(color);
		lblProjectViewHeight.setForeground(color);
		lblProjectViewWidth.setForeground(color);
	}
}