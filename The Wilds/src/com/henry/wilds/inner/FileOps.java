package com.henry.wilds.inner;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.ProjectViewListener;
import com.henry.wilds.util.StringUtil;
import com.henry.wilds.util.Util;

/**
 * This is the class for file operations, such as
 * saving and loading wilds project files, and 
 * saving the initialization file.
 * @author Samuel Henry
 * @since June 23, 2014
 * @version 1.0
 */
public class FileOps {
	
	private WildsUI frame;
	
	private JFileChooser jfc = new JFileChooser();
	
	private File projFile = null;
	private File initFile = new File(Constants.INIT_FILE_NAME);
	
	/**
	 * Creates the FileOps class with the
	 * main frame for showing JOptionPanes
	 * and such things.
	 * @param frame The main frame
	 */
	public FileOps(WildsUI frame) {
		this.frame = frame;
		
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Wilds Project Files (*.wip)", "wip");
		
		jfc.setFileFilter(ff);
		jfc.setAcceptAllFileFilterUsed(false);
	}
	
	/**
	 * Load the init file to the program, 
	 * which takes in the user preferences
	 * and last project edited.
	 */
	public void init() {
		System.out.println("Loading init file: " + Constants.INIT_FILE_NAME);
		
		if(initFile != null && initFile.isFile()) {
			try {
				FileInputStream in = new FileInputStream(initFile);
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				
				int r, g, b;
				
				String line = br.readLine();
				while(line != null) {
					if(!line.trim().equals("")) {
						if(line.startsWith("CurrentProj")) {
							line = line.split("=")[1];
							if(!line.equals("None")) {
								projFile = new File(line);
								if(!projFile.isFile()) projFile = null;
							} else projFile = null;
							
						} else if(line.startsWith("TableWidth")) {
							Constants.TABLE_WIDTH = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("TableHeight")) {
							Constants.TABLE_HEIGHT = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("TableSpeed")) {
							Constants.TABLE_SPEED = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("TableColor")) {
							line = line.split("=")[1];
							String[] colors = line.split(" ");
							r = Integer.parseInt(colors[0]);
							g = Integer.parseInt(colors[1]);
							b = Integer.parseInt(colors[2]);
							Constants.TABLE_COLOR = new Color(r, g, b);
							
						} else if(line.startsWith("ProjViewWidth")) {
							Constants.PROJ_VIEW_WIDTH = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("ProjViewHeight")) {
							Constants.PROJ_VIEW_HEIGHT = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("ProjViewBackColor")) {
							line = line.split("=")[1];
							String[] colors = line.split(" ");
							r = Integer.parseInt(colors[0]);
							g = Integer.parseInt(colors[1]);
							b = Integer.parseInt(colors[2]);
							Constants.PROJ_VIEW_BACK_COLOR = new Color(r, g, b);
							
						} else if(line.startsWith("Background")) {
							line = line.split("=")[1];
							String[] colors = line.split(" ");
							r = Integer.parseInt(colors[0]);
							g = Integer.parseInt(colors[1]);
							b = Integer.parseInt(colors[2]);
							Constants.BACKGROUND = new Color(r, g, b);
							
						} else if(line.startsWith("DarkBackground")) {
							line = line.split("=")[1];
							String[] colors = line.split(" ");
							r = Integer.parseInt(colors[0]);
							g = Integer.parseInt(colors[1]);
							b = Integer.parseInt(colors[2]);
							Constants.DARK_BACKGROUND = new Color(r, g, b);
							
						} else if(line.startsWith("FontColor")) {
							line = line.split("=")[1];
							String[] colors = line.split(" ");
							r = Integer.parseInt(colors[0]);
							g = Integer.parseInt(colors[1]);
							b = Integer.parseInt(colors[2]);
							Constants.FONT_COLOR = new Color(r, g, b);
							
						} else if(line.startsWith("FrameWidth")) {
							Constants.WIDTH = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("FrameHeight")) {
							Constants.HEIGHT = StringUtil.getIntegerFromSplitPos(line, "=", 1);
							
						} else if(line.startsWith("Maximized")) {
							line = line.split("=")[1];
							if(line.equals("true")) Constants.MAXIMIZED = true;
							else Constants.MAXIMIZED = false;
							
						} else if(line.startsWith("ShowAdditionalInfo")) {
							line = line.split("=")[1];
							if(line.equals("true")) Constants.SHOW_ADDITIONAL_INFO = true;
							else Constants.SHOW_ADDITIONAL_INFO = false;
							
						} else if(line.startsWith("TableImage")) {
							line = line.split("=")[1];
							File imgFile = new File(line);
							if(imgFile.isFile()) {
								ProjectView.TABLE_IMAGE_LOC = imgFile.getAbsolutePath();
								ProjectView.TABLE_IMAGE = Util.getImage(imgFile);
							}
							
						} else {
							System.out.println("Unknown line in init file!");
						}
					}
					line = br.readLine();
				}
				br.close();
				isr.close();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Init file not found!");
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			saveInitFile();
		}
	}
	
	/**
	 * This function loads a wilds project file
	 * to the program.
	 */
	public void loadFile() {		
		ProjectView pv = frame.getProjView();
		
		if(projFile != null && pv != null) {
			frame.setTitle("Wilds Dining Hall - " + projFile.getName());
			
			System.out.println("Loading " + projFile.getName());
			
			clearItems();
			
			try {
				FileInputStream in = new FileInputStream(projFile);
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				
				String what = "";
				String line = br.readLine();
				while(line != null) {
					if(!line.trim().equals("")) {
						if(line.equals("General")) what = line;
						else if(line.equals("Teams")) what = line;
						else if(line.equals("Counselors")) what = line;
						else if(line.equals("Tables")) what = line;
						else if(line.equals("Partners")) what = line;
						else if(line.equals("TablePartners")) what = line;
						else if(what.equals("General")) {
							if(line.startsWith("ProjViewWidth")) {
								line = line.split("=")[1];
								ProjectView.PROJ_VIEW_WIDTH = Integer.parseInt(line);
								
							} else if(line.startsWith("ProjViewHeight")) {
								line = line.split("=")[1];
								ProjectView.PROJ_VIEW_HEIGHT = Integer.parseInt(line);
								
							} else if(line.startsWith("TableWidth")) {
								line = line.split("=")[1];
								ProjectView.TABLE_WIDTH = Integer.parseInt(line);
								
							} else if(line.startsWith("TableHeight")) {
								line = line.split("=")[1];
								ProjectView.TABLE_HEIGHT = Integer.parseInt(line);
								
							} else if(line.startsWith("TableSpeed")) {
								line = line.split("=")[1];
								ProjectView.TABLE_SPEED = Integer.parseInt(line);
								
							} else if(line.startsWith("ProjViewBackground")) {
								line = line.split("=")[1];
								String[] color = line.split(" ");
								int r = Integer.parseInt(color[0]);
								int g = Integer.parseInt(color[1]);
								int b = Integer.parseInt(color[2]);
								ProjectView.PROJ_VIEW_BACK_COLOR = new Color(r, g, b);
							}
						} else if(what.equals("Teams")) { //[Team Name]=[Red value] [Green value] [Blue value]
							String[] teamData = line.split("=");
							String name = teamData[0];
							teamData = teamData[1].split(" ");
							int r = Integer.parseInt(teamData[0]);
							int g = Integer.parseInt(teamData[1]);
							int b = Integer.parseInt(teamData[2]);
							pv.teams.add(new Team(name, new Color(r, g, b)));
							
						} else if(what.equals("Counselors")) { //[Counselor name]=[Cabin name]:[Team Name]
							String[] counsData = line.split("=");
							String counselor = counsData[0];
							counsData = counsData[1].split(":");
							String cabinName = "";
							if(!counsData[0].equals("null")) {
								cabinName = counsData[0];
							}
							Team team = pv.getTeamByName(counsData[1]);
							pv.counselors.add(new Counselor(counselor, cabinName, team, null));
							
						} else if(what.equals("Partners")) {//[Conselors name]=[Partner name]
							String[] partners = line.split("=");
							Counselor c = pv.getCounsByName(partners[0]);
							if(!partners[1].equals("null")) c.setPartner(pv.getCounsByName(partners[1]));
							else c.setPartner(null);
							
						} else if(what.equals("Tables")) { //[Counselor name]=[Team name]:[X] [Y] [Width] [Height] [id]
							String[] tableData = line.split("=");
							Counselor c = pv.getCounsByName(tableData[0]);
							tableData = tableData[1].split(":");
							Team team = pv.getTeamByName(tableData[0]);
							String[] pos = tableData[1].split(" ");
							int x = Integer.parseInt(pos[0]);
							int y = Integer.parseInt(pos[1]);
							int width = Integer.parseInt(pos[2]);
							int height = Integer.parseInt(pos[3]);
							boolean fixed = pos[4].equals("true") ? true : false;
							String id = pos[5];
							
							Table newTable = new Table(c, team, x, y, width, height);
							pv.tables.add(newTable);
							newTable.setId(id);
							newTable.setFixed(fixed);
							
						} else if(what.equals("TablePartners")) {
							String[] tableData = line.split("=");
							
							Table t = pv.getTableById(tableData[0]);
							
							if(!tableData[1].equals("null")) {
								t.setPartner(pv.getTableById(tableData[1]));
							}
							
						}
					}
					
					line = br.readLine();
				}
				
				in.close();
				isr.close();
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("Can't load! The file isn't defined, retard...");
		}
		
		pv.sortLists();
	}
	
	/**
	 * This function opens a new file
	 */
	public void openFile() {
		if(jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File newFile = jfc.getSelectedFile();
			if(!newFile.exists()) {
				JOptionPane.showMessageDialog(frame, newFile.getName() + " does not exist.");
				return;
			} else if(!newFile.equals(projFile)) {
				int save = JOptionPane.showConfirmDialog(frame, "Do you want to save the changes to this project?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
				
				switch(save) {
					case 0 : saveChanges(); break;
					case 1 : break;
					case 2 : return;
					case -1 : return;
					default : System.out.println("UNKNOWN OPTION");
				}
				
				clearItems();
				if(projFile != newFile) {
					projFile = newFile;
					loadFile();
				}
			}
		}
	}
	
	/**
	 * This function creates a new file
	 * based on the file given to it
	 * @param f the File to create
	 */
	public void createNewFile(File f) {
		if(!f.getName().endsWith(".wip")) {
			JOptionPane.showMessageDialog(frame, "File must end with \".wip\"");
			return;
		}
		if(f.exists()) { 
			int overwrite = JOptionPane.showConfirmDialog(frame, f.getName() + " already exists. Do you want to overwrite it?", "Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION);
			
			switch(overwrite) {
				case 0 : break;
				case 1 : return;
				case 2 : return;
				case -1 : return;
				default : System.out.println("UNKNOWN OPTION");
			}
			
		}
		clearItems();
		projFile = f;
		
		try {
			FileOutputStream out = new FileOutputStream(projFile);
			
			System.out.println("Creating new file.");
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function creates a new wilds project
	 * to be saved in the future
	 */
	public void newFile() {
		int response = JOptionPane.showConfirmDialog(frame, "Do you want to save the changes to this project?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
		
		switch(response) {
			case 0 : saveChanges(); break;
			case 1 : break;
			case 2 : return;
			case -1 : return;
			default : System.out.println("UNKNOWN OPTION!");
		}
		
		clearItems();
		projFile = null;
		ProjectView.createNewFile();
		
		System.out.println("Creating new project.");
	}
	
	/**
	 * This 
	 * @return
	 */
	public boolean saveChanges() {
		if(projFile != null) {
			saveProject();
			return true;
		} else {
			if(jfc.showDialog(frame, "New Wilds Project...") == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				if(!f.getName().endsWith(".wip")) {
					JOptionPane.showMessageDialog(frame, "File must end with \".wip\"");
					return false;
				}
				projFile = f;
				saveProject();
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void saveAs() {
		if(jfc.showDialog(frame, "Save As") == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			if(!f.getName().endsWith(".wip")) {
				JOptionPane.showMessageDialog(frame, "File must end with \".wip\"");
				return;
			} else {
				projFile = f;
				saveProject();
			}
		}
	}
	
	/**
	 * Saves the project currently being edited
	 */
	private void saveProject() {
		System.out.println("Saving project: " + projFile.getName());
		
		ProjectView pv = frame.getProjView();
		
		try {
			FileOutputStream out = new FileOutputStream(projFile);
			writeln("General", out);
			writeln("ProjViewWidth=" + frame.getProjView().getWidth(), out);
			writeln("ProjViewHeight=" + frame.getProjView().getHeight(), out);
			writeln("TableWidth=" + ProjectView.TABLE_WIDTH, out);
			writeln("TableHeight=" + ProjectView.TABLE_HEIGHT, out);
			writeln("TableSpeed=" + ProjectView.TABLE_SPEED, out);
			int red = ProjectView.PROJ_VIEW_BACK_COLOR.getRed();
			int green = ProjectView.PROJ_VIEW_BACK_COLOR.getGreen();
			int blue = ProjectView.PROJ_VIEW_BACK_COLOR.getBlue();
			writeln("ProjViewBackground=" + red + " " + green + " " + blue, out);
			writeln(out);
			
			//Saves all the teams to the file.
			writeln("Teams", out);
			//[Team Name]=[Red value] [Green value] [Blue value]
			for(int i = 0; i < pv.teams.size(); i++) {
				Team team = pv.teams.get(i);
				int r = team.getColor().getRed();
				int g = team.getColor().getGreen();
				int b = team.getColor().getBlue();
				writeln(team.getTeamName() + "=" + r + " " + g + " " + b, out);
			}
			writeln(out);
			
			//Saves all the counselors to the file.
			writeln("Counselors", out);
			//[Counselor name]=[Cabin name]:[Team Name]
			for(int i = 0; i < pv.counselors.size(); i++) {
				Counselor c = pv.counselors.get(i);
				String cabinName = "null";
				if(!c.getCabinName().trim().equals("")) {
					cabinName = c.getCabinName();
				}
				String team = "null";
				if(c.getTeam() != null) {
					team = c.getTeam().getTeamName();
				}
				writeln(c.getName() + "=" + cabinName + ":" + team, out);
			}
			writeln(out);
			
			//Saves all the counselor's partners to the file.
			writeln("Partners", out);
			//[Counselor name]=[Partner name]
			for(int i = 0; i < pv.counselors.size(); i++) {
				Counselor c = pv.counselors.get(i);
				String partner;
				if(c.getPartner() == null) partner = "null";
				else partner = c.getPartner().getName();
				
				writeln(c.getName() + "=" + partner, out);
			}
			writeln(out);
			
			//Saves all the tables to the file.
			writeln("Tables", out);
			//[Counselor name]=[Team name]:[X] [Y] [Width] [Height] [fixed] [id]
			for(int i = 0; i < pv.tables.size(); i++) {
				Table t = pv.tables.get(i);
				
				if(t.getCounselor() == null) {
					write("null=", out);
				} else {
					write(t.getCounselor().getName() + "=", out);
				}
				if(t.getTeam() == null) {
					write("null:", out);
				} else {
					write(t.getTeam().getTeamName() + ":", out);
				}
				writeln(t.getX() + " " + t.getY() + " " + t.getWidth() + " " + t.getHeight() + " " + t.isFixed() + " " + t, out);
			}
			writeln(out);
			
			//Saves all the tables' partners to the file.
			writeln("TablePartners", out);
			//[Table ID]=[Table Partner ID]
			for(int i = 0; i < pv.tables.size(); i++) {
				Table t = pv.tables.get(i);
				
				String id = t.getPartner() == null ? "null" : t.getPartner().toString();
				
				writeln(t + "=" + id, out);
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes some text to a file
	 * @param toWrite What to write to the file
	 * @param out The FileOutputStream to write the text to
	 */
	private void write(String toWrite, FileOutputStream out) {
		try {
			out.write(toWrite.getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a line of text to a file
	 * @param toWrite What to write to the file
	 * @param out The FileOutputStream to write the text to
	 */
	private void writeln(String toWrite, FileOutputStream out) {
		try {
			out.write((toWrite + "\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a plain new line to the file
	 * @param out The FileOutputStream to write the line to
	 */
	private void writeln(FileOutputStream out) {
		try {
			out.write("\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the program's init file
	 */
	public void saveInitFile() {
		System.out.println("Saving init file.");
		
		try {
			FileOutputStream out = new FileOutputStream(initFile);
			
			int r, g, b;
			
			writeln("TableWidth=" + Constants.TABLE_WIDTH, out);
			
			writeln("TableHeight=" + Constants.TABLE_HEIGHT, out);
			
			writeln("TableSpeed=" + Constants.TABLE_SPEED, out);
			
			r = Constants.TABLE_COLOR.getRed();
			g = Constants.TABLE_COLOR.getGreen();
			b = Constants.TABLE_COLOR.getBlue();
			writeln("TableColor=" + r + " " + g + " " + b, out);
			
			writeln("ProjViewWidth=" + Constants.PROJ_VIEW_WIDTH, out);
			
			writeln("ProjViewHeight=" + Constants.PROJ_VIEW_HEIGHT, out);
			
			r = Constants.PROJ_VIEW_BACK_COLOR.getRed();
			g = Constants.PROJ_VIEW_BACK_COLOR.getGreen();
			b = Constants.PROJ_VIEW_BACK_COLOR.getBlue();
			writeln("ProjViewBackColor=" + r + " " + g + " " + b, out);
			
			r = Constants.BACKGROUND.getRed();
			g = Constants.BACKGROUND.getGreen();
			b = Constants.BACKGROUND.getBlue();
			writeln("Background=" + r + " " + g + " " + b, out);
			
			r = Constants.DARK_BACKGROUND.getRed();
			g = Constants.DARK_BACKGROUND.getGreen();
			b = Constants.DARK_BACKGROUND.getBlue();
			writeln("DarkBackground=" + r + " " + g + " " + b, out);
			
			r = Constants.FONT_COLOR.getRed();
			g = Constants.FONT_COLOR.getGreen();
			b = Constants.FONT_COLOR.getBlue();
			writeln("FontColor=" + r + " " + g + " " + b, out);
			
			if(!Constants.MAXIMIZED) {
				writeln("FrameWidth=" + frame.getWidth(), out);
				writeln("FrameHeight=" + frame.getHeight(), out);
			} else {
				writeln("FrameWidth=800", out);
				writeln("FrameHeight=600", out);
			}
			writeln("Maximized=" + Constants.MAXIMIZED, out);
			
			writeln("ShowAdditionalInfo=" + Constants.SHOW_ADDITIONAL_INFO, out);
			
			writeln("TableImage=" + ProjectView.TABLE_IMAGE_LOC, out);
			
			if(projFile != null) {
				writeln("CurrentProj=" + projFile.getAbsolutePath(), out);
			} else {
				writeln("CurrentProj=None", out);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function basically resets the whole program
	 */
	private void clearItems() {
		ProjectView pv = frame.getProjView();
		
		pv.teams.clear();
		pv.counselors.clear();
		pv.tables.clear();
		pv.selected.clear();
		
		ProjectViewListener.resetAll();
	}
}