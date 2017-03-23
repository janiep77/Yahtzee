package Pictures;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class Yahtzee {

	private int rollCounter;
	private JButton rollButton;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel scorePanel;
	private JPanel upper;
	private JPanel lower; 
	private JPanel dicePanel;
	private ArrayList<JToggleButton> buttons = new ArrayList<JToggleButton>();
	private ArrayList handlers = new ArrayList();
	private ArrayList<Dice> dice = new ArrayList<Dice>();
	
	private final ImageIcon DICE_BLANK =
	        new ImageIcon( "Pictures/blank.png");
 
	private final ImageIcon DICE_1 =  
	        new ImageIcon( "Pictures/1.png");
		
	private final ImageIcon DICE_2 =
	        new ImageIcon( "Pictures/2.png");	
		
	private final ImageIcon DICE_3 = 
	        new ImageIcon( "Pictures/3.png");
		
	private final ImageIcon DICE_4 = 
	        new ImageIcon( "Pictures/4.png");
		
	private final ImageIcon DICE_5 =
			new ImageIcon( "Pictures/5.png");
		
	private final ImageIcon DICE_6 =
	        new ImageIcon( "Pictures/6.png");
		
	private ImageIcon DICE_IMAGES[] = {
			DICE_1,
			DICE_2,
			DICE_3,
			DICE_4,
			DICE_5,
			DICE_6,
			DICE_BLANK
	};	
	public static void main(String[] args){
		new Yahtzee().go();
	}

	public void go(){
		frame = new JFrame("Yahtzee");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel 		= new JPanel(new BorderLayout());
		scorePanel		= new JPanel(new FlowLayout());
		dicePanel 		= new JPanel();
		setupDicePanel();
		
		upper		 	= new JPanel();
		//setupUpper();
		
		lower			= new JPanel();
		//setupLower();
		
		//scorePanel.add(upper);
		//scorePanel.add(lower);
		
		//mainPanel.add(scorePanel, BorderLauout.CENTER);
		mainPanel.add(dicePanel, BorderLayout.SOUTH);
		
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	private void setupUpper(){
		
	}
	private void setupLower(){
		
	}
	private void setupDicePanel(){
		rollButton 		= new JButton("Roll");
		rollButton.addActionListener(new RollButtonListener());
		dicePanel.add(rollButton);
		for(int i = 0; i < 5; i++){
			JToggleButton diceButton = new JToggleButton();
			diceButton.setIcon(DICE_BLANK);
			buttons.add(diceButton);
			dicePanel.add(diceButton);
			
			//dice.add(new Dice());
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
	}

	class RollButtonListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if (rollCounter == 0) enableDiceButtons(true);
			rollCounter++;
			if (rollCounter == 3) rollButton.setEnabled(false);
			rollDice();
		}
	}
}

	

