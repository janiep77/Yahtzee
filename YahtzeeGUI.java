import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class YahtzeeGUI {
	
	private final ImageIcon DICE_BLANK =
        new ImageIcon( "pix/blank.png");

	private final ImageIcon DICE_1 =  
        new ImageIcon( "pix/1.png");
	
	private final ImageIcon DICE_2 =
        new ImageIcon( "pix/2.png");
	
	private final ImageIcon DICE_3 = 
        new ImageIcon( "pix/3.png");
	
	private final ImageIcon DICE_4 = 
        new ImageIcon( "pix/4.png");
	
	private final ImageIcon DICE_5 =
        new ImageIcon( "pix/5.png");
	
	private final ImageIcon DICE_6 =
        new ImageIcon( "pix/6.png");
	
	private ImageIcon DICE_IMAGES[] = {
		DICE_1,
		DICE_2,
		DICE_3,
		DICE_4,
		DICE_5,
		DICE_6
	};
	
	private JFrame frame;
	private JButton rollButton;
	private ArrayList<JToggleButton> buttons = new ArrayList<JToggleButton>();
	private ArrayList<Dice> dice = new ArrayList<Dice>();
	
	private JPanel buttonPanel;
	
	private int rollCounter;
	private String title = "Simple Yahtzee 2";
	
	private int results[] = {0, 0, 0, 0, 0, 0};

	public static void main(String[] args) {
		new YahtzeeGUI().go();
	}
	
	public void go() {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buttonPanel = new JPanel();
		setupButtons();
		
		JPanel mainPanel = new JPanel();
		
		rollButton = new JButton("Roll");
		rollButton.addActionListener(new RollButtonListener());
		rollButton.setPreferredSize(new Dimension(90,66));
		
		JButton resetButton = new JButton("Reset");
		resetButton.setPreferredSize(new Dimension(90,66));
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetDice();
			}
		});
		
		mainPanel.add(resetButton);
		mainPanel.add(rollButton);
		mainPanel.add(buttonPanel);
		
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		resetDice();
		frame.pack();
		Constants.center(frame);
		frame.setVisible(true);
		
	}
	
	private void setupButtons() {
		
		for(int i = 0; i < 5; i++){
			JToggleButton button = new JToggleButton();
			
			buttons.add(button);
			buttonPanel.add(button);
			
			dice.add(new Dice());
		}
		
	}
	
	private void enableDiceButtons(boolean flag){
		for(JToggleButton b : buttons){
			b.setEnabled(flag);
		}
	}
	
	private void rollDice() {
		for(int i = 0; i < buttons.size(); i++){
			JToggleButton button = buttons.get(i);
			if(!button.isSelected())
				button.setIcon(DICE_IMAGES[dice.get(i).roll() - 1]);
		}
		frame.setTitle(title + " - Roll " + rollCounter);
	}
	
	private void resetDice() {
		for(JToggleButton b : buttons){
			b.setEnabled(false);
			b.setSelected(false);
			b.setIcon(DICE_BLANK);
		}
		frame.setTitle(title);
		rollCounter = 0;
		rollButton.setEnabled(true);
	}
	
	private int chanceScore(){
		int score = 0;
		for(int i = 0; i < results.length; i++){
			score += (i + 1) * results[i];
		}
		return score;
	}
	
	private boolean hasYahtzee() {
		
		
		boolean hasFive = false;
		for(int i = 0; i < results.length; i++){
			if(results[i] == 5){
				hasFive = true;
				break;
			} else if (results[i] > 0){
				break;
			}
		}
		return hasFive;
	}
	
	private void findResults() {
		clearResults();
		for(Dice d : dice){
			results[d.getValue() - 1]++;
		}
	}
	
	private void clearResults() {
		for (int i = 0; i < results.length; i++){
			results[i] = 0;
		}
	}
	
	class RollButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (rollCounter == 0) enableDiceButtons(true);
			rollCounter++;
			if (rollCounter == 3) rollButton.setEnabled(false);
			rollDice();
			
			findResults();
			System.out.println("Has yahtzee? " + hasYahtzee());
			System.out.println("Chance score: " + chanceScore());
			
		}	
	}

}


