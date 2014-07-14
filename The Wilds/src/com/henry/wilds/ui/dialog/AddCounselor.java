package com.henry.wilds.ui.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.henry.wilds.inner.Counselor;
import com.henry.wilds.inner.Team;
import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;
import com.henry.wilds.util.StringUtil;

/**
 * The dialog for adding a counselor to the counselor arraylist.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class AddCounselor extends WildsDialog {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblName = new JLabel("Name:");
	private JLabel lblCabinName = new JLabel("Cabin Name:");
	private JLabel lblTeam = new JLabel("Team:");
	private JLabel lblPartner = new JLabel("Partner:");
	private JLabel img = new JLabel(new ImageIcon(Images.ADD_COUNS));
	private JTextField txtName;
	private JTextField txtCabinName;
	private JComboBox<Counselor> partnerChooser = new JComboBox<Counselor>();
	private JComboBox<Team> teamChooser = new JComboBox<Team>();
	private JButton btnAddTeam = new JButton("Add Team");
	private JEditorPane teamColor = new JEditorPane();

	/**
	 * Creates the AddCounselor dialog for use
	 * in the program
	 * @param mainFrame The main WildsUI of the program
	 */
	public AddCounselor(final WildsUI mainFrame, ProjectView pv) {
		super(mainFrame, pv);
		
		createPanel();
	}
	
	/**
	 * Adds a team to the team dropdown list
	 * @param team
	 */
	public void addTeam(Team team) {
		if(isVisible()) {
			teamChooser.addItem(team);
			teamChooser.setSelectedItem(team);
		}
	}
	
	/**
	 * Resets the values of components and 
	 * disposes dialog
	 */
	public void resetAndDispose() {
		txtName.setText("");
		txtCabinName.setText("");
		dispose();
	}
	
	public void updatePartnerChooser(Team team) {
		partnerChooser.removeAllItems();
		
		partnerChooser.addItem(null);
		for(int i = 0; i < pv.counselors.size(); i++) {
			Counselor c = pv.counselors.get(i);
			if(c.getTeam() == team || c.getTeam() == null) {
				partnerChooser.addItem(c);
			}
		}
	}
	
	/**
	 * Sets the dialog visible with the needed values
	 * in the components
	 */
	@Override
	public void setVisible() {
		partnerChooser.removeAllItems();
		teamChooser.removeAllItems();
		
		teamChooser.addItem(null);
		for(int i = 0; i < pv.teams.size(); i++) {
			teamChooser.addItem(pv.teams.get(i));
		}
		
		partnerChooser.addItem(null);
		for(int i = 0; i < pv.counselors.size(); i++) {
			if(pv.counselors.get(i).getPartner() == null) {
				partnerChooser.addItem(pv.counselors.get(i));
			}
		}
		
		super.setVisible();
	}
	
	/**
	 * Creates the dialog and all of its components
	 */
	public void createPanel() {
		setTitle("New Counselor...");
		setIconImage(Images.ADD_COUNS_ICON);
		setSize(Constants.STD_DIALOG_WIDTH, Constants.STD_DIALOG_HEIGHT);
		setLocationRelativeTo(getParent());
		contentPanel.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(32, 50, Constants.TEXT_FIELD_WIDTH, 20);
		txtName.setToolTipText("New counselor's name");
		contentPanel.add(txtName);
		txtName.setColumns(10);
		lblName.setBounds(32, 34, 46, 14);
		contentPanel.add(lblName);
		
		lblCabinName.setBounds(32, 81, 61, 14);
		lblCabinName.setToolTipText("Counselor's cabin name");
		contentPanel.add(lblCabinName);
		txtCabinName = new JTextField();
		txtCabinName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				for(int i = 0; i < pv.counselors.size(); i++) {
					Counselor c = pv.counselors.get(i);
					if(c.getCabinName().equals(txtCabinName.getText()) && c.getPartner() == null) {
						partnerChooser.setSelectedItem(c);
						break;
					}
				}					
			}
		});
		txtCabinName.setBounds(32, 97, Constants.TEXT_FIELD_WIDTH, 20);
		txtCabinName.setToolTipText("Counselor's cabin name");
		contentPanel.add(txtCabinName);
		txtCabinName.setColumns(10);
		
		lblTeam.setBounds(32, 128, 46, 14);
		contentPanel.add(lblTeam);
		
		teamChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				Team team = (Team) teamChooser.getSelectedItem();
				
				if(team != null) teamColor.setBackground(team.getColor());
				
				updatePartnerChooser(team);
			}
		});
		teamChooser.setBounds(32, 145, Constants.TEXT_FIELD_WIDTH, 20);
		teamChooser.setToolTipText("Choose counselor's team");
		contentPanel.add(teamChooser);
		
		btnAddTeam.setToolTipText("Add team");
		btnAddTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.addTeam.setVisible();
			}
		});
		btnAddTeam.setBounds(169, 144, 89, 23);
		contentPanel.add(btnAddTeam);
		
		lblPartner.setBounds(32, 203, 46, 14);
		contentPanel.add(lblPartner);
		partnerChooser.setBounds(32, 220, Constants.TEXT_FIELD_WIDTH, 20);
		contentPanel.add(partnerChooser);
		
		teamColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teamColor.setToolTipText("Team's color");
		teamColor.setEditable(false);
		teamColor.setBounds(201, 170, 20, 20);
		contentPanel.add(teamColor);
		
		img.setBounds(190, 60, 50, 50);
		contentPanel.add(img);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String cabinName = txtCabinName.getText();
				Team team = (Team) teamChooser.getSelectedItem();
				Counselor partner = (Counselor) partnerChooser.getSelectedItem();
				
				if(name.equals("") || name.trim().equals("") || name == null) {
					JOptionPane.showMessageDialog(AddCounselor.this, "Name cannot be empty.");
				} else if(!StringUtil.isValidString(name)) {
					JOptionPane.showMessageDialog(AddCounselor.this, "Name" + Constants.INVALID_CHAR_SEQ_MSG);
				} else if(!StringUtil.isValidString(cabinName)) {
					JOptionPane.showMessageDialog(AddCounselor.this, "Cabin Name" + Constants.INVALID_CHAR_SEQ_MSG);
				} else {
					Counselor newCounselor = new Counselor(name, cabinName, team, partner);
					
					if(partner != null) {
						if(partner.getTeam() == null && team != null) {
							partner.setTeam(team);
						} else if(team == null && partner.getTeam() != null) {
							newCounselor.setTeam(partner.getTeam());
						}
						
						partner.setPartner(newCounselor);
					}
					
					pv.counselors.add(newCounselor);
					
					pv.sortLists();
					
					System.out.println("Successfully added " + newCounselor.superToString() + ": " + newCounselor.getName());
					
					resetAndDispose();
				}
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
	 * Sets the background of all the components in this
	 * dialog to a specific color
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		super.setBackgroundColor(color);
		setBackground(color);
		
		btnAddTeam.setBackground(color);
		teamChooser.setBackground(color);
		partnerChooser.setBackground(color);
		txtCabinName.setBackground(color);
		txtName.setBackground(color);
	}
	
	public void setFontColor(Color color) {
		setForeground(color);
		
		txtCabinName.setForeground(color);
		txtName.setForeground(color);
		lblPartner.setForeground(color);
		lblTeam.setForeground(color);
		lblCabinName.setForeground(color);
		lblName.setForeground(color);
	}
}