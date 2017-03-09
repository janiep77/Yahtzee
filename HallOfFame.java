package yahtzee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class HallOfFame implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<ScoreEntry> topScoreList;
	private String file;
	
	public HallOfFame(String fileLocation){
		file = fileLocation;
		topScoreList = new ArrayList<ScoreEntry>();
	}
	
	/**
	 * Call this method if you want to add an entry to the Hall of Fame, and construct the
	 * JDialog with this newest entry added to the list in the appropriate order.
	 * @param name of the player to be added
	 * @param score the score to be added
	 * @return JDialog that holds this Hall of Fame, with message about newly added entry.
	 */
	public final JDialog getHallOfFame(String name, int score){
		
		loadHallOfFame();
		int place = addEntry(name, score);
		
		JDialog dialog = getHallOfFame();
		
		//Show them where they rank
		JLabel lbl = new JLabel();
		Font f = new Font("Verdana", Font.BOLD, 14);
		lbl.setFont(f);
		lbl.setForeground(new Color(255, 0, 0));
		lbl.setText("Your score of " + score + " ranks " + place + " out of " + topScoreList.size() + " entries.");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);

		dialog.add(lbl, BorderLayout.NORTH);
				
		return dialog;
	}
	
	
	/**
	 * Construct the Hall of Fame JDialog, to be displayed elsewhere
	 * @return JDialog that holds all of the HallOfFame members
	 */
	public final JDialog getHallOfFame() {
		if(topScoreList.size() == 0) loadHallOfFame();
		
		final JDialog frame = new JDialog();
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setTitle("Yahtzee - Hall of Fame");
		
		if(topScoreList.size() != 0) {
			
			GridBagLayout grid = new GridBagLayout();
			JPanel scoresPanel = new JPanel(grid);
			
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 0.5;
			constraints.ipady = 7;
			
			int row = 0;
			
			TitledBorder title = BorderFactory.createTitledBorder("Top 100 Scores");
			scoresPanel.setBorder(title);
			
			constraints.gridy = row++;
			constraints.gridx = 0;
			constraints.anchor = GridBagConstraints.NORTHWEST;
			scoresPanel.add(new JLabel("      Name"), constraints);
			
			constraints.gridx = 1;
			scoresPanel.add(new JLabel("Date Achieved"), constraints);
			
			constraints.gridx = 2;
			constraints.anchor = GridBagConstraints.NORTHEAST;
			scoresPanel.add(new JLabel("Score"), constraints);
			
			constraints.gridy = row;
			
			for(ScoreEntry entry : topScoreList){
				
				constraints.gridx = 0;
				JLabel nameLabel = new JLabel(String.format("%3s. ", row) + entry.getName());
				constraints.anchor = GridBagConstraints.NORTHWEST;
				scoresPanel.add(nameLabel, constraints);
				
				constraints.gridx = 1;
				JLabel timeLabel = new JLabel(entry.getTimestamp());
				scoresPanel.add(timeLabel, constraints);
				
				constraints.gridx = 2;
				JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore()));
				constraints.anchor = GridBagConstraints.NORTHEAST;
				scoresPanel.add(scoreLabel, constraints);
				
				constraints.gridy = ++row;
				if(row > 100) break;	
			}
			
			constraints.weighty=1;
			constraints.gridy++;
			scoresPanel.add(new JLabel(" "), constraints);
			
			JScrollPane scroller = new JScrollPane(scoresPanel);
			scroller.setPreferredSize(new Dimension(400, 350));
			
			frame.add(scroller, BorderLayout.CENTER);
			
		} else { 
			//no entries. Print out something else!
			JLabel lblNone = new JLabel ("No entries exist");
			lblNone.setHorizontalAlignment(SwingConstants.CENTER);
			frame.add(lblNone, BorderLayout.CENTER);
		}
		
		JPanel dismissPanel = new JPanel();
		JButton dismiss = new JButton ("Dismiss");
		
		dismiss.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();				
			}
		});
		
		dismissPanel.add(dismiss);
		frame.add(dismissPanel, BorderLayout.SOUTH);
		
		frame.setPreferredSize(new Dimension(500, 375));
		
		frame.setSize(500, 375);
		frame.setModal(true);
		
		return frame;
	}
	
	
	/**
	 * Loads the topScoreList ArrayList from the file specified
	 */
	@SuppressWarnings("unchecked")
	private void loadHallOfFame(){
		//load the list, make it sorted.
		
		FileInputStream fis		= null;
		ObjectInputStream ois	= null;
		
		try {
			File f = new File(file);
			if(f.exists()){
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);
			
				topScoreList = (ArrayList<ScoreEntry>) ois.readObject();
			
				Collections.sort(topScoreList);
			} else {
				//System.err.println("File does not yet exist");
				topScoreList = new ArrayList<ScoreEntry>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * This will add an entry - specified by name and score - to the topScoreList ArrayList
	 * @param name The name of the player to be added 
	 * @param score The player's yahtzee score
	 * @return the player's rank in topScoreList
	 */
	private int addEntry(String name, int score) {
		ScoreEntry e = new ScoreEntry(name, score);
		
		topScoreList.add(e);
		Collections.sort(topScoreList);
		saveHallOfFame();
		
		return (topScoreList.indexOf(e) + 1);
		
	}
	
	/**
	 * This method writes the current contents of topScoreList to the file specified.
	 */
	private void saveHallOfFame(){
		FileOutputStream fos	= null;
		ObjectOutputStream oos	= null;
		
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(topScoreList);
			
			oos.close();
			fos.close();
						
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private class ScoreEntry implements Serializable, Comparable<ScoreEntry> {

		private static final long serialVersionUID = 1L;
		private String name;
		private int score;
		private long timestamp; //epoch time
		
		public ScoreEntry(String name, int score){
			this.name	= name;
			this.score	= score;
			timestamp = System.currentTimeMillis();
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			if(name.length() > 20)
				name = name.substring(0,19);
			this.name = name;
		}

		/**
		 * @return the score
		 */
		public int getScore() {
			return score;
		}

		/**
		 * @param score the score to set
		 */
		public void setScore(int score) {
			this.score = score;
		}
		
		public String getTimestamp() {			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a");
			return sdf.format(new Date(timestamp));
		}

		@Override
		public int compareTo(ScoreEntry o) {
			if(o.getScore() > getScore()) return 1;
			else if (getScore() > o.getScore()) return -1;
			return 0;
		}
		
	}
	
	public static void main(String args[]){
		//Follow these instructions in order to display the HallOfFame from your program 
		
		//Create a new instance of the HallOfFame class - maybe add a String to your
		//Constants class to store the filename? 
		HallOfFame h = new HallOfFame("/home/YOUR_USERNAME/topTenFile"); //must have read/write access
		
		//JDialog f = h.getHallOfFame("Drew", 180); //to add an entry, and then display hall of fame
		JDialog f = h.getHallOfFame(); //to display just the hall of fame, not adding an entry
		
		//f.setLocationRelativeTo(yourMainFrameHere); //groups it with your frame on the screen
		f.setVisible(true);
		
	}

}
