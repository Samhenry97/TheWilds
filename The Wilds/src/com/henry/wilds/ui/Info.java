package com.henry.wilds.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.henry.wilds.inner.Counselor;
import com.henry.wilds.inner.Table;
import com.henry.wilds.inner.Team;
import com.henry.wilds.util.Constants;
import javax.swing.JCheckBox;

/**
 * This class holds the info for the selected table in the ProjectView
 * @author Samuel Henry
 * @since June 20, 2014
 * @version 1.0
 */
public class Info extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblSelectedTableInfo = new JLabel("Selected table info:");
	private JLabel lblCounselor = new JLabel("Counselor: ");
	private JLabel lblX = new JLabel("X:");
	private JLabel lblY = new JLabel("Y:");
	private JLabel lblWidth = new JLabel("Width:");
	private JLabel lblHeight = new JLabel("Height:");
	private JLabel lblTeam = new JLabel("Team:");
	private JComboBox<Counselor> counsChooser = new JComboBox<Counselor>();
	private JComboBox<Team> teamChooser = new JComboBox<Team>();
	private JSpinner xSpinner = new JSpinner();
	private JSpinner ySpinner = new JSpinner();
	private JSpinner widthSpinner = new JSpinner();
	private JSpinner heightSpinner = new JSpinner();
	private JButton btnRotate = new JButton("Rotate");
	private JButton btnSwap = new JButton("Swap");
	private JButton btnChoosePartner = new JButton("Choose Partner");
	private JCheckBox fixed = new JCheckBox("Fixed");
	
	private boolean loading = false;
	private boolean enabled = false;
	
	private ProjectView pv;

	/**
	 * Creates the info JPanel, then starts the thread that
	 * runs it.
	 */
	public Info() {
		createPanel();
	}

	/**
	 * Called by the ProjectView whenever the value
	 * of the selected table is changed
	 */
	public void update() {
		if(pv.getZoom() == 100) {
			loading = true;
			
			if(pv.selected.isEmpty()) {
				if(enabled) setEnabled(false);
				if(btnSwap.isEnabled()) btnSwap.setEnabled(false);
				clearItems();
			} else if(pv.selected.size() == 1) {
				if(!enabled) setEnabled(true);
				counsChooser.setEnabled(true);
				
				Table t = pv.selected.get(0);
				updateCounsChooser();
				updateTeamChooser();
				
				xSpinner.setValue(t.getX());
				ySpinner.setValue(t.getY());
				widthSpinner.setValue(t.getWidth());
				heightSpinner.setValue(t.getHeight());
				fixed.setSelected(t.isFixed());
				
				if(t.getCounselor() != null) {
					counsChooser.setSelectedItem(t.getCounselor());
				} else {
					counsChooser.setSelectedItem(null);
				}
				if(t.getTeam() != null) {
					teamChooser.setSelectedItem(t.getTeam());
				} else {
					teamChooser.setSelectedItem(null);
				}
				
				if(btnSwap.isEnabled()) btnSwap.setEnabled(false);
			} else {
				if(pv.selected.size() == 2) {
					if(!btnSwap.isEnabled()) btnSwap.setEnabled(true);
				} else {
					if(btnSwap.isEnabled()) btnSwap.setEnabled(false);
				}
				
				Team team = null;
				
				for(int i = 0; i < pv.selected.size(); i++) {
					Table t = pv.selected.get(i);
					
					if(i == 0) {
						team = t.getTeam();
					} else {
						if(t.getTeam() != team && team != null) {
							team = null;
						}
					}
				}
				
				updateTeamChooser();
				if(team != null) {
					teamChooser.setSelectedItem(team);
				}
				
				if(!enabled) setEnabled(true);
				counsChooser.setEnabled(false);
				
				xSpinner.setValue(0);
				ySpinner.setValue(0);
				widthSpinner.setValue(0);
				heightSpinner.setValue(0);
				
				fixed.setSelected(false);
				
				if(btnChoosePartner.isEnabled()) btnChoosePartner.setEnabled(false);
			}
			
			loading = false;
		}
	}
	
	/**
	 * Resets and refills the counselor JComboBox
	 */
	public void updateCounsChooser() {
		counsChooser.removeAllItems();
		
		counsChooser.addItem(null);
		Table t = pv.selected.get(0);
		
		if(t.getTeam() == null) {
			for(int i = 0; i < pv.counselors.size(); i++) {
				Counselor c = pv.counselors.get(i);
				
				boolean add = true;
				
				for(int x = 0; x < pv.tables.size(); x++) {
					Table other = pv.tables.get(x);
					if(other.getCounselor() == c && t.getCounselor() != c) {
						add = false;
						break;
					}
				}
				
				if(add) {
					counsChooser.addItem(pv.counselors.get(i));
				}
			}
		} else {
			for(int i = 0; i < pv.counselors.size(); i++) {
				Counselor c = pv.counselors.get(i);
				
				boolean add = true;
				
				for(int x = 0; x < pv.tables.size(); x++) {
					Table other = pv.tables.get(x);
					if(other.getCounselor() == c && t.getCounselor() != c) {
						add = false;
						break;
					}
				}
				
				if(c.getTeam() == t.getTeam() && add) {
					counsChooser.addItem(c);
				}
			}
		}
	}
	
	/**
	 * Updates and refills the Team JComboBox
	 */
	public void updateTeamChooser() {
		teamChooser.removeAllItems();
		
		teamChooser.addItem(null);
		for(int i = 0; i < pv.teams.size(); i++) {
			teamChooser.addItem(pv.teams.get(i));
		}
	}
	
	/**
	 * Sets the components in the panel enabled
	 * @param enabled Whether or not they should be enabled
	 */
	public void setEnabled(boolean enabled) {
		counsChooser.setEnabled(enabled);
		teamChooser.setEnabled(enabled);
		xSpinner.setEnabled(enabled);
		ySpinner.setEnabled(enabled);
		widthSpinner.setEnabled(enabled);
		heightSpinner.setEnabled(enabled);
		btnRotate.setEnabled(enabled);
		btnChoosePartner.setEnabled(enabled);
		fixed.setEnabled(enabled);
		
		this.enabled = enabled;
	}
	
	/**
	 * Sets the components in the panel to the selected
	 * table.
	 */
	public void clearItems() {
		counsChooser.setSelectedItem(null);
		teamChooser.setSelectedItem(null);
		
		xSpinner.setValue(0);
		ySpinner.setValue(0);
		widthSpinner.setValue(0);
		heightSpinner.setValue(0);
		
		fixed.setSelected(false);
	}
	
	/**
	 * Updates the x and y position of the table.
	 */
	public void updatePos() {
		if(pv.selected.size() == 1) {
			Table t = pv.selected.get(0);
			xSpinner.setValue(t.getX());
			ySpinner.setValue(t.getY());
		}
	}
	
	/**
	 * Creates the JPanel for the Info class
	 */
	public void createPanel() {
		setPreferredSize(new Dimension(200, 474));
		setLayout(null);
		
		lblSelectedTableInfo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectedTableInfo.setBounds(30, 30, 130, 14);
		add(lblSelectedTableInfo);
		
		lblCounselor.setBounds(30, 72, 61, 14);
		add(lblCounselor);
		
		counsChooser.setBounds(30, 89, Constants.TEXT_FIELD_WIDTH, 20);
		counsChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!loading && pv.selected.size() == 1) {
					Counselor c = (Counselor) counsChooser.getSelectedItem();
					Table t = pv.selected.get(0);
					
					if(c != null) {						
						if(c.getTeam() != null) t.setTeam(c.getTeam());
						else t.setTeam(null);
					}
					
					t.setCounselor(c);
				}
			}
		});
		add(counsChooser);
		
		teamChooser.setBounds(30, 134, 130, 20);
		teamChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!loading && !pv.selected.isEmpty()) {
					Team team = (Team) teamChooser.getSelectedItem();
					
					for(int i = 0; i < pv.selected.size(); i++) {
						Table t = pv.selected.get(i);
						
						if(t.getTeam() != team) {
							t.setTeam(team);
							t.setCounselor(null);
						}
					}
					
					loading = true;
					updateCounsChooser();
					loading = false;
				}
			}
		});
		add(teamChooser);
		
		SpinnerModel xModel = new SpinnerNumberModel(0, 0, Constants.PROJ_VIEW_MAX_WIDTH, 1);
		xSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!loading && !pv.selected.isEmpty()) {
					for(int i = 0; i < pv.selected.size(); i++) { 
						pv.selected.get(i).setX((Integer) xSpinner.getValue());
					}
				}
			}
		});
		xSpinner.setBounds(77, 176, Constants.SPINNER_WIDTH, 20);
		xSpinner.setModel(xModel);
		add(xSpinner);
		
		SpinnerModel yModel = new SpinnerNumberModel(0, 0, Constants.PROJ_VIEW_MAX_HEIGHT, 1);
		ySpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!loading && !pv.selected.isEmpty()) {
					for(int i = 0; i < pv.selected.size(); i++) { 
						pv.selected.get(i).setY((Integer) ySpinner.getValue());
					}
				}
			}
		});
		ySpinner.setBounds(77, 207, Constants.SPINNER_WIDTH, 20);
		ySpinner.setModel(yModel);
		add(ySpinner);
		
		SpinnerModel widthModel = new SpinnerNumberModel(0, 0, Constants.TABLE_MAX_WIDTH, 1);
		widthSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!loading && !pv.selected.isEmpty()) {
					for(int i = 0; i < pv.selected.size(); i++) { 
						pv.selected.get(i).setWidth((Integer) widthSpinner.getValue());
					}
				}
			}
		});
		widthSpinner.setBounds(77, 238, Constants.SPINNER_WIDTH, 20);
		widthSpinner.setModel(widthModel);
		add(widthSpinner);
		
		SpinnerModel heightModel = new SpinnerNumberModel(0, 0, Constants.TABLE_MAX_HEIGHT, 1);
		heightSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!loading && !pv.selected.isEmpty()) {
					for(int i = 0; i < pv.selected.size(); i++) { 
						pv.selected.get(i).setHeight((Integer) heightSpinner.getValue());
					}
				}
			}
		});
		heightSpinner.setBounds(77, 269, Constants.SPINNER_WIDTH, 20);
		heightSpinner.setModel(heightModel);
		add(heightSpinner);
		
		lblX.setBounds(30, 179, 17, 14);
		add(lblX);
		
		lblY.setBounds(30, 210, 17, 17);
		add(lblY);
		
		lblWidth.setBounds(30, 240, 43, 17);
		add(lblWidth);
		
		lblHeight.setBounds(30, 272, 46, 14);
		add(lblHeight);
		
		lblTeam.setBounds(30, 120, 46, 14);
		add(lblTeam);
		
		btnRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!pv.selected.isEmpty()) {
					for(int i = 0; i < pv.selected.size(); i++) {
						Table t = pv.selected.get(i);
						
						int width = t.getHeight();
						int height = t.getWidth();
						t.setWidth(width);
						t.setHeight(height);
						widthSpinner.setValue(width);
						heightSpinner.setValue(height);
					}
				}
			}
		});
		btnRotate.setBounds(45, 350, 89, 23);
		add(btnRotate);
		
		btnSwap.setBounds(45, 384, 89, 23);
		btnSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pv.selected.size() == 2) {
					Table t1 = pv.selected.get(0);
					Table t2 = pv.selected.get(1);
					
					int t1x = t1.getX();
					int t1y = t1.getY();
					
					int t2x = t2.getX();
					int t2y = t2.getY();
					
					t1.setX(t2x);
					t1.setY(t2y);
					
					t2.setX(t1x);
					t2.setY(t1y);
				}
			}
		});
		add(btnSwap);
		
		setBackgroundColor(Constants.DARK_BACKGROUND);
		setFontColor(Constants.FONT_COLOR);
		
		setEnabled(false);
		btnSwap.setEnabled(false);
		
		btnChoosePartner.setBounds(30, 418, 114, 23);
		btnChoosePartner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pv.selected.size() == 1) {
					pv.act = ProjectView.Action.PARTNER;
				}
			}
		});
		add(btnChoosePartner);
		
		fixed.setBounds(30, 308, 97, 23);
		fixed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < pv.selected.size(); i++) {
					pv.selected.get(i).setFixed(fixed.isSelected());
				}
			}
		});
		add(fixed);
	}
	
	/**
	 * Sets the project view
	 */
	public void setProjView(ProjectView pv) {
		this.pv = pv;
	}
	
	/**
	 * Sets the background color of all components to
	 * the specified color
	 * @param color The color to change the background to
	 */
	public void setBackgroundColor(Color color) {
		setBackground(color);
		
		counsChooser.setBackground(color);
		teamChooser.setBackground(color);
		xSpinner.setBackground(color);
		ySpinner.setBackground(color);
		btnRotate.setBackground(color);
		btnSwap.setBackground(color);
		btnChoosePartner.setBackground(color);
		fixed.setBackground(color);
	}
	
	/**
	 * Sets the font color of the components in this dialog
	 * to a specified color
	 * @param color The color to change to font to
	 */
	public void setFontColor(Color color) {
		setForeground(color);
		
		lblSelectedTableInfo.setForeground(color);
		lblCounselor.setForeground(color);
		lblX.setForeground(color);
		lblY.setForeground(color);
		lblWidth.setForeground(color);
		lblHeight.setForeground(color);
		lblTeam.setForeground(color);
		fixed.setForeground(color);
	}
}