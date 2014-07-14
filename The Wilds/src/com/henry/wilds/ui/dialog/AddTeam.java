package com.henry.wilds.ui.dialog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.henry.wilds.inner.Team;
import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;
import com.henry.wilds.util.StringUtil;

/**
 * This is the class for the dialog that lets
 * the user add a team to the team arraylist.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class AddTeam extends WildsDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField teamName;
	private Color currentColor = Color.BLACK;
	private JButton chooseColor = new JButton("Choose Color...");
	private JEditorPane colorChosen = new JEditorPane();
	private JLabel lblColor = new JLabel("Color:");
	private JLabel lblTeamName = new JLabel("Team Name:");
	
	/**
	 * Creates a dialog that allows the user
	 * to create and add a team
	 * @param mainFrame The main window
	 */
	public AddTeam(final WildsUI mainFrame, ProjectView pv) {
		super(mainFrame, pv);
		
		createPanel();
	}
	
	/**
	 * Reset values of components,
	 * then dispose the dialog
	 */
	public void resetAndDispose() {
		teamName.setText("");
		dispose();
	}
	
	/**
	 * Sets the dialog visible
	 */
	@Override
	public void setVisible() {
		teamName.requestFocusInWindow();
		
		super.setVisible(true);
	}
	
	/**
	 * Creates the dialog and all the components contained inside
	 */
	public void createPanel() {
		contentPanel.setLayout(null);
		setTitle("New Team");
		setSize(Constants.STD_DIALOG_WIDTH, 225);
		setLocationRelativeTo(getParent());
		setIconImage(Images.ADD_TEAM_ICON);
		
		lblTeamName.setBounds(31, 34, 64, 14);
		contentPanel.add(lblTeamName);
		
		teamName = new JTextField();
		teamName.setToolTipText("Team name");
		teamName.setBounds(31, 50, Constants.TEXT_FIELD_WIDTH, 20);
		contentPanel.add(teamName);
		teamName.setColumns(10);
		
		chooseColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = JColorChooser.showDialog(AddTeam.this, "Choose Color", currentColor);
				colorChosen.setBackground(currentColor);
				teamName.requestFocusInWindow();
			}
		});
		chooseColor.setBounds(61, 97, 130, 23);
		chooseColor.setToolTipText("Choose team color");
		contentPanel.add(chooseColor);
		
		colorChosen = new JEditorPane();
		colorChosen.setToolTipText("Current color");
		colorChosen.setBounds(31, 100, 20, 20);
		colorChosen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPanel.add(colorChosen);
		colorChosen.setEditable(false);
		lblColor.setBounds(31, 81, 46, 14);
		contentPanel.add(lblColor);
		
		JLabel img = new JLabel();
		img.setBounds(220, 30, 50, 50);
		img.setIcon(new ImageIcon(Images.ADD_TEAM));
		contentPanel.add(img);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = teamName.getText();
				if(name.equals("") || name.trim().equals("") || name == null) {
					JOptionPane.showMessageDialog(AddTeam.this, "Team Name cannot be empty.");
				} else if(!StringUtil.isValidString(name)) {
					JOptionPane.showMessageDialog(AddTeam.this, "Team Name" + Constants.INVALID_CHAR_SEQ_MSG);
				} else {
					boolean add = true;
					for(int i = 0; i < pv.teams.size(); i++) {
						if(pv.teams.get(i).getTeamName().equals(name)) {
							add = false;
							break;
						}
					}
					if(add) {
						Team teamToAdd = new Team(name, currentColor);
						pv.teams.add(teamToAdd);
						mainFrame.addCouns.addTeam(teamToAdd);
						
						System.out.println("Successfully added " + teamToAdd.superToString() + ": " + teamToAdd.getTeamName());
						
						pv.sortLists();
						
						resetAndDispose();
					} else {
						JOptionPane.showMessageDialog(AddTeam.this, "There's already a team named " + name);
					}
				}
				teamName.setText("");
				teamName.requestFocusInWindow();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAndDispose();
			}
		});
		
		setBackgroundColor(Constants.BACKGROUND);
		setFontColor(Constants.FONT_COLOR);
		
		teamName.requestFocusInWindow();
	}
	
	/**
	 * Sets the background of all the components of 
	 * this dialog to a specified color
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		super.setBackgroundColor(color);
		setBackground(color);
		
		chooseColor.setBackground(color);
		teamName.setBackground(color);
	}
	
	public void setFontColor(Color color) {
		setForeground(color);
		
		lblTeamName.setForeground(color);
		lblColor.setForeground(color);
		teamName.setForeground(color);
	}
}