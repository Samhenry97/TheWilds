package com.henry.wilds.ui.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.henry.wilds.inner.Counselor;
import com.henry.wilds.inner.Table;
import com.henry.wilds.inner.Team;
import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;
import com.henry.wilds.util.StringUtil;

/**
 * This class is the dialog box for editing teams.
 * Chooser a team from a dropdown list, and edit it
 * from there.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class EditTeam extends WildsDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel info = new JPanel();
	private JLabel lblName = new JLabel("Name:");
	private JLabel lblColor = new JLabel("Color:");
	private JLabel lblChooseTeam = new JLabel("Choose Team:");
	private JLabel img = new JLabel(new ImageIcon(Images.EDIT_TEAM));
	private JComboBox<Team> teamChooser = new JComboBox<Team>();
	private JTextField txtName;
	private JEditorPane colorChosen = new JEditorPane();
	private Color currentColor;
	private JButton chooseColor = new JButton("Choose Color...");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnSaveChanges = new JButton("Save Changes");
	private JButton btnReset = new JButton("Reset");

	/**
	 * The constructor for the dialog for editing teams
	 * @param mainFrame
	 */
	public EditTeam(WildsUI mainFrame, ProjectView pv) {
		super(mainFrame, pv);
		
		createPanel();
	}
	
	/**
	 * Updates the dropdown list for choosing teams,
	 * then sets the dialog visible.
	 */
	public void setVisible() {
		updateChooser();
		
		super.setVisible();
	}
	
	/**
	 * Sets the dialog visible, updating items,
	 * then sets the items according to a team.
	 * @param t Team to set visible to
	 */
	public void setVisible(Team t) {
		setVisible();
		
		teamChooser.setSelectedItem(t);
		setItems(t);
	}
	
	/**
	 * Saves the changes made in the dialog to whatever
	 * team is selected.
	 * @param exit Whether or not to exit the dialog after saving.
	 */
	private void saveChanges(boolean exit) {
		String name = txtName.getText();
		
		if(name.equals("") || name.trim().equals("") || name == null) {
			JOptionPane.showMessageDialog(this, "Name cannot be empty.");
		} else if(!StringUtil.isValidString(name)) {
			JOptionPane.showMessageDialog(EditTeam.this, "Team Name" + Constants.INVALID_CHAR_SEQ_MSG);
		} else {
			boolean save = true;
			for(int i = 0; i < pv.teams.size(); i++) {
				String teamName = pv.teams.get(i).getTeamName();
				if(teamName.equals(name) && !teamName.equals(teamChooser.getSelectedItem())) {
					JOptionPane.showMessageDialog(this, "There's already a team named " + name + "!");
					save = false;
					break;
				}
			}
			
			if(save) {
				Team toSave = (Team) teamChooser.getSelectedItem();
				
				if(toSave != null) {
					toSave.setTeamName(name);
					toSave.setColor(currentColor);
					txtName.setText("");
					if(exit) resetAndDispose();
					else updateChooser();
				} else {
					System.out.println("Error saving changes..");
				}
			}
		}
	}
	
	/**
	 * Set the items in the dialog to a certain team.
	 * @param team The team to set the items by
	 */
	private void setItems(Team team) {
		if(team != null) {
			System.out.println("Setting values to " + team.superToString() + ": " + team.getTeamName());
			
			txtName.setText(team.getTeamName());
			
			currentColor = team.getColor();
			colorChosen.setBackground(currentColor);
		} else {
			txtName.setText("");
			
			currentColor = Color.WHITE;
			colorChosen.setBackground(currentColor);
		}
	}
	
	/**
	 * Reset the textbox and then dispose the dialog.
	 */
	private void resetAndDispose() {
		txtName.setText("");
		dispose();
	}
	
	/**
	 * Update the dropdown list for choosing teams.
	 */
	private void updateChooser() {
		teamChooser.removeAllItems();
		
		for(int i = 0; i < pv.teams.size(); i++) {
			teamChooser.addItem(pv.teams.get(i));
		}
	}
	
	/**
	 * Reset the dialog's boxes to the current team's variables.
	 */
	private void reset() {
		setItems(null);
		for(int i = 0; i < pv.teams.size(); i++) {
			if(pv.teams.get(i).getTeamName().equals(teamChooser.getSelectedItem())) {
				setItems(pv.teams.get(i));
			}
		}
	}
	
	/**
	 * Creates the panel and all of the components contained in it
	 */
	private void createPanel() {
		setSize(415, 400);
		setTitle("Edit Teams");
		setLocationRelativeTo(getParent());
		setIconImage(Images.EDIT_TEAM_ICON);
		contentPanel.setLayout(null);
		
		lblChooseTeam.setBounds(32, 34, 69, 14);
		contentPanel.add(lblChooseTeam);
		teamChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < pv.teams.size(); i++) {
					if(pv.teams.get(i).equals((Team) teamChooser.getSelectedItem())) {
						setItems(pv.teams.get(i));
					}
				}
			}
		});
		teamChooser.setBounds(32, 50, Constants.TEXT_FIELD_WIDTH, 20);
		teamChooser.setToolTipText("Choose team to edit");
		contentPanel.add(teamChooser);
		
		img.setBounds(300, 30, 50, 50);
		contentPanel.add(img);
		
		info.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Team info"));
		info.setBounds(32, 94, 341, 99);
		contentPanel.add(info);
		info.setLayout(null);
		
		lblName.setBounds(23, 26, 46, 14);
		info.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(23, 41, Constants.TEXT_FIELD_WIDTH, 20);
		txtName.setToolTipText("Team's name");
		info.add(txtName);
		txtName.setColumns(10);
		
		lblColor.setBounds(179, 26, 46, 14);
		info.add(lblColor);
		
		chooseColor.setBounds(209, 38, 109, 23);
		chooseColor.setToolTipText("Choose the team's color");
		chooseColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = JColorChooser.showDialog(EditTeam.this, "Choose Color", currentColor);
				colorChosen.setBackground(currentColor);
			}
		});
		info.add(chooseColor);
		
		colorChosen.setBounds(179, 41, 20, 20);
		colorChosen.setToolTipText("Team's color");
		colorChosen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		info.add(colorChosen);
		colorChosen.setEditable(false);
		
		btnSaveChanges.setToolTipText("Save changes made to team");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanges(false);
			}
		});
		btnSaveChanges.setBounds(32, 255, 101, 23);
		contentPanel.add(btnSaveChanges);
		
		btnReset.setToolTipText("Reset changes made to team");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setBounds(143, 255, 89, 23);
		contentPanel.add(btnReset);
		
		btnDelete.setToolTipText("Delete the selected team");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Team teamToDelete = (Team) teamChooser.getSelectedItem();
				
				for(int i = 0; i < pv.counselors.size(); i++) {
					Counselor c = pv.counselors.get(i);
					
					if(c.getTeam() == teamToDelete) {
						c.setTeam(null);
					}
				}
				
				for(int i = 0; i < pv.tables.size(); i++) {
					Table t = pv.tables.get(i);
					
					if(t.getTeam() == teamToDelete) {
						t.setTeam(null);
					}
				}
				
				pv.teams.remove(teamToDelete);
				updateChooser();
				reset();
			}
		});
		btnDelete.setBounds(172, 49, 89, 23);
		contentPanel.add(btnDelete);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanges(true);
				resetAndDispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAndDispose();
			}
		});
		
		setBackgroundColor(Constants.BACKGROUND);
		setFontColor(Constants.FONT_COLOR);
	}
	
	/**
	 * Sets the background of all the components in the dialog to
	 * a specified color
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		super.setBackgroundColor(color);
		setBackground(color);
		
		info.setBackground(color);
		teamChooser.setBackground(color);
		txtName.setBackground(color);
		chooseColor.setBackground(color);
		btnDelete.setBackground(color);
		btnSaveChanges.setBackground(color);
		btnReset.setBackground(color);
	}
	
	/**
	 * Sets the font colo
	 * @param color
	 */
	public void setFontColor(Color color) {
		setForeground(color);
		
		info.setForeground(color);
		lblName.setForeground(color);
		lblColor.setForeground(color);
		lblChooseTeam.setForeground(color);
		txtName.setForeground(color);
	}
}