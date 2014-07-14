package com.henry.wilds.ui.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
 * This class is the dialog for editing a counselor.
 * Choose the counselor from a dropdown list, and then 
 * edit him from there.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class EditCounselor extends WildsDialog {
	private static final long serialVersionUID = 1L;

	private JLabel lblName = new JLabel("Name:");
	private JLabel lblChooseCounselor = new JLabel("Choose counselor:");
	private JLabel lblTeam = new JLabel("Team:");
	private JLabel lblCabinName = new JLabel("Cabin Name:");
	private JLabel lblPartner = new JLabel("Partner:");
	private JLabel img = new JLabel(new ImageIcon(Images.EDIT_COUNS));
	private JComboBox<Counselor> counsChooser = new JComboBox<Counselor>(); //The dropdown list for the counselor to be edited
	private JTextField txtName; //The name of the counselor to be edited
	private JTextField txtCabinName; //The cabin name of the counselor to be edited
	private JComboBox<Counselor> partnerChooser = new JComboBox<Counselor>(); //The dropdown list for the partner of the counselor to be edited
	private JComboBox<Team> teamChooser = new JComboBox<Team>(); //The team dropdown list of counselor to be edited
	private JEditorPane teamColor = new JEditorPane(); //The pane that shows the color of the team selected
	private JPanel panel = new JPanel();
	private JButton btnSaveChanges = new JButton("Save Changes");
	private JButton btnReset = new JButton("Reset");
	private JButton btnDelete = new JButton("Delete");
	
	/**
	 * The constructor for the dialog for editing counselors
	 * @param mainFrame The main window of the program
	 */
	public EditCounselor(WildsUI mainFrame, ProjectView pv) {
		super(mainFrame, pv);
		
		createPanel();
	}
	
	/**
	 * This method sets all the contents of the dialog
	 * to what they need to be when the window is displayed.
	 * It then displays the dialog.
	 */
	@Override
	public void setVisible() {
		updateChooser();
		updatePartnerChooser();
		updateTeamChooser();
		
		setItems((Counselor) counsChooser.getSelectedItem());
		
		super.setVisible();
	}
	
	/**
	 * Sets the frame visible, then sets the selected
	 * counselor and items to a specified counselor
	 * @param c
	 */
	public void setVisible(Counselor c) {
		setVisible();
		
		counsChooser.setSelectedItem(c);
		setItems(c);
	}
	
	/**
	 * This method saves the changes to the selected counselors based
	 * on the values that have been input.
	 * @param exit Whether or not to exit once the changes have been saved.
	 */
	private void saveChanges(boolean exit) {
		String name = txtName.getText();
		Team team = (Team) teamChooser.getSelectedItem();
		Counselor partner = (Counselor) partnerChooser.getSelectedItem();
		
		if(name.equals("") || name.trim().equals("") || name == null) {
			JOptionPane.showMessageDialog(this, "Name cannot be empty.");
		} else if(!StringUtil.isValidString(name)) {
			JOptionPane.showMessageDialog(EditCounselor.this, "Name" + Constants.INVALID_CHAR_SEQ_MSG);
		} else if(!StringUtil.isValidString(txtCabinName.getText())) {
			JOptionPane.showMessageDialog(EditCounselor.this, "Cabin Name" + Constants.INVALID_CHAR_SEQ_MSG);
		} else {
			Counselor toSave = (Counselor) counsChooser.getSelectedItem();
			
			if(toSave != null) {
				toSave.setName(name);
				toSave.setTeam(team);
				toSave.setCabinName(txtCabinName.getText());
				if(toSave.getPartner() != partner) {
					if(toSave.getPartner() != null) {
						toSave.getPartner().setPartner(null);
					}
					toSave.setPartner(partner);
					if(partner != null) {
						partner.setPartner(toSave);
					}
				}
				
				if(exit) resetAndDispose();
				else updateChooser();
			} else {
				System.out.println("Error saving changes..");
			}
		}
	}
	
	/**
	 * Reset the items in the components of the dialog
	 * based on the selected counselor
	 */
	private void reset() {
		setItems(null);
		for(int i = 0; i < pv.counselors.size(); i++) {
			if(pv.counselors.get(i).getName().equals(counsChooser.getSelectedItem())) {
				setItems(pv.counselors.get(i));
			}
		}
	}
	
	/**
	 * Reset the values of the dialog, then dispose the whole thing.
	 */
	private void resetAndDispose() {
		txtName.setText("");
		txtCabinName.setText("");
		dispose();
	}
	
	/**
	 * Remove all items from the counselor chooser, then
	 * add all the values from the counselor arraylist
	 * to the chooser.
	 */
	private void updateChooser() {
		counsChooser.removeAllItems();
		
		for(int i = 0; i < pv.counselors.size(); i++) {
			counsChooser.addItem(pv.counselors.get(i));
		}
	}
	
	/**
	 * Remove all items from the partner chooser, then
	 * add all the values from the counselor arraylist
	 * that can be added to the chooser.
	 */
	private void updatePartnerChooser() {
		partnerChooser.removeAllItems();
		
		partnerChooser.addItem(null);
		for(int i = 0; i < pv.counselors.size(); i++) {
			Counselor c = pv.counselors.get(i);
			if(c != (Counselor) counsChooser.getSelectedItem()) {
				if(c.getPartner() == (Counselor) counsChooser.getSelectedItem()) {
					partnerChooser.addItem(c);
				} else if(c.getPartner() == null) {
					partnerChooser.addItem(c);
				}
			}
		}
	}
	
	/**
	 * Remove all items from the partner chooser, then
	 * add all the values from the team arraylist
	 * to the chooser.
	 */
	private void updateTeamChooser() {
		teamChooser.removeAllItems();
		
		teamChooser.addItem(null);
		for(int i = 0; i < pv.teams.size(); i++) {
			teamChooser.addItem(pv.teams.get(i));
		}
	}
	
	/**
	 * Set the items in the dialog to a specific 
	 * counselor's variables.
	 * @param couns The counselor to set the items to
	 */
	private void setItems(Counselor couns) {
		if(couns != null) {
			System.out.println("Setting values to " + couns.superToString() + ": " + couns.getName());
			txtName.setText(couns.getName());
			txtCabinName.setText(couns.getCabinName());
			if(couns.getTeam() != null) {
				teamChooser.setSelectedItem(couns.getTeam());
				teamColor.setBackground(couns.getTeam().getColor());
			} else {
				teamChooser.setSelectedItem(null);
				teamColor.setBackground(Color.WHITE);
			}
			
			updatePartnerChooser();
			if(couns.getPartner() != null) {
				partnerChooser.setSelectedItem(couns.getPartner());
			} else {
				partnerChooser.setSelectedItem(null);
			}
		} else {
			System.out.println("SETTING TO NULL");
			txtName.setText("");
			txtCabinName.setText("");
			updatePartnerChooser();
		}
	}
	
	/**
	 * Set the color of the JEditorPane that displays
	 * the current color selected based on the team chooser.
	 */
	private void setTeamColor() {
		Team team = (Team) teamChooser.getSelectedItem();
		if(team != null) {
			teamColor.setBackground(team.getColor());
		}
	}
	
	/**
	 * Creates the dialog and all of its components
	 */
	private void createPanel() {
		setTitle("Edit counselors");
		setSize(500, 475);
		setLocationRelativeTo(getParent());
		setIconImage(Images.EDIT_COUNS_ICON);
		contentPanel.setLayout(null);
		
		counsChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < pv.counselors.size(); i++) {
					if(pv.counselors.get(i).equals((Counselor) counsChooser.getSelectedItem())) {
						setItems(pv.counselors.get(i));
					}
				}
			}
		});
		counsChooser.setBounds(32, 54, 197, 20);
		counsChooser.setToolTipText("Counselor to edit");
		contentPanel.add(counsChooser);
		
		img.setBounds(375, 40, 50, 50);
		contentPanel.add(img);
		
		//The panel that contains the info of the selected counselor
		panel.setBounds(32, 116, 420, 143);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Counselor info"));
		contentPanel.add(panel);
		panel.setLayout(null);
		
		//The label for the name of the selected counselor
		lblName.setBounds(24, 30, 46, 14);
		panel.add(lblName);
		
		//The text field for editing the name
		txtName = new JTextField();
		txtName.setBounds(24, 46, Constants.TEXT_FIELD_WIDTH, 20);
		txtName.setToolTipText("Counselor's name");
		panel.add(txtName);
		txtName.setColumns(10);
		
		//The label for the name of the selected counselor's cabin
		lblCabinName.setBounds(24, 77, 61, 14);
		panel.add(lblCabinName);
		
		//The text field for editing the cabin name
		txtCabinName = new JTextField();
		txtCabinName.setBounds(24, 94, Constants.TEXT_FIELD_WIDTH, 20);
		txtCabinName.setToolTipText("Counselors's cabin name");
		panel.add(txtCabinName);
		txtCabinName.setColumns(10);
		
		//The Team dropdown list
		lblTeam.setBounds(215, 30, 46, 14);
		panel.add(lblTeam);
		teamChooser.setBounds(215, 46, Constants.TEXT_FIELD_WIDTH, 20);
		teamChooser.setToolTipText("Counselor's team");
		teamChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTeamColor();
			}
		});
		panel.add(teamChooser);
		
		//The label for the partner dropdown list
		lblPartner.setBounds(215, 77, 46, 14);
		panel.add(lblPartner);
		
		//The actual partner dropdown list
		partnerChooser.setBounds(215, 94, Constants.TEXT_FIELD_WIDTH, 20);
		partnerChooser.setToolTipText("Counselor's partner");
		panel.add(partnerChooser);
		
		//The JEditorPane for the current color of the team selected
		teamColor.setBounds(360, 46, 21, 20);
		teamColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teamColor.setToolTipText("Team color");
		panel.add(teamColor);
		teamColor.setEditable(false);
		
		//The label for choosing the counselor to be edited
		lblChooseCounselor.setBounds(32, 37, 89, 14);
		contentPanel.add(lblChooseCounselor);
		
		//The button for saving the changes made to the counselor
		btnSaveChanges.setToolTipText("Save changes made to counselor");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanges(false);
			}
		});
		btnSaveChanges.setBounds(32, 317, 101, 23);
		contentPanel.add(btnSaveChanges);
		
		//Button to reset all changes made
		btnReset.setToolTipText("Reset changes made to this counselor");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setBounds(143, 317, 89, 23);
		contentPanel.add(btnReset);
		
		//Button to delete the current selected counselor
		btnDelete.setToolTipText("Delete the selected counselor");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Counselor counsToDelete = (Counselor) counsChooser.getSelectedItem();
				
				for(int i = 0; i < pv.tables.size(); i++) {
					Table t = pv.tables.get(i);
					
					if(t.getCounselor() == counsToDelete) {
						t.setCounselor(null);
					}
				}
				
				pv.counselors.remove(counsToDelete);
				updateChooser();
				reset();
			}
		});
		btnDelete.setBounds(239, 53, 89, 23);
		contentPanel.add(btnDelete);
		
		//What to do when the okay button is clicked
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanges(true);
			}
		});
		
		//What to do when cancel button is clicked
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAndDispose();
			}
		});
		
		setBackgroundColor(Constants.BACKGROUND);
		setFontColor(Constants.FONT_COLOR);
	}
	
	/**
	 * Sets the background color of all the components in the dialog
	 * to the specified color
	 * @param color The color to set background to
	 */
	public void setBackgroundColor(Color color) {
		super.setBackgroundColor(color);
		setBackground(color);
		
		counsChooser.setBackground(color);
		teamChooser.setBackground(color);
		txtName.setBackground(color);
		txtCabinName.setBackground(color);
		partnerChooser.setBackground(color);
		btnDelete.setBackground(color);
		btnReset.setBackground(color);
		btnSaveChanges.setBackground(color);
		panel.setBackground(color);
	}
	
	public void setFontColor(Color color) {
		setForeground(color);
		
		lblName.setForeground(color);
		lblChooseCounselor.setForeground(color);
		lblTeam.setForeground(color);
		lblCabinName.setForeground(color);
		lblPartner.setForeground(color);
		txtName.setForeground(color);
		txtCabinName.setForeground(color);
		panel.setForeground(color);
	}
}