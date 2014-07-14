package com.henry.wilds.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.ProjectViewListener;

/**
 * This is the basic class for the dialog boxes
 * in the program. It has the basic framework,
 * and is extendable.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class WildsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	protected final JPanel contentPanel = new JPanel();
	protected JButton okButton = new JButton("OK");
	protected JButton cancelButton = new JButton("Cancel");
	protected WildsUI mainFrame;
	protected JPanel buttonPane = new JPanel();
	protected ProjectView pv;

	public WildsDialog(WildsUI mainFrame, ProjectView pv) {
		this.mainFrame = mainFrame;
		this.pv = pv;
		
		setTitle(Constants.DEFAULT_DIALOG_TITLE);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ProjectViewListener.resetAll();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setVisible() {
		setVisible(true);
	}
	
	public void setBackgroundColor(Color color) {
		setBackground(color);
		
		buttonPane.setBackground(color);
		cancelButton.setBackground(color);
		okButton.setBackground(color);
		contentPanel.setBackground(color);
	}
}